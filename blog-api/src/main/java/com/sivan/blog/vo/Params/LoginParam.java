package com.sivan.blog.vo.Params;

import lombok.Data;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/1 14:40
 */

@Data
public class LoginParam {

    private String account;

    private String password;

    private String nickname;
}
