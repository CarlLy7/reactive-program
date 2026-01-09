package com.carl.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: carl
 * @date: 2026.01.09
 * @Since: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBookVo {
    private Integer id;
    private String name;

    private List<BookVo> books;
}
