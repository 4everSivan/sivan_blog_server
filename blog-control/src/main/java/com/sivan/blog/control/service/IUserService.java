package com.sivan.blog.control.service;

import com.sivan.blog.control.model.params.PageParam;
import com.sivan.blog.control.vo.Result;
import com.sivan.blog.control.vo.SysUserVo;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/4/2 15:56
 */
public interface IUserService {

    /**
     * 查询普通用户
     * @return
     */
    Result listUser(PageParam pageParam);

    Result deleteUser(Long id);

    Result updateUser(SysUserVo sysUserVo);

    Result searchUser(String username);
}
