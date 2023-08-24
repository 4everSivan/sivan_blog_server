package com.sivan.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/1/16 20:41
 */

@Data

public class ArticleVo {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    private Integer weight;
    /**
     * 创建时间
     */
    private String createDate;

    private String author;

    private String avatar;

    /**
     *
     * 编辑页 作者id
     */
    private Long authorId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long bodyId;

    private ArticleBodyVo body;

    private List<TagVo> tags;

    private CategoryVo category;

    private Integer primaryId;

}
