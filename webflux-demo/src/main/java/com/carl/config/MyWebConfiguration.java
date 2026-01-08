package com.carl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * @description:
 * @author: carl
 * @createDate: 2026-01-08 21:22
 * @Since: 1.0
 */
@Configuration
public class MyWebConfiguration {

    /**
     * 自定义webflux配置
     *
     * @return
     */
    @Bean
    public WebFluxConfigurer webFluxConfigurer() {
        return new WebFluxConfigurer() {
            /**
             * 配置跨域
             * @param registry
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedHeaders("*")
                        .allowedMethods("*")
                        .allowedOrigins("*");
            }
        };
    }

}
