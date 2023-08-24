package com.sivan.blog.dao.doc;

import lombok.Data;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/2/28 19:59
 */

@Data
public class Archives {

    private Integer year;

    private Integer month;

    private Long count;
}
