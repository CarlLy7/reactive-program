package com.carl.repository;

import com.carl.model.TBook;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * @description:
 * @author: carl
 * @date: 2026.01.09
 * @Since: 1.0
 */
@Repository
public interface TBookRepository extends R2dbcRepository<TBook,Integer> {

    @Query("""
            SELECT b.*,u.name as authorName 
            FROM t_book  b
            JOIN t_user u on b.author_id=u.id
            where b.id=:bookId
            """)
    Mono<TBook> getBookById(@Param("bookId") int bookId);

}
