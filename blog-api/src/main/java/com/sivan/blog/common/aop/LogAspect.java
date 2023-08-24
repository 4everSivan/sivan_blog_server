package com.sivan.blog.common.aop;

import com.alibaba.fastjson.JSON;
import com.sivan.blog.utils.HttpContextUtils;
import com.sivan.blog.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/5 16:24
 * <p>
 * Aspect: 切面 定义了通知和切点之间的关系
 */

@Component
@Aspect
@Slf4j
public class LogAspect {

    /**
     * 定义切点
     */
    @Pointcut("@annotation(com.sivan.blog.common.aop.LogAnnotation)")
    public void pt() {
    }

    /**
     * 环绕通知
     *
     * @param joinPoint
     * @return
     */
    @Around("pt()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {

        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = joinPoint.proceed();
        // 执行时常（毫秒）
        long time = System.currentTimeMillis() - beginTime;
        //保存日志
        recordLog(joinPoint, time);
        return result;
    }


    private void recordLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        log.info("========================log start==========================");
        log.info("module:{}", logAnnotation.module());
        log.info("operator:{}", logAnnotation.operator());

        // 请求方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.info("request method:{}", className + "." + methodName + "()");

        // 请求的参数
        Object[] args = joinPoint.getArgs();
        String params = null;
        if (args.length > 0) {
            params = JSON.toJSONString(args[0]);
        }
        log.info("params:{}", params);

        //获取request 设置IP地址
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        log.info("ip:{}", IpUtils.getIpAddr(request));

        log.info("excute time : {} ms", time);
        log.info("======================== log end ==========================");
    }

}
