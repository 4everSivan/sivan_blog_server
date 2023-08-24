package com.sivan.blog.handler;

import com.alibaba.fastjson.JSON;
import com.sivan.blog.dao.pojo.SysUser;
import com.sivan.blog.service.ILoginService;
import com.sivan.blog.utils.UserThreadLocal;
import com.sivan.blog.vo.ErrorCode;
import com.sivan.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 登录拦截器
 * @author Sivan
 * @version 1.0
 * @date 2022/3/2 19:12
 */

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private ILoginService loginService;

    /**
     *  在执行controller方法（handler）之前进行执行
     *  1. 需要判断 请求的接口路径 是否为 HandlerMethod (controller 方法)
     *  2. 判断 token 是否为空，如果为空 未登录
     *  3. 如果token 不为空，登录验证loginService checkToken
     *  4. 如果认证成功 放行
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)){
            // handler 可能是 RequestResourceHandler
            return true;
        }

        String token = request.getHeader("Authorization");

        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");

        if (StringUtils.isBlank(token)){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }

        UserThreadLocal.set(sysUser);
        return true;
    }

    /**
     * 如果不删除ThreadLocal中用完的信息 会有内存泄露的风险
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.remove();
    }
}
