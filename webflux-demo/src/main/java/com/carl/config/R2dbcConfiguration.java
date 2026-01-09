package com.carl.config;

import com.carl.converts.TBookConvert;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.MySqlDialect;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

/**
 * @description: 自定义的R2dbc的配置
 * @author: carl
 * @date: 2026.01.09
 * @Since: 1.0
 */
@Configuration
//开启R2dbc的仓库功能--JPA的实现
@EnableR2dbcRepositories
public class R2dbcConfiguration {

    /**
     * 增加类型转换器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public R2dbcCustomConversions r2dbcCustomConversions(){
        return R2dbcCustomConversions.of(MySqlDialect.INSTANCE,new TBookConvert());
    }

}
