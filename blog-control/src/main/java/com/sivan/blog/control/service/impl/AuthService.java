package com.sivan.blog.control.service.impl;

import com.sivan.blog.control.pojo.Admin;
import com.sivan.blog.control.pojo.Permission;
import com.sivan.blog.control.service.IAdminService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/11 16:05
 */

@Service
public class AuthService {

    @Autowired
    private IAdminService adminService;

    /**
     * 权限认证
     * @param request
     * @param authentication
     * @return
     */
    public boolean auth(HttpServletRequest request, Authentication authentication){
        //请求路径
        String requestURI = request.getRequestURI();
        Object principal = authentication.getPrincipal();
        if (principal == null || "anonymousUser".equals(principal)){
            //未登录
            return false;
        }
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        Admin admin = adminService.findAdminByUser(username);
        if (admin == null){
            return false;
        }
        if (admin.getId() == 1){
            //超级管理员
            return true;
        }
        Long id = admin.getId();
        List<Permission> permissionList = this.adminService.findPermissionByAdminId(id);
        requestURI = StringUtils.split(requestURI,'?')[0];
        for (Permission permission : permissionList) {
            if (requestURI.equals(permission.getPath())){
                return true;
            }
        }
        return true;
    }
}
