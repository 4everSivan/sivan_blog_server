package com.sivan.blog.control.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sivan.blog.control.mapper.ArticleMapper;
import com.sivan.blog.control.mapper.CommentMapper;
import com.sivan.blog.control.mapper.UserMapper;
import com.sivan.blog.control.model.params.PageParam;
import com.sivan.blog.control.pojo.Admin;
import com.sivan.blog.control.pojo.Article;
import com.sivan.blog.control.pojo.Comment;
import com.sivan.blog.control.pojo.SysUser;
import com.sivan.blog.control.service.IUserService;
import com.sivan.blog.control.vo.PageResult;
import com.sivan.blog.control.vo.Result;
import com.sivan.blog.control.vo.SysUserVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/4/2 15:57
 */

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CommentMapper commentMapper;

    //加密盐
    private static final  String salt = "Sivan819!@#";

    @Override
    public Result listUser(PageParam pageParam) {
        Page<SysUser> page = new Page<>(pageParam.getCurrentPage(),pageParam.getPageSize());
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();

        Page<SysUser> sysUserPage = userMapper.selectPage(page, queryWrapper);
        PageResult<SysUser> pageResult = new PageResult<>();
        pageResult.setList(sysUserPage.getRecords());
        pageResult.setTotal(sysUserPage.getTotal());
        return Result.success(pageResult);
    }

    @Override
    public Result deleteUser(Long id) {
        if (id == null){
            return Result.fail(400,"无该用户");
        }
        // 删除该用户文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getAuthorId,id);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        if (articles.size() != 0){
            for(Article article : articles){
                articleMapper.deleteById(article.getId());
            }
        }

        // 删除评论
        LambdaQueryWrapper<Comment> commentQueryWrapper = new LambdaQueryWrapper<>();
        commentQueryWrapper.eq(Comment::getAuthorId,id);
        List<Comment> comments = commentMapper.selectList(commentQueryWrapper);
        if (comments.size() != 0){
            for(Comment comment : comments){
                commentMapper.deleteById(comment.getId());
            }
        }
        //删除该用户
        userMapper.deleteById(id);
        return Result.success(null);
    }

    @Override
    public Result updateUser(SysUserVo sysUserVo) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getId,sysUserVo.getId());
        SysUser sysUser = userMapper.selectOne(queryWrapper);
        sysUser.setAvatar(sysUserVo.getAvatar());
        // 与原密码不同则加密后修改 否则不修改密码
        if (!sysUser.getPassword().equals(sysUserVo.getPassword())){
            sysUser.setPassword(DigestUtils.md5Hex(sysUserVo.getPassword() + salt));
        }
        userMapper.updateById(sysUser);
        return Result.success(null);
    }

    @Override
    public Result searchUser(String username) {
        List<SysUser> sysUsers = userMapper.searchUserByUsername(username);
        return Result.success(sysUsers);
    }
}
