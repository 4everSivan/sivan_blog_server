package com.sivan.blog.controller;

import com.sivan.blog.service.IMessageService;
import com.sivan.blog.vo.Params.MessageParam;
import com.sivan.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/19 16:02
 */

@RestController
@RequestMapping("message")
public class MessageController {

    @Autowired
    private IMessageService messageService;

    @PostMapping("saveMessage")
    public Result saveMessage(@RequestBody MessageParam messageParam){
        return messageService.saveMessage(messageParam);
    }
    
    @GetMapping("listMessage")
    public Result listMessage(){
        return messageService.listMessage();
    }
}
