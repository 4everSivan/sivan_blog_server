package com.sivan.blog.vo.Params;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/11 15:04
 * 用户中心分页
 */
@Data
public class PageParam {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long Id;

    private Integer currentPage;

    private Integer pageSize;

    private String queryString;
}
