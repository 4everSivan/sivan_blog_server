package com.sivan.blog.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * HttpServletRequest 工具类
 * @author Sivan
 * @version 1.0
 * @date 2022/3/5 16:43
 */
public class HttpContextUtils {

    public  static HttpServletRequest getHttpServletRequest(){
        return (((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
    }
}
