package com.carl.reactive;


import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * @description:
 * @author: carl
 * @date: 2025.12.30
 * @Since: 1.0
 */

public class AppDemo {
    static class MyProcessor extends SubmissionPublisher<String> implements Flow.Processor<String, String> {
        private Flow.Subscription subscription;

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            subscription.request(1);
        }

        @Override
        public void onNext(String item) {
            item += " :哈哈";
            //处理完后发送出去
            submit(item);
            //请求接收下一个数据
            subscription.request(1);
        }

        @Override
        public void onError(Throwable throwable) {
            System.out.println("处理错误：" + throwable.getMessage());
        }

        @Override
        public void onComplete() {
            close();
        }
    }

    public static void main(String[] args) {
        //创建发布者
        SubmissionPublisher publisher = new SubmissionPublisher();

        MyProcessor myProcessor = new MyProcessor();
        MyProcessor myProcessor2 = new MyProcessor();
        MyProcessor myProcessor3 = new MyProcessor();


        //创建订阅者
        Flow.Subscriber<String> subscriber = new Flow.Subscriber<String>() {
            private Flow.Subscription subscription;

            /**
             * 建立绑定关系
             * @param subscription a new subscription
             */
            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                System.out.println("订阅消息-----");
                subscription.request(1);

            }

            /**
             * 一接收到消息之后如何处理
             * @param item the item
             */
            @Override
            public void onNext(String item) {
                System.out.println("结果:" + item);
                //接收完之后，指定可以再接收处理多少个消息
                subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("接收到异常:" + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("处理完毕了----");
            }
        };
        //绑定
        publisher.subscribe(myProcessor);
        myProcessor.subscribe(myProcessor2);
        myProcessor2.subscribe(myProcessor3);
        myProcessor3.subscribe(subscriber);

        for (int i = 0; i < 10; i++) {
            publisher.submit("p->" + i);
        }

        //主线程不能停止，不然就完蛋了
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
