package com.sivan.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sivan.blog.dao.doc.Archives;
import com.sivan.blog.dao.pojo.Article;

import java.util.List;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/1/16 20:07
 */
public interface IArticleMapper extends BaseMapper<Article> {

    List<Archives> listArchives();

    /**
     * 分页查询
     * @param page
     * @param categoryId
     * @param tagId
     * @param year
     * @param month
     * @return
     */
    IPage<Article> listArticle(Page<Article> page,
                               Long categoryId,
                               Long tagId,
                               String year,
                               String month);

    /**
     * 删除文章
     * @param articleId
     */
    void deleteArticle(Long articleId);

    List<Article> searchByTitle(String title);
}
