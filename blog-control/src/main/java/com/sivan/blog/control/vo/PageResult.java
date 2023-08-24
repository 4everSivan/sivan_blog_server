package com.sivan.blog.control.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/11 15:14
 */
@Data
public class PageResult<T> {

    private List<T> list;

    private Long total;

}
