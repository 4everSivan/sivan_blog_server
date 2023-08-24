package com.sivan.blog.service;

import com.sivan.blog.vo.Params.ArticleParam;
import com.sivan.blog.vo.Params.PageParams;
import com.sivan.blog.vo.Result;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/1/16 20:27
 */
public interface IArticleService {

    /**
     * 分页查询 文章列表
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);

    /**
     * 最热文章
     * @param limit
     * @return
     */
    Result hotArticle(int limit);

    /**
     * 最新文章
     * @param limit
     * @return
     */
    Result newArticle(int limit);

    /**
     * 文章归档
     * @return
     */
    Result listArchives();

    /**
     * 查看文章详情
     * @return
     */
    Result findArticleById(Long articleId);

    /**
     * 文章发布
     * @param articleParam
     * @return
     */
    Result publish(ArticleParam articleParam);

    /**
     * 修改文章
     * @param articleParam
     * @return
     */
    Result updateArticle(ArticleParam articleParam);

    /**
     * 删除文章
     * @return
     * @param articleId
     */
    Result deleteArticle(Long articleId);

    /**
     * 查询文章（模糊查询）
     * @param title
     * @return
     */
    Result searchArticle(String title);
}
