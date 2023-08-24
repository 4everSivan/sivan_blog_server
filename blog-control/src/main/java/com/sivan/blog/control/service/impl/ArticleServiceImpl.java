package com.sivan.blog.control.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sivan.blog.control.mapper.ArticleMapper;
import com.sivan.blog.control.model.params.PageParam;
import com.sivan.blog.control.pojo.Admin;
import com.sivan.blog.control.pojo.Article;
import com.sivan.blog.control.service.IArticleService;
import com.sivan.blog.control.vo.ArticleVo;
import com.sivan.blog.control.vo.PageResult;
import com.sivan.blog.control.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/4/10 20:02
 */

@Service
public class ArticleServiceImpl implements IArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Result findAll(PageParam pageParam) {
        Page<Article> page = new Page<>(pageParam.getCurrentPage(),pageParam.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(pageParam.getQueryString())){
            queryWrapper.eq(Article::getTitle,pageParam.getQueryString());
        }
        Page<Article> permissionPage = articleMapper.selectPage(page, queryWrapper);
        PageResult<Article> pageResult = new PageResult<>();
        pageResult.setList(permissionPage.getRecords());
        pageResult.setTotal(permissionPage.getTotal());
        return Result.success(pageResult);
    }

    @Override
    public Result deleteArticle(Long articleId) {
        articleMapper.deleteArticleById(articleId);
        return Result.success(null);
    }

    @Override
    public Result updateArticle(ArticleVo articleVo) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId,articleVo.getId());
        Article article = articleMapper.selectOne(queryWrapper);
        article.setTitle(articleVo.getTitle());
        article.setSummary(articleVo.getSummary());
        articleMapper.updateById(article);
        return Result.success(null);
    }

    @Override
    public Result searchArticle(String title) {
        List<Article> articles = articleMapper.searchArticleByTitle(title);
        return Result.success(articles);
    }
}
