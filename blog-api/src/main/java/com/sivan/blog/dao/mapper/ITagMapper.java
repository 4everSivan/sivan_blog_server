package com.sivan.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sivan.blog.dao.pojo.Tag;

import java.util.List;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/1/16 20:12
 */
public interface ITagMapper extends BaseMapper<Tag> {

    /**
     * 根据文章id查询标签列表
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 查询最热标签前n条
     * @param limit
     * @return
     */
    List<Long> findHotsByTagIds(int limit);

    List<Tag> findTagsByTagIds(List<Long> tagIds);
}
