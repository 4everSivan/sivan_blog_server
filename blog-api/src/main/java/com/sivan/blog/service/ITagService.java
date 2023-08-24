package com.sivan.blog.service;

import com.sivan.blog.vo.Result;
import com.sivan.blog.vo.TagVo;

import java.util.List;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/1/16 20:57
 */
public interface ITagService {

    /**
     * 根据文章id查询标签列表
     * @param articleId
     * @return
     */
    List<TagVo> findTagsByArticleId(Long articleId);

    Result hots(int limit);

    /**
     * 查询所有的文章标签
     * @return
     */
    Result findAll();

    /**
     * 查询所有分类标签
     * @return
     */
    Result findAllDetail();

    /**
     * 查询 分类标签 by id
     * @param id
     * @return
     */
    Result findDetailById(Long id);
}
