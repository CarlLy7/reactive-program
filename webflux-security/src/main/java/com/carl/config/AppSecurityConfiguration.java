package com.carl.config;

import com.carl.components.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @description: Security自定义配置
 * @author: carl
 * @createDate: 2026-01-11 15:14
 * @Since: 1.0
 */
@Configuration
@EnableReactiveMethodSecurity  //开启基于方法注解的权限认证
public class AppSecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;


    @Bean
    SecurityWebFilterChain springSecurityWebFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange(authorize -> {
            //放行所有的静态资源
            authorize.matchers(PathRequest.toStaticResources().atCommonLocations()).permitAll();
            //拦截所有请求
            authorize.anyExchange().authenticated();
        });

        //禁用csrf
        http.csrf(csrfSpec -> {
            csrfSpec.disable();
        });

        //使用表单登录
        http.formLogin();

        return http.build();
    }


    /**
     * 配置认证管理器
     * @return
     */
    @Bean
    public ReactiveAuthenticationManager authenticationManager() {
        UserDetailsRepositoryReactiveAuthenticationManager manager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsServiceImpl);
        manager.setPasswordEncoder(passwordEncoder());
        return manager;
    }

}
