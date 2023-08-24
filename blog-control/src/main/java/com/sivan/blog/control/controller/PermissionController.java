package com.sivan.blog.control.controller;

import com.sivan.blog.control.model.params.PageParam;
import com.sivan.blog.control.pojo.Permission;
import com.sivan.blog.control.service.IPermissionService;
import com.sivan.blog.control.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/11 15:00
 */

@RestController
@RequestMapping("admin")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @PostMapping("permission/permissionList")
    public Result listPermission(@RequestBody PageParam pageParam){
        return permissionService.listPermission(pageParam);
    }

    @PostMapping("permission/add")
    public Result add(@RequestBody Permission permission){
        return permissionService.add(permission);
    }

    @PostMapping("permission/update")
    public Result update(@RequestBody Permission permission){
        return permissionService.update(permission);
    }

    @GetMapping("permission/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return permissionService.delete(id);
    }
}
