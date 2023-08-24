package com.sivan.blog.controller;

import com.sivan.blog.dao.pojo.SysUser;
import com.sivan.blog.utils.UserThreadLocal;
import com.sivan.blog.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/2 19:36
 */

@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping
    public Result test(){
        SysUser sysUser = UserThreadLocal.get();
        return Result.success(null);
    }
}
