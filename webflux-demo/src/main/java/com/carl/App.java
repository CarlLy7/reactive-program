package com.carl;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

import java.io.IOException;
import java.net.URI;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        HttpHandler handler = (ServerHttpRequest request, ServerHttpResponse response) -> {
            URI uri = request.getURI();
            System.out.println(uri.toString());
            DataBufferFactory bufferFactory = response.bufferFactory();
            DataBuffer buffer = bufferFactory.wrap(new String(uri.toString() + " Hello!").getBytes());
            return response.writeWith(Mono.just(buffer));
        };
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(handler);
        HttpServer.create().host("localhost").port(8080).handle(adapter).bindNow();
        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
