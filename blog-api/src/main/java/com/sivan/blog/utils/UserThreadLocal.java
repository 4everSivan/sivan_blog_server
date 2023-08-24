package com.sivan.blog.utils;

import com.sivan.blog.dao.pojo.SysUser;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/2 19:45
 */
public class UserThreadLocal {

    private UserThreadLocal(){}

    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void set(SysUser sysUser){
        LOCAL.set(sysUser);
    }

    public static SysUser get(){
        return LOCAL.get();
    }

    public static void remove(){
        LOCAL.remove();
    }

}
