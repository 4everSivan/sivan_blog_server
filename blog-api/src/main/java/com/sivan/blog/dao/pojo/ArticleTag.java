package com.sivan.blog.dao.pojo;

import lombok.Data;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/5 15:07
 */

@Data
public class ArticleTag {

    private Long id;

    private Long articleId;

    private Long tagId;
}
