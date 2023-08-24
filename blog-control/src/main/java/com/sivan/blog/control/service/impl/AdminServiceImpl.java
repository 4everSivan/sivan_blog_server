package com.sivan.blog.control.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sivan.blog.control.mapper.AdminMapper;
import com.sivan.blog.control.model.params.PageParam;
import com.sivan.blog.control.pojo.Admin;
import com.sivan.blog.control.pojo.Permission;
import com.sivan.blog.control.service.IAdminService;
import com.sivan.blog.control.vo.PageResult;
import com.sivan.blog.control.vo.Result;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/11 15:54
 */

@Service
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;


    @Override
    public Admin findAdminByUser(String username) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername,username);
        queryWrapper.last("limit 1");
        Admin admin = adminMapper.selectOne(queryWrapper);
        return admin;
    }


    @Override
    public List<Permission> findPermissionByAdminId(Long adminId) {
//        SELECT * FROM my_permission where id in (select permission_id FROM my_admin_permission where admin_id = 1)
        return adminMapper.findPermissionByAdminId(adminId);
    }


    @Override
    public Result listUser(PageParam pageParam) {
        Page<Admin> page = new Page<>(pageParam.getCurrentPage(),pageParam.getPageSize());
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(pageParam.getQueryString())){
            queryWrapper.eq(Admin::getUsername,pageParam.getQueryString());
        }
        Page<Admin> permissionPage = adminMapper.selectPage(page, queryWrapper);
        PageResult<Admin> pageResult = new PageResult<>();
        pageResult.setList(permissionPage.getRecords());
        pageResult.setTotal(permissionPage.getTotal());
        return Result.success(pageResult);
    }

    @Override
    public Result userInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Admin admin = this.findAdminByUser(authentication.getName());
        return Result.success(admin);
    }

    @Override
    public Result updateAdmin(Admin admin) {
        Admin newAdmin = adminMapper.selectById(admin.getId());
        if (!newAdmin.getPassword().equals(admin.getPassword())){
            newAdmin.setPassword(new BCryptPasswordEncoder().encode(admin.getPassword()));
        }
        admin.setDescription(admin.getDescription());
        adminMapper.updateById(newAdmin);
        return Result.success(null);

    }
}
