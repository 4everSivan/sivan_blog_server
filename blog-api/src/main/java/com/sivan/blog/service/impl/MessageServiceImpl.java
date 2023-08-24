package com.sivan.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sivan.blog.dao.mapper.IMessageMapper;
import com.sivan.blog.dao.pojo.Message;
import com.sivan.blog.service.IMessageService;
import com.sivan.blog.vo.Params.MessageParam;
import com.sivan.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/19 16:14
 */

@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private IMessageMapper messageMapper;

    @Override
    public Result saveMessage(MessageParam messageParam) {
        Message message = new Message();
        message.setNickname(messageParam.getNickname());
        message.setContent(messageParam.getContent());
        message.setCreateDate(System.currentTimeMillis());
        message.setParentId(messageParam.getParent());
        message.setEmail(messageParam.getEmail());
        this.messageMapper.insert(message);
        return Result.success(null);
    }

    @Override
    public Result listMessage() {
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Message::getForeignId,0).orderByDesc(Message::getId);
        List<Message> messages = this.messageMapper.selectList(queryWrapper);
        return Result.success(messages);
    }

}
