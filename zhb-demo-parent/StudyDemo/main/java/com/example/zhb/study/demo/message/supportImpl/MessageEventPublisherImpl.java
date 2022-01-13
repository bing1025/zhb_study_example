package com.example.zhb.study.demo.message.supportImpl;

import com.example.zhb.study.demo.common.util.SpringContextUtil;
import com.example.zhb.study.demo.message.support.MessageEvent;
import com.example.zhb.study.demo.message.support.MessageEventPublisher;
import com.example.zhb.study.demo.message.support.MessageListener;
import com.example.zhb.study.demo.message.support.SupportMsgEvents;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

/***
 *
 * @author Xiaojie.Li
 * @description 消息事件发布器
 * @date 2021/5/31
 */
@Slf4j
@Service("messageEventPublisher")
public class MessageEventPublisherImpl implements MessageEventPublisher, ApplicationListener<ContextRefreshedEvent> {

    @Resource
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /*@Resource
    AfterCommitExecutor afterCommitExecutor;*/

    /**
     * 已注册的ToDoListener
     */
    static final Set<MessageListener> LISTENERS = new LinkedHashSet<>();

    static final ConcurrentHashMap<Class<? extends MessageEvent>, Set<MessageListener>> LISTENER_CACHE = new ConcurrentHashMap<>();

    static final LinkedBlockingQueue<MessageEvent> SYNC_EVENTS = new LinkedBlockingQueue<>(50000);

    static volatile boolean IS_CLOSED = false;

    final Thread worker = new Thread(() -> {
        while (!IS_CLOSED) {
            work();
        }
    });

    @PostConstruct
    public void init() {
        start();
    }

    @Override
    public boolean publishEvent(MessageEvent event) {
        return publishEvent(event, false);
    }

    @Override
    public boolean publishEvent(MessageEvent event, boolean isSync) {

        if (!IS_CLOSED) {
            if (event == null) {
                throw new IllegalArgumentException("null event");
            }
            try {
                doPublish(event, isSync);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return false;
            }
            return true;
        }
        return false;
    }

    public void close() {
        clean(worker);
    }

    /**
     * 给外部调用的方法
     * @param event
     * @param isSync
     */
    private void doPublish(MessageEvent event, boolean isSync) {
        if (isSync) {
            execute(event, true);
        } else {
            // afterCommitExecutor.execute(() -> SYNC_EVENTS.offer(event));
        }
    }

    private void clean(Thread state) {
        IS_CLOSED = true;
        log.warn("progress shout down,");

        while (state.getState() != Thread.State.TERMINATED) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }

        log.warn("");
        while (SYNC_EVENTS.size() > 0) {
            work();
        }
        log.warn("");
    }


    private void registerShutdownHook(Thread state) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> clean(state)));
    }

    private void start() {
        registerShutdownHook(worker);
        worker.start();
    }

    private void work() {
        MessageEvent event;
        try {
            event = SYNC_EVENTS.take();
            execute(event, false);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void execute(MessageEvent event, boolean isSync) {
        val messageListeners = LISTENER_CACHE.getOrDefault(event.getClass(),new HashSet<>());
        if (!CollectionUtils.isEmpty(messageListeners)) {

            for (MessageListener messageListener : messageListeners) {
                execute(event, messageListener, isSync);
            }
        }
    }

    private void execute(MessageEvent event, MessageListener messageListener, boolean isSync) {
        if (isSync) {
            doExecute(event, messageListener);
        } else {
            threadPoolTaskExecutor.submit(new EventTask(event, messageListener));
        }
    }

    private void doExecute(MessageEvent event, MessageListener messageListener) {
        // 具体的 实现细节，各子类单独实现各自业务
        messageListener.doMsg(event);
    }

    /**
     *Spring容器初始化完成后，调用BeanPostProcessor这个类，这个类实现ApplicationListener接口，重写onApplicationEvent方法，
     *
     * 方法中就是我们自己要在容器初始化完成后加载的数据或者缓存
     *
     * 将 LISTENERS 和 LISTENER_CACHE 均初始化
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        resolveSupportListListeners();
    }

    private void resolveSupportListListeners() {

        // 查找 包含 SupportMsgEvents 注解的类
        Collection<Object> objects = SpringContextUtil.getBeansWithAnnotation(SupportMsgEvents.class).values();

        if (!CollectionUtils.isEmpty(objects)) {
            LISTENERS.addAll(objects.stream().map((Object o) -> (MessageListener) o).collect(Collectors.toList()));
        }

        for (MessageListener listener : LISTENERS) {
            SupportMsgEvents supportMsgEvents = AopUtils.getTargetClass(listener).getDeclaredAnnotation(SupportMsgEvents.class);
            if (supportMsgEvents != null) {
                Class<? extends MessageEvent>[] supportEvs = supportMsgEvents.value();

                for (Class<? extends MessageEvent> supportEv : supportEvs) {
                    Set<MessageListener> listenerSet = LISTENER_CACHE.get(supportEv);

                    if (listenerSet == null) {
                        listenerSet = new HashSet<>();
                    }

                    listenerSet.add(listener);
                    LISTENER_CACHE.put(supportEv, listenerSet);
                }
            }
        }
    }

    private void retry(MessageEvent event) {
        if (event != null && !event.isExceedMax() && !IS_CLOSED) {
            event.retryAgain();
            this.publishEvent(event);
        }
    }

    private class EventTask implements Callable<Boolean> {

        MessageListener listener;
        MessageEvent event;

        public EventTask(MessageEvent event, MessageListener listener) {
            this.event = event;
            this.listener = listener;
        }

        @Override
        public Boolean call() {
            try {
                doExecute(event, listener);
            } catch (Throwable t) {
                log.error(t.getMessage(), t);
                retry(event);
                return false;
            }
            return true;
        }
    }
}
