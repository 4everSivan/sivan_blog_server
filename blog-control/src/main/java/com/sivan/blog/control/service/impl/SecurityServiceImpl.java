package com.sivan.blog.control.service.impl;

import com.sivan.blog.control.pojo.Admin;
import com.sivan.blog.control.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/11 15:51
 */

@Component
public class SecurityServiceImpl implements UserDetailsService {

    @Autowired
    private IAdminService adminService;
    /**
     * 1. 登录时会把username传递到这个方法
     * 2. 通过username 查询 admin表 如果password存在 将密码传递给 SpringSecurity
     * 3. 如果不存在 认证失败
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = this.adminService.findAdminByUser(username);
        if (admin == null){
            //登录失败
            return null;
        }
        UserDetails userDetails = new User(username,admin.getPassword(),new ArrayList<>());
        return userDetails;
    }
}
