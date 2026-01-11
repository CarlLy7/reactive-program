package com.carl.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @description:
 * @author: carl
 * @createDate: 2026-01-11 15:16
 * @Since: 1.0
 */
@RestController
public class TestController {

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('add')")
    public Mono<String> hello(){
        return Mono.just("Hello world");
    }


    @GetMapping("/update")
    @PreAuthorize("hasAuthority('update')")
    public Mono<String> update(){
        return Mono.just("update");
    }

    @GetMapping("/query")
    @PreAuthorize("hasAuthority('query')")
    public Mono<String> query(){
        return Mono.just("query");
    }
}
