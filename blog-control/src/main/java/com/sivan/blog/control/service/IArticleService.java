package com.sivan.blog.control.service;
import com.sivan.blog.control.model.params.PageParam;
import com.sivan.blog.control.vo.ArticleVo;
import com.sivan.blog.control.vo.Result;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/4/10 19:59
 */

public interface IArticleService {

    Result findAll(PageParam pageParam);

    Result deleteArticle(Long articleId);

    Result updateArticle(ArticleVo articleVo);

    Result searchArticle(String title);
}
