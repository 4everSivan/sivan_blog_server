package com.sivan.blog.service;

import com.sivan.blog.vo.Params.CommentParam;
import com.sivan.blog.vo.Result;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/4 14:56
 */

public interface ICommentsService {

    /**
     * 根据文章 id 查询所有的评论列表
     * @param id
     * @return
     */
    Result commentsByArticleId(Long id);


    /**
     * 评论
     * @param commentParam
     * @return
     */
    Result comment(CommentParam commentParam);

    /**
     * 根据评论id删除评论
     * @param id
     * @return
     */
    Result deleteComment(Long id);
}
