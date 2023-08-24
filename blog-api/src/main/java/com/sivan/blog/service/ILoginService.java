package com.sivan.blog.service;

import com.sivan.blog.dao.pojo.SysUser;
import com.sivan.blog.vo.Params.LoginParam;
import com.sivan.blog.vo.Result;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/1 14:38
 */


public interface ILoginService {

    /**
     * 登录
     * @param loginParam
     * @return
     */
    Result login(LoginParam loginParam);

    /**
     * 校验token
     * @param token
     * @return
     */
    SysUser checkToken(String token);

    /**
     * 登出
     * @param token
     * @return
     */
    Result logout(String token);
}
