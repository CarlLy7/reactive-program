package com.carl.repository;

import com.carl.domain.BookVo;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * @description:
 * @author: carl
 * @date: 2026.01.09
 * @Since: 1.0
 */
@Repository
public interface BookReposity extends R2dbcRepository<BookVo,Integer> {

    Flux<BookVo> findByAuthorId(Integer authorId);
}
