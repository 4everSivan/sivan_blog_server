package com.sivan.blog.controller;

import com.sivan.blog.service.ISysUserService;
import com.sivan.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/1 20:03
 */

@RestController
@RequestMapping("users")
public class UsersController {

    @Autowired
    private ISysUserService sysUserService;

    @GetMapping("currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token ){
        return sysUserService.findUserByToken(token);
    }

    @PostMapping("selectOne")
    public Result selectOne(@RequestParam Long id){
        return sysUserService.selectOne(id);
    }

}
