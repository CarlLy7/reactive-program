package com.carl.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @description:
 * @author: carl
 * @date: 2026.01.09
 * @Since: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TBook {
    @Id
    private Integer id;

    private String title;


    /**
     * 作者信息
     */
    private TUser user;
}
