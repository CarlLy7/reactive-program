package com.carl.repository;

import com.carl.model.TUser;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Collection;

/**
 * @description:
 * @author: carl
 * @date: 2026.01.09
 * @Since: 1.0
 */
@Repository
public interface TUserRepository extends R2dbcRepository<TUser,Integer> {

    Flux<TUser> findByIdInAndNameLike(Collection<Integer> ids, String name);

    @Query("select * from t_user where name like :name")
    Flux<TUser> selectByName(String name);
}
