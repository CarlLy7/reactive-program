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
