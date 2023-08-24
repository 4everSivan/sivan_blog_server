package com.sivan.blog.controller;

import com.sivan.blog.dao.pojo.SysUser;
import com.sivan.blog.service.IManagementService;
import com.sivan.blog.vo.Params.PageParam;
import com.sivan.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/5/21 21:36
 */

@RestController
@RequestMapping("management")
public class ManagementController {

    @Autowired
    private IManagementService managementService;

    @PostMapping("listMyArticle")
    public Result listMyArticle(@RequestBody PageParam pageParam){
        return managementService.listMyArticle(pageParam);
    }

    @PostMapping("updateUserInfo")
    public Result updateUserInfo(@RequestBody SysUser user){
        return managementService.updateUserInfo(user);
    }

    @PostMapping("statistical")
    public Result listStatistical(@RequestParam Long id){
        return managementService.listStatistical(id);
    }

    @PostMapping("search")
    public Result searchUserArticle(@RequestParam String title){
        return managementService.searchUserArticle(title);
    }
}
