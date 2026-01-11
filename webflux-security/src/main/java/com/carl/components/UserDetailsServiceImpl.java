package com.carl.components;

import jakarta.annotation.Resource;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: carl
 * @createDate: 2026-01-11 16:41
 * @Since: 1.0
 */
@Component
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {
    @Resource
    private DatabaseClient databaseClient;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return databaseClient.sql("""
                SELECT
                  u.id,
                  u.username,
                  u.`password`,
                  tr.`name`,
                  tr.`value`,
                  p.`value` as pvalue,
                  p.description
                FROM
                  t_user u
                  LEFT JOIN t_user_role ur ON u.id = ur.user_id
                  LEFT JOIN t_roles tr ON tr.id = ur.role_id
                  LEFT JOIN t_role_perm rp ON rp.role_id = tr.id
                  LEFT JOIN t_perm p ON p.id = rp.perm_id
                WHERE
                  u.username = ?
                limit 1
                """)
                .bind(0,username)
                .fetch()
                .one()
                .map(item->{
                    List<GrantedAuthority> grantedAuthorityList=new ArrayList<>();
                    grantedAuthorityList.add(new SimpleGrantedAuthority("add"));
                    grantedAuthorityList.add(new SimpleGrantedAuthority("query"));
                    return User.builder()
                            .username(username)
                            .password(item.get("password").toString())
                            .authorities(grantedAuthorityList)
                            .build();
                });
    }
}
