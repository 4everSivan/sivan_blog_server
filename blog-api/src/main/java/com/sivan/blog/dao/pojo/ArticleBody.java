package com.sivan.blog.dao.pojo;

import lombok.Data;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/3 15:00
 */

@Data
public class ArticleBody {

    private Long id;

    private String content;

    private String contentHtml;

    private Long articleId;

}
