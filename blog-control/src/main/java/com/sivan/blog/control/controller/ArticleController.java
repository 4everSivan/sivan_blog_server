package com.sivan.blog.control.controller;

import com.sivan.blog.control.model.params.PageParam;
import com.sivan.blog.control.pojo.Article;
import com.sivan.blog.control.service.IArticleService;
import com.sivan.blog.control.vo.ArticleVo;
import com.sivan.blog.control.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/4/10 19:58
 */

@RestController
@RequestMapping("article")
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @PostMapping("listArticle")
    public Result listArticle(@RequestBody PageParam pageParam){
        return articleService.findAll(pageParam);
    }

    @PostMapping("delete")
    public Result deleteArticle(@RequestParam Long articleId) {
        return articleService.deleteArticle(articleId);
    }

    @PostMapping("update")
    public Result updateArticle(@RequestBody ArticleVo articleVo){
        return articleService.updateArticle(articleVo);
    }

    @PostMapping("search")
    public Result searchArticle(@RequestParam String title){
        return articleService.searchArticle(title);
    }

}
