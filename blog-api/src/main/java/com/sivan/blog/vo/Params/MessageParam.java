package com.sivan.blog.vo.Params;

import lombok.Data;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/19 18:23
 */

@Data
public class MessageParam {

    private String content;

    private String nickname;

    private Long parent;

    private String email;
}
