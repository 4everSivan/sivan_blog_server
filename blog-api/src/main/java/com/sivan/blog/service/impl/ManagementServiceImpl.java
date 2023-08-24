package com.sivan.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sivan.blog.dao.mapper.IArticleMapper;
import com.sivan.blog.dao.mapper.ICommentsMapper;
import com.sivan.blog.dao.mapper.IMessageMapper;
import com.sivan.blog.dao.mapper.ISysUserMapper;
import com.sivan.blog.dao.pojo.Article;
import com.sivan.blog.dao.pojo.Comment;
import com.sivan.blog.dao.pojo.SysUser;
import com.sivan.blog.service.IManagementService;
import com.sivan.blog.utils.QiniuUtils;
import com.sivan.blog.vo.Params.PageParam;
import com.sivan.blog.vo.Params.PageResult;
import com.sivan.blog.vo.Params.UserStatisticalVo;
import com.sivan.blog.vo.Result;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Sivan
 * @version 1.0
 * @Date 2022/5/21 21:38
 */

@Service
public class ManagementServiceImpl implements IManagementService {

    @Autowired
    private IArticleMapper articleMapper;

    @Autowired
    private ISysUserMapper userMapper;

    @Autowired
    private IMessageMapper messageMapper;

    @Autowired
    private ICommentsMapper commentsMapper;

    @Autowired
    private QiniuUtils qiniuUtils;

    private static final String salt = "Sivan819!@#";

    /**
     * 用户中心 分页查询
     *
     * @param pageParam
     * @return
     */
    @Override
    public Result listMyArticle(PageParam pageParam) {
        Page<Article> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getAuthorId, pageParam.getId());
        if (StringUtils.isNotBlank(pageParam.getQueryString())) {
            queryWrapper.eq(Article::getTitle, pageParam.getQueryString());
        }
        Page<Article> ArticlePage = articleMapper.selectPage(page, queryWrapper);
        PageResult<Article> pageResult = new PageResult<>();
        pageResult.setList(ArticlePage.getRecords());
        pageResult.setTotal(ArticlePage.getTotal());
        return Result.success(pageResult);
    }

    /**
     * 用户中心 更新用户信息
     *
     * @param user
     * @return
     */
    @Override
    public Result updateUserInfo(SysUser user) {
        SysUser OldUser = userMapper.selectById(user.getId());
        OldUser.setNickname(user.getNickname());
        // 与原密码不同则加密后修改 否则不修改密码
        if (!OldUser.getPassword().equals(user.getPassword())) {
            OldUser.setPassword(DigestUtils.md5Hex(user.getPassword() + salt));
        }
        if (!OldUser.getAvatar().equals(user.getAvatar())) {
            String oldAvatar = OldUser.getAvatar();
            int index = oldAvatar.indexOf("/");
            index = oldAvatar.indexOf("/", index + 1);
            index = oldAvatar.indexOf("/", index + 1);
            oldAvatar = oldAvatar.substring(index + 2);
            if (!oldAvatar.equals("userAvatar.png")){
                qiniuUtils.delete(oldAvatar);
            }
            OldUser.setAvatar(user.getAvatar());
        }
        OldUser.setEmail(user.getEmail());
        OldUser.setMobilePhoneNumber(user.getMobilePhoneNumber());
        userMapper.updateById(OldUser);
        return Result.success(null);
    }

    /**
     * 用户中心 数据统计
     * @param id
     * @return
     */
    @Override
    public Result listStatistical(Long id) {
        UserStatisticalVo userVo = new UserStatisticalVo();
        SysUser sysUser = userMapper.selectById(id);

        //文章数
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getAuthorId, id);
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        userVo.setArticleCount(articleList.size());

        // 浏览数
        // 评论数
        int viewCount = 0;
        int commentCount = 0;
        LambdaQueryWrapper<Comment> commentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        for (Article article : articleList) {
            viewCount += article.getViewCounts();
            commentLambdaQueryWrapper.eq(Comment::getArticleId, article.getId());
            List<Comment> comments = this.commentsMapper.selectList(commentLambdaQueryWrapper);
            commentCount += comments.size();
        }

        userVo.setViewCount(viewCount);
        userVo.setMessageCount(commentCount);

        // 注册天数
        userVo.setRegisterDays(((sysUser.getLastLogin() - sysUser.getCreateDate()) / 86400000) );

        return Result.success(userVo);
    }

    /**
     * 用户中心 模糊查询
     * @param title
     * @return
     */
    @Override
    public Result searchUserArticle(String title) {
        Page<Article> page = new Page<>(1, 5);
        List<Article> articleList = this.articleMapper.searchByTitle(title);
        PageResult<Article> pageResult = new PageResult<>();
        pageResult.setList(articleList);
        Long total = (long) articleList.size();
        pageResult.setTotal(total);
        return Result.success(pageResult);

    }
}
