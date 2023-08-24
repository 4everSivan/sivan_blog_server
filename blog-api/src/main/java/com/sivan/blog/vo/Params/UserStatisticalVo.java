package com.sivan.blog.vo.Params;

import lombok.Data;

/**
 * @author Sivan
 * @version 1.0
 * @Date 2022/5/25 17:23
 */

@Data
public class UserStatisticalVo {

    /**
     * 文章数
     */
    private int articleCount;

    /**
     * 浏览数
     */
    private int viewCount;

    /**
     * 评论数量
     */
    private int messageCount;

    /**
     * 注册天数
     */
    private Long registerDays;
}
