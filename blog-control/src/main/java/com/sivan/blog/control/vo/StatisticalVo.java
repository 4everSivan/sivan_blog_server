package com.sivan.blog.control.vo;

import lombok.Data;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/4/10 19:11
 * 统计功能的数据
 */

@Data
public class StatisticalVo {

    private Integer countArticle;

    private Integer countView;

    private Integer countMessage;
}
