package com.sivan.blog.control.pojo;

import lombok.Data;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/4 14:46
 */

@Data
public class Comment {

    private Long id;

    private String content;

    private Long createDate;

    private Long articleId;

    private Long authorId;

    private Long parentId;

    private Long toUid;

    private Integer level;

}
