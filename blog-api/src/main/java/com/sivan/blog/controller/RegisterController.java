package com.sivan.blog.controller;

import com.sivan.blog.service.ILoginService;
import com.sivan.blog.service.IRegisterService;
import com.sivan.blog.vo.Params.LoginParam;
import com.sivan.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/2 15:48
 */

@RestController
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private IRegisterService registerService ;

    @PostMapping
    public Result register(@RequestBody LoginParam loginParam){
        //sso 单点登录 后期如果把登录注册功能提出去（单独的服务 可以提供接口服务）
        return registerService.register(loginParam);
    }
}
