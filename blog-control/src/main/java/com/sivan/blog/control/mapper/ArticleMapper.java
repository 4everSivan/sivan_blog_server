package com.sivan.blog.control.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sivan.blog.control.pojo.Article;

import java.util.List;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/4/10 20:03
 */
public interface ArticleMapper extends BaseMapper<Article> {

//    List<Article> findStatistical();
    void deleteArticleById(Long articleId);

    List<Article> searchArticleByTitle(String title);
}
