package com.sivan.blog.controller;

import com.sivan.blog.service.ICommentsService;
import com.sivan.blog.vo.Params.CommentParam;
import com.sivan.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/4 14:49
 */

@RestController
@RequestMapping("comments")
public class CommentsController {

    @Autowired
    private ICommentsService commentsService;

    /**
     * 文章评论列表
     * @param id
     * @return
     */
    @GetMapping("article/{id}")
    public Result comments(@PathVariable("id") Long id){
        return commentsService.commentsByArticleId(id);
    }

    /**
     * 发表评论
     * @param commentParam
     * @return
     */
    @PostMapping("create/change")
    public Result comment(@RequestBody CommentParam commentParam){
        return commentsService.comment(commentParam);
    }

    /**
     * 删除评论
     * @return
     */
    @PostMapping("delete/{id}")
    public Result deleteComment(@PathVariable("id") Long id){
        return commentsService.deleteComment(id);
    }
}
