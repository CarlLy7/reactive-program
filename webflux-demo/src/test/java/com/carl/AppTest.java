package com.carl;


import com.carl.model.TUser;
import com.carl.repository.TBookRepository;
import com.carl.repository.TUserRepository;
import io.asyncer.r2dbc.mysql.MySqlConnectionConfiguration;
import io.asyncer.r2dbc.mysql.MySqlConnectionFactory;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Arrays;

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
                    return new TUser(id, name);
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

        userRepository.selectByName("c%").subscribe(v-> System.out.println("dddd:"+v));

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
}
