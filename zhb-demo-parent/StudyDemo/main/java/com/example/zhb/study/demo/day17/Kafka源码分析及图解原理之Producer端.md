一.前言
　　任何消息队列都是万变不离其宗都是3部分，消息生产者（Producer）、消息消费者（Consumer）和服务载体（在Kafka中用Broker指代）。那么本篇主要讲解Producer端，会有适当的图解帮助理解底层原理。

　

回到顶部
一.开发应用
　　首先介绍一下开发应用，如何构建一个KafkaProducer及使用，还有一些重要参数的简介。

1.1 一个栗子
复制代码
 1 /**
 2  * Kafka Producer Demo实例类。
 3  *
 4  * @author GrimMjx
 5  */
 6 public class ProducerDemo {
 7     public static void main(String[] args) throws ExecutionException, InterruptedException {
 8         Properties prop = new Properties();
 9         prop.put("client.id", "DemoProducer");
10 
11         // 以下三个参数必须指定
12         // 用于创建与Kafka broker服务器的连接，集群的话则用逗号分隔
13         prop.put("bootstrap.servers", "localhost:9092");
14         // 消息的key序列化方式
15         prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
16         // 消息的value序列化方式
17         prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
18 
19         // 以下参数为可配置选项
20         prop.put("acks", "-1");
21         prop.put("retries", "3");
22         prop.put("batch.size", "323840");
23         prop.put("linger.ms", "10");
24         prop.put("buffer.memory", "33554432");
25         prop.put("max.block.ms", "3000");
26 
27         KafkaProducer<String, String> producer = new KafkaProducer<String, String>(prop);
28         try {
29             // 异步发送，继续发送消息不用等待。当有结果返回时，callback会被通知执行
30             producer.send(new ProducerRecord<String, String>("test", "key1", "value1"),
31                     new Callback() {
32                         // 返回结果RecordMetadata记录元数据包括了which partition的which offset
33                         public void onCompletion(RecordMetadata metadata, Exception e) {
34                             // 发送成功
35                             if (e == null) {
36                                 System.out.println("The offset of the record we just sent is: " + metadata.offset());
37 
38                                 // 发送失败
39                             } else {
40                                 if (e instanceof RetriableException) {
41                                     // 处理可重试的异常，比如分区leader副本不可用
42                                     // 一般用retries参数来设置重置，毕竟这里也没有什么其他能做的，也是同样的重试发送消息
43                                 } else {
44                                     // 处理不可重试异常
45                                 }
46                             }
47                         }
48                     }
49             );
50 
51             // 同步发送，send方法返回Future，然后get。在没有返回结果一直阻塞
52             producer.send(new ProducerRecord<String, String>("test", "key1", "value1")).get();
53 
54         } finally {
55             // producer运行的时候占用系统额外资源，最后一定要关闭
56             producer.close();
57         }
58     }
59 }
复制代码
　　注释已经写得十分详细了，参数的下面会说，这里就只说一下异步发送和同步发送。我们先看下KafkaProducer.send方法，可以看到返回的是一个Future，那么如何实现同步阻塞和异步非阻塞呢？



同步阻塞：send方法返回Future，然后get。在没有返回结果一直阻塞，无限等待
异步非阻塞：send方法提供callback，调用send方法后可以继续发送消息不用等待。当有结果返回时，callback会被通知执行


1.2 重要参数
　　这里分析一下broker端的重要参数，前3个是必要参数。Kafka的文档真的很吊，可以看这个类，每个参数和注释都解释的十分详细：org.apache.kafka.clients.producer.ProducerConfig

bootstrap.server（必要）：broker服务器列表，如果集群的机器很多，不用全配，producer可以发现集群中所有broker
key.serializer/value.serializer（必要）：key和value的序列化方式。这两个参数都必须是全限定类名，可以自定义拓展。
acks：有3个值，0、1和all（-1）
0：produce不关心broker端的处理结果，吞吐量最高
1：produce发送消息给leader broker端，broker端写入本地日志返回结果，折中方案
all(-1)：配合min.insync.replicas使用，控制写入isr中的多少副本才算成功
思考：如果当前集群中ISR副本小于min.insync.replicas会发生什么，消费者还能正常消费吗？stack overflow地址：https://stackoverflow.com/questions/57231185/does-min-insync-replicas-property-effects-consumers-in-kafka
buffer.memory：producer启动会创建一个内存缓冲区保存待发送的消息，这部分的内存大小就是这个参数来控制的
commpression.type：压缩算法的选择，目前有GZIP、Snappy和LZ4。目前结合LZ4性能最好
retries：重试次数，0.11.0.0版本之前可能导致消息重发
batch.size：相同分区多条消息集合叫batch，当batch满了则发送给broker
linger.ms：难道batch没满就不发了么？当然不是，不满则等linger.ms时间再发。延时权衡行为
max.request.size：控制发送请求的大小
request.timeout.ms：超过时间则会在回调函数抛出TimeoutException异常
partitioner.class：分区机制，可自定义，默认分区器的处理是：有key则用murmur2算法计算key的哈希值，对总分区取模算出分区号，无key则轮询
enable.idempotence：Apache Kafka 0.11.0.0版本用于实现EOS的利器
回到顶部
二.源码分析及图解原理
2.1 RecordAccumulator
　　上面介绍的参数中buffer.memory是缓冲区的大小，RecordAccmulator就是承担了缓冲区的角色。默认是32MB。

　　还有上面介绍的参数中batch.size提到了batch的概念，在kafka producer中，消息不是一条一条发给broker的，而是多条消息组成一个ProducerBatch，然后由Sender一次性发出去，这里的batch.size并不是消息的条数（凑满多少条即发送），而是一个大小。默认是16KB，可以根据具体情况来进行优化。

　　在RecordAccumulator中，最核心的参数就是：

private final ConcurrentMap<TopicPartition, Deque<ProducerBatch>> batches;
　　它是一个ConcurrentMap，key是TopicPartition类，代表一个topic的一个partition。value是一个包含ProducerBatch的双端队列。等待Sender线程发送给broker。画张图来看下：



　　再从源码角度来看如何添加到缓冲区队列里的，主要看这个方法：org.apache.kafka.clients.producer.internals.RecordAccumulator#append：

　　注释写的十分详细了，这里需要思考一点，为什么分配内存的代码没有放在synchronized同步块里？看起来这里很多余，导致下面的synchronized同步块中还要tryAppend一下，因为这时候可能其他线程已经创建好RecordBatch了。造成多余的内存申请。但是仔细想想，如果把分配内存放在synchronized同步块会有什么问题？

　　内存申请不到线程会一直等待，如果放在同步块中会造成一直不释放Deque队列的锁，那其他线程将无法对Deque队列进行线程安全的同步操作。那不是走远了？

复制代码
 1 /**
 2  * Add a record to the accumulator, return the append result
 3  * <p>
 4  * The append result will contain the future metadata, and flag for whether the appended batch is full or a new batch is created
 5  * <p>
 6  *
 7  * @param tp The topic/partition to which this record is being sent
 8  * @param timestamp The timestamp of the record
 9  * @param key The key for the record
10  * @param value The value for the record
11  * @param headers the Headers for the record
12  * @param callback The user-supplied callback to execute when the request is complete
13  * @param maxTimeToBlock The maximum time in milliseconds to block for buffer memory to be available
14  */
15 public RecordAppendResult append(TopicPartition tp,
16                                  long timestamp,
17                                  byte[] key,
18                                  byte[] value,
19                                  Header[] headers,
20                                  Callback callback,
21                                  long maxTimeToBlock) throws InterruptedException {
22     // We keep track of the number of appending thread to make sure we do not miss batches in
23     // abortIncompleteBatches().
24     appendsInProgress.incrementAndGet();
25     ByteBuffer buffer = null;
26     if (headers == null) headers = Record.EMPTY_HEADERS;
27     try {
28         // check if we have an in-progress batch
29         // 其实就是一个putIfAbsent操作的方法，不展开分析
30         Deque<ProducerBatch> dq = getOrCreateDeque(tp);
31         // batches是线程安全的，但是Deque不是线程安全的
32         // 已有在处理中的batch
33         synchronized (dq) {
34             if (closed)
35                 throw new IllegalStateException("Cannot send after the producer is closed.");
36             RecordAppendResult appendResult = tryAppend(timestamp, key, value, headers, callback, dq);
37             if (appendResult != null)
38                 return appendResult;
39         }
40 
41         // we don't have an in-progress record batch try to allocate a new batch
42         // 创建一个新的ProducerBatch
43         byte maxUsableMagic = apiVersions.maxUsableProduceMagic();
44         // 分配一个内存
45         int size = Math.max(this.batchSize, AbstractRecords.estimateSizeInBytesUpperBound(maxUsableMagic, compression, key, value, headers));
46         log.trace("Allocating a new {} byte message buffer for topic {} partition {}", size, tp.topic(), tp.partition());
47         // 申请不到内存
48         buffer = free.allocate(size, maxTimeToBlock);
49         synchronized (dq) {
50             // Need to check if producer is closed again after grabbing the dequeue lock.
51             if (closed)
52                 throw new IllegalStateException("Cannot send after the producer is closed.");
53 
54             // 再次尝试添加，因为分配内存的那段代码并不在synchronized块中
55             // 有可能这时候其他线程已经创建好RecordBatch了，finally会把分配好的内存还回去
56             RecordAppendResult appendResult = tryAppend(timestamp, key, value, headers, callback, dq);
57             if (appendResult != null) {
58                 // 作者自己都说了，希望不要总是发生，多个线程都去申请内存，到时候还不是要还回去？
59                 // Somebody else found us a batch, return the one we waited for! Hopefully this doesn't happen often...
60                 return appendResult;
61             }
62 
63             // 创建ProducerBatch
64             MemoryRecordsBuilder recordsBuilder = recordsBuilder(buffer, maxUsableMagic);
65             ProducerBatch batch = new ProducerBatch(tp, recordsBuilder, time.milliseconds());
66             FutureRecordMetadata future = Utils.notNull(batch.tryAppend(timestamp, key, value, headers, callback, time.milliseconds()));
67 
68             dq.addLast(batch);
69             // incomplete是一个Set集合，存放不完整的batch
70             incomplete.add(batch);
71 
72             // Don't deallocate this buffer in the finally block as it's being used in the record batch
73             buffer = null;
74 
75             // 返回记录添加结果类
76             return new RecordAppendResult(future, dq.size() > 1 || batch.isFull(), true);
77         }
78     } finally {
79         // 释放要还的内存
80         if (buffer != null)
81             free.deallocate(buffer);
82         appendsInProgress.decrementAndGet();
83     }
84 }
复制代码
　　附加tryAppend()方法，不多说，都在代码注释里：

复制代码
 1 /**
 2  *  Try to append to a ProducerBatch.
 3  *
 4  *  If it is full, we return null and a new batch is created. We also close the batch for record appends to free up
 5  *  resources like compression buffers. The batch will be fully closed (ie. the record batch headers will be written
 6  *  and memory records built) in one of the following cases (whichever comes first): right before send,
 7  *  if it is expired, or when the producer is closed.
 8  */
 9 private RecordAppendResult tryAppend(long timestamp, byte[] key, byte[] value, Header[] headers, Callback callback, Deque<ProducerBatch> deque) {
10     // 获取最新加入的ProducerBatch
11     ProducerBatch last = deque.peekLast();
12     if (last != null) {
13         FutureRecordMetadata future = last.tryAppend(timestamp, key, value, headers, callback, time.milliseconds());
14         if (future == null)
15             last.closeForRecordAppends();
16         else
17             // 记录添加结果类包含future、batch是否已满的标记、是否是新batch创建的标记
18             return new RecordAppendResult(future, deque.size() > 1 || last.isFull(), false);
19     }
20     // 如果这个Deque没有ProducerBatch元素，或者已经满了不足以加入本条消息则返回null
21     return null;
22 }
复制代码
 　　以上代码见图解：

 

2.2 Sender
　　Sender里最重要的方法莫过于run()方法，其中比较核心的方法是org.apache.kafka.clients.producer.internals.Sender#sendProducerData

　　其中pollTimeout需要认真读注释，意思是最长阻塞到至少有一个通道在你注册的事件就绪了。返回0则表示走起发车了

复制代码
 1 private long sendProducerData(long now) {
 2     // 获取当前集群的所有信息
 3     Cluster cluster = metadata.fetch();
 4     // get the list of partitions with data ready to send
 5     // @return ReadyCheckResult类的三个变量解释
 6     // 1.Set<Node> readyNodes 准备好发送的节点
 7     // 2.long nextReadyCheckDelayMs 下次检查节点的延迟时间
 8     // 3.Set<String> unknownLeaderTopics 哪些topic找不到leader节点
 9     RecordAccumulator.ReadyCheckResult result = this.accumulator.ready(cluster, now);
10     // if there are any partitions whose leaders are not known yet, force metadata update
11     // 如果有些topic不知道leader信息，更新metadata
12     if (!result.unknownLeaderTopics.isEmpty()) {
13         // The set of topics with unknown leader contains topics with leader election pending as well as
14         // topics which may have expired. Add the topic again to metadata to ensure it is included
15         // and request metadata update, since there are messages to send to the topic.
16         for (String topic : result.unknownLeaderTopics)
17             this.metadata.add(topic);
18         this.metadata.requestUpdate();
19     }
20 
21     // 去除不能发送信息的节点
22     // remove any nodes we aren't ready to send to
23     Iterator<Node> iter = result.readyNodes.iterator();
24     long notReadyTimeout = Long.MAX_VALUE;
25     while (iter.hasNext()) {
26         Node node = iter.next();
27         if (!this.client.ready(node, now)) {
28             iter.remove();
29             notReadyTimeout = Math.min(notReadyTimeout, this.client.connectionDelay(node, now));
30         }
31     }
32 
33     // 获取将要发送的消息
34     // create produce requests
35     Map<Integer, List<ProducerBatch>> batches = this.accumulator.drain(cluster, result.readyNodes,
36             this.maxRequestSize, now);
37 
38     // 保证发送消息的顺序
39     if (guaranteeMessageOrder) {
40         // Mute all the partitions drained
41         for (List<ProducerBatch> batchList : batches.values()) {
42             for (ProducerBatch batch : batchList)
43                 this.accumulator.mutePartition(batch.topicPartition);
44         }
45     }
46 
47     // 过期的batch
48     List<ProducerBatch> expiredBatches = this.accumulator.expiredBatches(this.requestTimeout, now);
49     boolean needsTransactionStateReset = false;
50     // Reset the producer id if an expired batch has previously been sent to the broker. Also update the metrics
51     // for expired batches. see the documentation of @TransactionState.resetProducerId to understand why
52     // we need to reset the producer id here.
53     if (!expiredBatches.isEmpty())
54         log.trace("Expired {} batches in accumulator", expiredBatches.size());
55     for (ProducerBatch expiredBatch : expiredBatches) {
56         failBatch(expiredBatch, -1, NO_TIMESTAMP, expiredBatch.timeoutException());
57         if (transactionManager != null && expiredBatch.inRetry()) {
58             needsTransactionStateReset = true;
59         }
60         this.sensors.recordErrors(expiredBatch.topicPartition.topic(), expiredBatch.recordCount);
61     }
62     if (needsTransactionStateReset) {
63         transactionManager.resetProducerId();
64         return 0;
65     }
66     sensors.updateProduceRequestMetrics(batches);
67     // If we have any nodes that are ready to send + have sendable data, poll with 0 timeout so this can immediately
68     // loop and try sending more data. Otherwise, the timeout is determined by nodes that have partitions with data
69     // that isn't yet sendable (e.g. lingering, backing off). Note that this specifically does not include nodes
70     // with sendable data that aren't ready to send since they would cause busy looping.
71     // 到底返回的这个pollTimeout是啥，我觉得用英文的注释解释比较清楚
72     // 1.The amount of time to block if there is nothing to do
73     // 2.waiting for a channel to become ready; if zero, block indefinitely;
74     long pollTimeout = Math.min(result.nextReadyCheckDelayMs, notReadyTimeout);
75     if (!result.readyNodes.isEmpty()) {
76         log.trace("Nodes with data ready to send: {}", result.readyNodes);
77         // if some partitions are already ready to be sent, the select time would be 0;
78         // otherwise if some partition already has some data accumulated but not ready yet,
79         // the select time will be the time difference between now and its linger expiry time;
80         // otherwise the select time will be the time difference between now and the metadata expiry time;
81         pollTimeout = 0;
82     }
83 
84     // 发送消息
85     // 最后调用client.send()
86     sendProduceRequests(batches, now);
87     return pollTimeout;
88 }
复制代码
　　其中也需要了解这个方法：org.apache.kafka.clients.producer.internals.RecordAccumulator#ready。返回的类中3个关键参数的解释都在注释里。烦请看注释，我解释不好的地方可以看英文，原汁原味最好：

复制代码
 1 /**
 2  * Get a list of nodes whose partitions are ready to be sent, and the earliest time at which any non-sendable
 3  * partition will be ready; Also return the flag for whether there are any unknown leaders for the accumulated
 4  * partition batches.
 5  * <p>
 6  * A destination node is ready to send data if:
 7  * <ol>
 8  * <li>There is at least one partition that is not backing off its send
 9  * <li><b>and</b> those partitions are not muted (to prevent reordering if
10  *   {@value org.apache.kafka.clients.producer.ProducerConfig#MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION}
11  *   is set to one)</li>
12  * <li><b>and <i>any</i></b> of the following are true</li>
13  * <ul>
14  *     <li>The record set is full</li>
15  *     <li>The record set has sat in the accumulator for at least lingerMs milliseconds</li>
16  *     <li>The accumulator is out of memory and threads are blocking waiting for data (in this case all partitions
17  *     are immediately considered ready).</li>
18  *     <li>The accumulator has been closed</li>
19  * </ul>
20  * </ol>
21  */
22 /**
23  * @return ReadyCheckResult类的三个变量解释
24  * 1.Set<Node> readyNodes 准备好发送的节点
25  * 2.long nextReadyCheckDelayMs 下次检查节点的延迟时间
26  * 3.Set<String> unknownLeaderTopics 哪些topic找不到leader节点
27  *
28  * 一个节点满足以下任一条件则表示可以发送数据 
29  * 1.batch满了
30  * 2.batch没满，但是等了lingerMs的时间
31  * 3.accumulator满了
32  * 4.accumulator关了
33  */
34 public ReadyCheckResult ready(Cluster cluster, long nowMs) {
35     Set<Node> readyNodes = new HashSet<>();
36     long nextReadyCheckDelayMs = Long.MAX_VALUE;
37     Set<String> unknownLeaderTopics = new HashSet<>();
38     boolean exhausted = this.free.queued() > 0;
39     for (Map.Entry<TopicPartition, Deque<ProducerBatch>> entry : this.batches.entrySet()) {
40         TopicPartition part = entry.getKey();
41         Deque<ProducerBatch> deque = entry.getValue();
42         Node leader = cluster.leaderFor(part);
43         synchronized (deque) {
44             // leader没有且队列非空则添加unknownLeaderTopics
45             if (leader == null && !deque.isEmpty()) {
46                 // This is a partition for which leader is not known, but messages are available to send.
47                 // Note that entries are currently not removed from batches when deque is empty.
48                 unknownLeaderTopics.add(part.topic());
49 
50                 // 如果readyNodes不包含leader且muted不包含part
51                 // mute这个变量跟producer端的一个配置有关系：max.in.flight.requests.per.connection=1
52                 // 主要防止topic同分区下的消息乱序问题，限制了producer在单个broker连接上能够发送的未响应请求的数量
53                 // 如果设置为1，则producer在收到响应之前无法再给该broker发送该topic的PRODUCE请求
54             } else if (!readyNodes.contains(leader) && !muted.contains(part)) {
55                 ProducerBatch batch = deque.peekFirst();
56                 if (batch != null) {
57                     long waitedTimeMs = batch.waitedTimeMs(nowMs);
58                     boolean backingOff = batch.attempts() > 0 && waitedTimeMs < retryBackoffMs;
59                     // 等待时间
60                     long timeToWaitMs = backingOff ? retryBackoffMs : lingerMs;
61                     // batch满了
62                     boolean full = deque.size() > 1 || batch.isFull();
63                     // batch过期
64                     boolean expired = waitedTimeMs >= timeToWaitMs;
65                     boolean sendable = full || expired || exhausted || closed || flushInProgress();
66                     if (sendable && !backingOff) {
67                         readyNodes.add(leader);
68                     } else {
69                         long timeLeftMs = Math.max(timeToWaitMs - waitedTimeMs, 0);
70                         // Note that this results in a conservative estimate since an un-sendable partition may have
71                         // a leader that will later be found to have sendable data. However, this is good enough
72                         // since we'll just wake up and then sleep again for the remaining time.
73                         // 目前还没有leader，下次重试
74                         nextReadyCheckDelayMs = Math.min(timeLeftMs, nextReadyCheckDelayMs);
75                     }
76                 }
77             }
78         }
79     }
80     return new ReadyCheckResult(readyNodes, nextReadyCheckDelayMs, unknownLeaderTopics);
81 }
复制代码
　　还有一个方法就是org.apache.kafka.clients.producer.internals.RecordAccumulator#drain，从accumulator缓冲区获取要发送的数据，最大一次性发max.request.size大小的数据（最上面的配置参数里有）：

复制代码
  1 /**
  2  * Drain all the data for the given nodes and collate them into a list of batches that will fit within the specified
  3  * size on a per-node basis. This method attempts to avoid choosing the same topic-node over and over.
  4  *
  5  * @param cluster The current cluster metadata
  6  * @param nodes The list of node to drain
  7  * @param maxSize The maximum number of bytes to drain
  8  * maxSize也就是producer端配置参数max.request.size来控制的，一次最多发多少
  9  * @param now The current unix time in milliseconds
 10  * @return A list of {@link ProducerBatch} for each node specified with total size less than the requested maxSize.
 11  */
 12 public Map<Integer, List<ProducerBatch>> drain(Cluster cluster, Set<Node> nodes, int maxSize, long now) {
 13     if (nodes.isEmpty())
 14         return Collections.emptyMap();
 15     Map<Integer, List<ProducerBatch>> batches = new HashMap<>();
 16     for (Node node : nodes) {
 17         // for循环获取要发的batch
 18         List<ProducerBatch> ready = drainBatchesForOneNode(cluster, node, maxSize, now);
 19         batches.put(node.id(), ready);
 20     }
 21     return batches;
 22 }
 23 
 24 private List<ProducerBatch> drainBatchesForOneNode(Cluster cluster, Node node, int maxSize, long now) {
 25     int size = 0;
 26     // 获取node的partition
 27     List<PartitionInfo> parts = cluster.partitionsForNode(node.id());
 28     List<ProducerBatch> ready = new ArrayList<>();
 29     /* to make starvation less likely this loop doesn't start at 0 */
 30     // 避免每次都从一个partition取，要雨露均沾
 31     int start = drainIndex = drainIndex % parts.size();
 32     do {
 33         PartitionInfo part = parts.get(drainIndex);
 34         TopicPartition tp = new TopicPartition(part.topic(), part.partition());
 35         this.drainIndex = (this.drainIndex + 1) % parts.size();
 36 
 37         // Only proceed if the partition has no in-flight batches.
 38         if (isMuted(tp, now))
 39             continue;
 40 
 41         Deque<ProducerBatch> deque = getDeque(tp);
 42         if (deque == null)
 43             continue;
 44 
 45         // 加锁，不用说了吧
 46         synchronized (deque) {
 47             // invariant: !isMuted(tp,now) && deque != null
 48             ProducerBatch first = deque.peekFirst();
 49             if (first == null)
 50                 continue;
 51 
 52             // first != null
 53             // 查看是否在backoff期间
 54             boolean backoff = first.attempts() > 0 && first.waitedTimeMs(now) < retryBackoffMs;
 55             // Only drain the batch if it is not during backoff period.
 56             if (backoff)
 57                 continue;
 58 
 59             // 超过maxSize且ready里有东西
 60             if (size + first.estimatedSizeInBytes() > maxSize && !ready.isEmpty()) {
 61                 // there is a rare case that a single batch size is larger than the request size due to
 62                 // compression; in this case we will still eventually send this batch in a single request
 63                 // 有一种特殊的情况，batch的大小超过了maxSize，且batch是空的。也就是一个batch大小直接大于一次发送的maxSize
 64                 // 这种情况下最终还是会发送这个batch在一次请求
 65                 break;
 66             } else {
 67                 if (shouldStopDrainBatchesForPartition(first, tp))
 68                     break;
 69 
 70                 // 这块配置下面会讲
 71                 boolean isTransactional = transactionManager != null ? transactionManager.isTransactional() : false;
 72                 ProducerIdAndEpoch producerIdAndEpoch =
 73                     transactionManager != null ? transactionManager.producerIdAndEpoch() : null;
 74                 ProducerBatch batch = deque.pollFirst();
 75                 if (producerIdAndEpoch != null && !batch.hasSequence()) {
 76                     // If the batch already has an assigned sequence, then we should not change the producer id and
 77                     // sequence number, since this may introduce duplicates. In particular, the previous attempt
 78                     // may actually have been accepted, and if we change the producer id and sequence here, this
 79                     // attempt will also be accepted, causing a duplicate.
 80                     //
 81                     // Additionally, we update the next sequence number bound for the partition, and also have
 82                     // the transaction manager track the batch so as to ensure that sequence ordering is maintained
 83                     // even if we receive out of order responses.
 84                     batch.setProducerState(producerIdAndEpoch, transactionManager.sequenceNumber(batch.topicPartition), isTransactional);
 85                     transactionManager.incrementSequenceNumber(batch.topicPartition, batch.recordCount);
 86                     log.debug("Assigned producerId {} and producerEpoch {} to batch with base sequence " +
 87                             "{} being sent to partition {}", producerIdAndEpoch.producerId,
 88                         producerIdAndEpoch.epoch, batch.baseSequence(), tp);
 89 
 90                     transactionManager.addInFlightBatch(batch);
 91                 }
 92                 // 添加batch，并且close
 93                 batch.close();
 94                 size += batch.records().sizeInBytes();
 95                 ready.add(batch);
 96 
 97                 batch.drained(now);
 98             }
 99         }
100     } while (start != drainIndex);
101     return ready;
102 }
复制代码
回到顶部
三.幂等性producer
　　上面说到一个参数，enable.idempotence。0.11.0.0版本引入的幂等性producer表示它的发送操作是幂等的，也就是说，不会存在各种错误导致的重复消息。（比如说瞬时的发送错误可能导致producer端出现重试，同一个消息可能发送多次）

　　producer发送到broker端的每批消息都会有一个序列号（用于去重），Kakfa会把这个序列号存在底层日志，保存序列号只需要几个字节，开销很小。producer端会分配一个PID，对于PID、分区和序列号的关系，可以想象成一个哈希表，key就是（PID，分区），value就是序列号。比如第一次给broker发送((PID=1，分区=1),序列号=2)，第二次发送的value比2小或者等于2，则broker会拒绝PRODUCE请求，实现去重。

　　这个只能保证单个producer实例的EOS语义