package com.sivan.blog.control.controller;

import com.sivan.blog.control.service.IWelcomeService;
import com.sivan.blog.control.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/4/10 19:15
 */

@RestController
@RequestMapping("welcome")
public class WelcomeController {

    @Autowired
    private IWelcomeService iWelcomeService;

    @PostMapping("statistical")
    public Result getStatistical(){
        return iWelcomeService.getStatistical();
    }
}
