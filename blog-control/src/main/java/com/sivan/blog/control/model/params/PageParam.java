package com.sivan.blog.control.model.params;

import lombok.Data;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/11 15:04
 */
@Data
public class PageParam {

    private Integer currentPage;

    private Integer pageSize;

    private String queryString;
}
