package com.sivan.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sivan.blog.dao.mapper.ICommentsMapper;
import com.sivan.blog.dao.pojo.Comment;
import com.sivan.blog.dao.pojo.SysUser;
import com.sivan.blog.service.ICommentsService;
import com.sivan.blog.service.ISysUserService;
import com.sivan.blog.utils.UserThreadLocal;
import com.sivan.blog.vo.CommentVo;
import com.sivan.blog.vo.Params.CommentParam;
import com.sivan.blog.vo.Result;
import com.sivan.blog.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/4 14:58
 */

@Service
public class CommentsServiceImpl implements ICommentsService {

    @Autowired
    private ICommentsMapper commentsMapper;

    @Autowired
    private ISysUserService sysUserService;

    /**
     * 1. 根据文章 id 查询 评论列表 从 my_comments 中查询
     * 2. 根据作者 id 查询 作者信息
     * 3. 判断：如果 level = 1
     *      则查询有没有子评论
     *          有：根据评论 id 进行查询（parentId）
     * @param id
     * @return
     */
    @Override
    public Result commentsByArticleId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,id);
        queryWrapper.eq(Comment::getLevel,1);
        List<Comment> comments = commentsMapper.selectList(queryWrapper);
        List<CommentVo> commentVoList = copyList(comments);
        return Result.success(commentVoList);

    }


    @Override
    public Result comment(CommentParam commentParam) {
        SysUser sysUser = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setContent(commentParam.getContent());
        comment.setAuthorId(sysUser.getId());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
        if (parent == null || parent == 0){
            comment.setLevel(1);
        }else {
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        commentsMapper.insert(comment);
        CommentVo commentVo = copy(comment);
        return Result.success(commentVo);
    }

    /**
     * 删除评论(二级评论也会一并删除)
     * @param id
     * @return
     */
    @Override
    public Result deleteComment(Long id) {
        Comment comment = commentsMapper.selectById(id);
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,comment.getArticleId());
        queryWrapper.eq(Comment::getParentId,comment.getId());
        List<Comment> comments = commentsMapper.selectList(queryWrapper);
        if (comments.size() != 0){
            for (Comment comm : comments){
                commentsMapper.deleteById(comm.getId());
            }
        }
        commentsMapper.deleteById(id);
        return Result.success(null);
    }

    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : comments) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
        //作者信息
        Long authorId = comment.getAuthorId();
        UserVo userVo = this.sysUserService.findUserVoById(authorId);
        commentVo.setAuthor(userVo);
        //子评论
        Integer level = comment.getLevel();
        if (level == 1){
            Long id = comment.getId();
            List<CommentVo> commentVoList = findCommentsByParentId(id);
            commentVo.setChildrens(commentVoList);
        }
        //to User 评论目标
        if (level > 1){
            Long toUid = comment.getToUid();
            UserVo toUserVo = this.sysUserService.findUserVoById(toUid);
            commentVo.setToUser(toUserVo);
        }
        return commentVo;
    }

    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId,id);
        queryWrapper.eq(Comment::getLevel,2);
        return copyList(commentsMapper.selectList(queryWrapper));
    }
}
