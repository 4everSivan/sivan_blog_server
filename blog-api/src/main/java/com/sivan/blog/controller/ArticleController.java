package com.sivan.blog.controller;

import com.sivan.blog.common.aop.LogAnnotation;
import com.sivan.blog.service.IArticleService;
import com.sivan.blog.vo.Params.ArticleParam;
import com.sivan.blog.vo.Params.PageParams;
import com.sivan.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/1/16 20:14
 * jason数据进行交互
 */

@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    /**
     * 首页 文章列表
     * @param pageParams
     * @return
     */
    @PostMapping
    public Result listArticle(@RequestBody PageParams pageParams){
        return articleService.listArticle(pageParams);
    }


    /**
     * 首页 最热文章
     * @return
     */
    @PostMapping("hot")
    public Result hotArticle(){
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    /**
     * 首页 最新文章
     * @return
     */
    @PostMapping("new")
    public Result newArticle(){
        int limit = 5;
        return articleService.newArticle(limit);
    }

    /**
     * 文章列表
     * @return
     */
    @LogAnnotation(module = "文章",operator = "获取文章列表")
    @PostMapping("listArchives")
    public Result listArchives(){
        return articleService.listArchives();
    }

    /**
     * 文章详情
     * @param articleId
     * @return
     */
    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long articleId){
        return articleService.findArticleById(articleId);
    }

    /**
     * 发布文章
     * @return
     */
    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }

    /**
     * 更新文章
     * @param articleParam
     * @return
     */
    @PostMapping("update")
    public Result updateArticle(@RequestBody ArticleParam articleParam) {
        return articleService.updateArticle(articleParam);
    }

    /**
     * 删除文章
     * @param articleId
     * @return
     */
    @PostMapping("delete/{articleId}")
    public Result deleteArticle(@PathVariable("articleId") Long articleId){
        return articleService.deleteArticle(articleId);
    }

    /**
     * 查询文章（模糊查询）
     * @param title
     * @return
     */
    @PostMapping("search")
    public Result searchArticle(@RequestParam String title){
        System.out.println(title);
        return articleService.searchArticle(title);
    }
}
