package com.example.zhb.study.demo.day27;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * CompletableFuture执行多个异步任务,将结果合并返回
 * @Author: zhouhb
 * @date: 2023/06/15/11:17
 * @Description:
 */
public class CompletableFutureTest {

    public static void main(String[] args) {
        try {
            System.out.println("==="+test());
            TestFindPrice();
            testFindPrice2();
            testFindPrice3();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static  Map<String, String> test() throws InterruptedException, ExecutionException {
            // 不存在并发插入情况，不需要使用ConcurrentHashMap
//		Map<String, String> data = new ConcurrentHashMap<>(3);
            Map<String, String> data = new HashMap<>(3);
            //第一个任务。
            CompletableFuture<String> task01 = CompletableFuture.supplyAsync(() -> {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "task01";
            });
            //第二个任务。
            CompletableFuture<String> task02 = CompletableFuture.supplyAsync(() -> {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "task02";
            });
            // 第三个任务
            CompletableFuture<String> task03 = CompletableFuture.supplyAsync(() -> {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "task03";
            });
            // get()方法会阻塞
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
            data.put("task01",task01.get());
            System.out.printf("task01执行完毕;当前时间:%s\n",formatter.format(LocalDateTime.now()));
            data.put("task02",task02.get());
            System.out.printf("task02执行完毕;当前时间:%s\n",formatter.format(LocalDateTime.now()));
            data.put("task03",task03.get());
            System.out.printf("task03执行完毕;当前时间:%s\n",formatter.format(LocalDateTime.now()));
            return data;
        }


    private static Double calculatePrice(String product) {
        Double value = 0.0;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        switch (product) {
            case "Apple":
                value = 10.0;
                break;
            case "Banana":
                value = 5.0;
                break;
            default:
                break;
        }
        return value;
    }

    // 使用 CompletableFuture 发起异步请求
    public static void  TestFindPrice() {
        List<String> list = Arrays.asList("Apple", "Banana");
        List<CompletableFuture<String>> priceFuture =
                list.stream()
                        .map(a -> CompletableFuture.supplyAsync(
                                () -> a + " price is:" + calculatePrice(a)))
                        .collect(Collectors.toList());
        List<String> resultList = priceFuture.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        resultList.forEach(System.out::println);
    }

    /**
     * 俩子任务返回结果后执行下一个子任务
     */
    public void ConsolidationResultExec()throws Exception{

        CompletableFuture<Object> cf = CompletableFuture.supplyAsync(()-> t1())
                .thenCombineAsync(CompletableFuture.supplyAsync(()-> t2()), (t1, t2)-> t3(t1, t2));
        cf.get(10, TimeUnit.SECONDS);
    }

    public Object t1(){
        return null;
    }

    public Object t2(){
        return null;
    }

    public Object t3(Object t1, Object t2){
        return null;
    }


    // 构造同步和异步操作
    public static void testFindPrice2() {
        List<String> list = Arrays.asList("Apple", "Banana");
        ExecutorService executor = Executors.newCachedThreadPool();
        List<CompletableFuture<String>> priceFutures =
                list.stream()
                        .map(a -> CompletableFuture.supplyAsync(
                                () -> calculatePrice(a), executor))
                        .map(future -> future.thenApply(Quote::parse))
                        .map(future -> future.thenCompose(quote ->
                                CompletableFuture.supplyAsync(
                                        () -> Discount.applyDiscount(quote), executor)))
                        .collect(Collectors.toList());
        List<String> resultList = priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        resultList.forEach(System.out::println);
    }

    static class Quote {
        public static Double parse(Double val) {
            return val;
        }
    }

    static class Discount {
        public static String applyDiscount(Double val) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "price is:" + val;
        }
    }

    // 将两个 CompletableFuture 对象整合起来，无论它们是否存在依赖
    public static void testFindPrice3() {
        Future<Double> futurePrice =
                CompletableFuture.supplyAsync(() -> calculatePrice("Apple"))
                        .thenCombine(
                                CompletableFuture.supplyAsync(
                                        () -> getRate()),
                                (price, rate) -> price * rate
                        );
        try {
            Double result = futurePrice.get();
            System.out.println(result);
        } catch (ExecutionException | InterruptedException ee) {
            // 计算抛出一个异常
        }
    }

    private static Double getRate() {
        return 0.8;
    }

}
