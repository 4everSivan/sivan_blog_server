package com.sivan.blog.dao.pojo;

import lombok.Data;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/3 15:05
 */

@Data
public class Category {

    private Long id;

    private String avatar;

    private String categoryName;

    private String description;
}
