package com.sivan.blog.control.controller;

import com.sivan.blog.control.model.params.PageParam;
import com.sivan.blog.control.service.IUserService;
import com.sivan.blog.control.vo.Result;
import com.sivan.blog.control.vo.SysUserVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/4/2 15:53
 */

@RestController
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("listUser")
    public Result listUser(@RequestBody PageParam pageParam){
        return userService.listUser(pageParam);
    }

    @PostMapping("delete")
    public Result deleteUser(@RequestParam Long id){
        return userService.deleteUser(id);
    }

    @PostMapping("update")
    public Result updateUser(@RequestBody SysUserVo sysUserVo){
        return userService.updateUser(sysUserVo);
    }

    @PostMapping("search")
    public Result searchUser(@RequestParam String username){
        return userService.searchUser(username);
    }
}
