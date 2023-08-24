package com.sivan.blog.control.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/19 18:17
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 设置欢迎页
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:login.html");
    }
}
