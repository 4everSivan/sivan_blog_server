package com.sivan.blog.service;

import com.sivan.blog.dao.pojo.SysUser;
import com.sivan.blog.vo.Result;
import com.sivan.blog.vo.UserVo;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/1/16 21:37
 */
public interface ISysUserService {

    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    /**
     * 根据 token 查询用户信息
     * @param token
     */
    Result findUserByToken(String token);

    /**
     * 根据 账户 查找用户
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);

    /**
     * 保存用户
     * @param sysUser
     */
    void save(SysUser sysUser);


    /**
     * 根据id获取UserVo对象
     * @param id
     * @return
     */
    UserVo findUserVoById(Long id);


    Result selectOne(Long id);
}
