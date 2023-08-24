package com.sivan.blog.vo.Params;

import lombok.Data;

import java.util.List;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/5/21 15:14
 */
@Data
public class PageResult<T> {

    private List<T> list;

    private Long total;

}
