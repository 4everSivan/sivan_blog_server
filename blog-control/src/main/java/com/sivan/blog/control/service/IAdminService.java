package com.sivan.blog.control.service;

import com.sivan.blog.control.model.params.PageParam;
import com.sivan.blog.control.pojo.Admin;
import com.sivan.blog.control.pojo.Permission;
import com.sivan.blog.control.vo.Result;

import java.util.List;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/11 15:54
 */


public interface IAdminService {

    Admin findAdminByUser(String username);

    List<Permission> findPermissionByAdminId(Long id);

    Result listUser(PageParam pageParam);

    Result userInfo();

    Result updateAdmin(Admin admin);
}
