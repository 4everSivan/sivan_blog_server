package com.sivan.blog.dao.pojo;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/1/16 20:05
 */
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class Article {

    public static final int ARTICLE_TOP = 1;

    public static final int ARTICLE_COMMON = 0;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    /**
     * 作者id
     */
    private Long authorId;
    /**
     * 内容id
     */
    private Long bodyId;
    /**
     *类别id
     */
    private Long categoryId;

    /**
     * 置顶
     */
    private Integer weight;


    /**
     * 创建时间
     */
    private Long createDate;

    /**
     * 是否私密
     */
    private Integer primaryId;
}
