package com.sivan.blog.config;

import com.sivan.blog.handler.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/1/16 19:51
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    /**
     * 跨域配置
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
//                .allowedOrigins("http://localhost:8080");
//                .allowedOrigins("http://121.5.176.179:80")
//                .allowedOrigins("http://127.0.0.1:80");
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        /**
         * 拦截test接口，后续遇到需要拦截的接口再作修改
         */
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/test")
                .addPathPatterns("/comments/create/change")
                .addPathPatterns("/articles/publish");
    }

}
