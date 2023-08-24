package com.sivan.blog.control.controller;

import com.sivan.blog.control.model.params.PageParam;
import com.sivan.blog.control.pojo.Admin;
import com.sivan.blog.control.service.IAdminService;
import com.sivan.blog.control.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/18 16:19
 */

@RestController
@RequestMapping("adminUser")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @PostMapping("listUser")
    public Result listUser(@RequestBody PageParam pageParam){
        return adminService.listUser(pageParam);
    }

    @PostMapping("user/userInfo")
    public Result userInfo(){
        return adminService.userInfo();
    }

    @PostMapping("update")
    public Result updateAdmin(@RequestBody Admin admin){
        return adminService.updateAdmin(admin);
    }
}
