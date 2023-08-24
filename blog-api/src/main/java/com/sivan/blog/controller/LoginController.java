package com.sivan.blog.controller;

import com.sivan.blog.service.ILoginService;
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
 * @date 2022/3/1 14:35
 */

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private ILoginService loginService;

    @PostMapping
    public Result login(@RequestBody LoginParam loginParam){
        return loginService.login(loginParam);
    }
}
