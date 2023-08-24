package com.sivan.blog.vo.Params;

import lombok.Data;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/1/16 20:16
 */

@Data
public class PageParams {

    /**
     * 默认值为1
     */
    private int page = 1;

    private int pageSize = 10;

    private Long categoryId;

    private Long tagId;

    private String year;

    private String month;

    public String getMonth() {
        if (this.month != null && this.month.length() == 1) {
            return "0" + this.month;
        }
        return this.month;
    }
}
