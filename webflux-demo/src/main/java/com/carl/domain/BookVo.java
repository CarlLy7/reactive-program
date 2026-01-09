package com.carl.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @description:
 * @author: carl
 * @date: 2026.01.09
 * @Since: 1.0
 */
@Data
public class BookVo {
    @Id
    private Integer id;

    private String title;

    private Integer authorId;
}
