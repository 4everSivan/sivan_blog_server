package com.sivan.blog.vo;

import lombok.Data;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/3 15:12
 */

@Data
public class CategoryVo {

    private Long id;

    private String avatar;

    private String categoryName;

    private String description;
}
