package com.sivan.blog.service;

import com.sivan.blog.vo.Params.MessageParam;
import com.sivan.blog.vo.Result;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/19 16:11
 */

public interface IMessageService {

    /**
     * 保存评论
     * @param messageParam
     * @return
     */
    Result saveMessage(MessageParam messageParam);


    /**
     * 评论列表
     * @return
     */
    Result listMessage();
}
