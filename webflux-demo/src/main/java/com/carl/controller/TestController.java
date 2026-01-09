package com.carl.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @description:
 * @author: carl
 * @date: 2026.01.08
 * @Since: 1.0
 */
@RestController
public class TestController {

    @GetMapping("/hello")
    public Mono<String> hello(@RequestParam(value = "key")String key) {
        return Mono.just("Hello World "+key);
    }


    /**
     * 补充一下map和flatMap的区别
     * map:执行1：1的转换，转换过程是同步的
     * flatMap:执行1：N的转换，转换过程是异步的，当需要进行非阻塞I/O操作(如数据库调用、HTTP请求)时使用
     * @return
     */
    @GetMapping(value = "/sse",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    @CrossOrigin(origins = "*")
    public Flux<String> sse() {
        return Flux.range(1,10)
                .map(i->{
                    return "haha-"+i;
                })
                .delayElements(Duration.ofSeconds(1))
                ;

    }



}
