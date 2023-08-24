package com.sivan.blog.service;

import com.sivan.blog.vo.Params.LoginParam;
import com.sivan.blog.vo.Result;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/2 15:53
 */

public interface IRegisterService {

    /**
     * 注册
     * @param loginParam
     * @return
     */
    Result register(LoginParam loginParam);
}
