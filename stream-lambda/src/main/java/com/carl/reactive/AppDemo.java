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
    public static void main(String[] args) {
        //创建发布者
        SubmissionPublisher publisher = new SubmissionPublisher();
        //创建订阅者
        Flow.Subscriber<String> subscriber=new Flow.Subscriber<String>() {
            private Flow.Subscription subscription;
            /**
             * 建立绑定关系
             * @param subscription a new subscription
             */
            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                System.out.println("订阅消息-----");
                this.subscription=subscription;

            }

            /**
             * 一接收到消息之后如何处理
              * @param item the item
             */
            @Override
            public void onNext(String item) {
                System.out.println(item);
                //接收完之后，指定可以再接收处理多少个消息
                subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("接收到异常:"+throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("处理完毕了----");
            }
        };
        //绑定
        publisher.subscribe(subscriber);

        for (int i = 0; i < 10; i++) {
            publisher.submit("p->"+i);
        }
    }
}
