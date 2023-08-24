package com.sivan.blog.controller;

import com.sivan.blog.service.ILoginService;
import com.sivan.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/1 20:33
 */

@RestController
@RequestMapping("logout")
public class LogoutController {

    @Autowired
    private ILoginService loginService;

    @GetMapping
    public Result logout(@RequestHeader("Authorization") String token){
        return loginService.logout(token);
    }
}
