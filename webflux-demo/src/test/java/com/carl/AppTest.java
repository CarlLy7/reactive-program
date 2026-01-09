package com.carl;


import com.carl.domain.BookVo;
import com.carl.domain.UserBookVo;
import com.carl.model.TUser;
import com.carl.repository.TBookRepository;
import com.carl.repository.TUserRepository;
import io.asyncer.r2dbc.mysql.MySqlConnectionConfiguration;
import io.asyncer.r2dbc.mysql.MySqlConnectionFactory;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Unit test for simple App.
 */
@SpringBootTest
public class AppTest {
    @Test
    public void testR2dbc() {
        MySqlConnectionConfiguration mySqlConnectionConfiguration = MySqlConnectionConfiguration.builder()
                .host("localhost")
                .port(3306)
                .database("carl")
                .username("root")
                .password("123456")
                .build();
        MySqlConnectionFactory connectionFactory = MySqlConnectionFactory.from(mySqlConnectionConfiguration);

        Mono.from(connectionFactory.create())
                .flatMapMany(mySqlConnection -> mySqlConnection
                        .createStatement("SELECT * FROM t_user WHERE id= ?id")
                        .bind("id", 1)
                        .execute())
                .flatMap(mySqlResult -> mySqlResult.map((row, rowMetadata) -> {
                    Integer id = row.get("id", Integer.class);
                    String name = row.get("name", String.class);
                    return new TUser(id, name, null);
                }))
                .doOnNext(System.out::println)
                .doOnError(System.err::println)
                .subscribe();
        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Resource
    private TUserRepository userRepository;

    @Test
    public void testR2dbc2() {
        userRepository.findByIdInAndNameLike(Arrays.asList(1, 2), "ca%")
                .subscribe(System.out::println);

        userRepository.selectByName("c%").subscribe(v -> System.out.println("dddd:" + v));

        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Resource
    private TBookRepository tBookRepository;

    @Test
    public void testR2dbc3() {
        tBookRepository.getBookById(1)
                .subscribe(System.out::println);
        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Resource
    private DatabaseClient databaseClient;

    /**
     * 1-N 查询,如果是返回的VO的话，需要使用偏底层的DatabaseClient来查询然后再进行自定义封装1-N的实体
     */
    @Test
    public void testR2dbc4() {
        databaseClient.sql("""
                        SELECT
                               u.*,
                               b.id AS bookId,
                               b.title
                             FROM
                               t_user u
                               JOIN t_book b ON u.id = b.author_id
                        """)
//                .bind(0,1)
                .fetch()
                .all()
                //通过用户的Id进行分组，得到一个Flux<GroupFlux<>>
                .groupBy(rowResult -> rowResult.get("id"))
                .flatMap(groupFlux -> {
                    ////groupFlux 手机起来就成了：Mono<List<Map<String,Object>>>
                    return groupFlux.collectList()
                            .flatMap(rows -> {
                                Map<String, Object> firstMap = rows.get(0);
                                UserBookVo userBookVo = new UserBookVo();
                                userBookVo.setId((Integer) firstMap.get("id"));
                                userBookVo.setName((String) firstMap.get("name"));
                                List<BookVo> bookVoList = rows.stream().map(item -> {
                                    BookVo bookVo = new BookVo();
                                    bookVo.setId((Integer) item.get("bookId"));
                                    bookVo.setTitle((String) item.get("title"));
                                    return bookVo;
                                }).collect(Collectors.toList());
                                userBookVo.setBooks(bookVoList);
                                return Mono.just(userBookVo);
                            });
                })
                .subscribe(res -> System.out.println("res===" + res));

        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
