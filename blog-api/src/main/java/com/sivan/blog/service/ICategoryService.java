package com.sivan.blog.service;

import com.sivan.blog.vo.CategoryVo;
import com.sivan.blog.vo.Result;

import java.util.List;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/3 15:26
 */
public interface ICategoryService {

    /**
     * 查询类别
     * @param categoryId
     * @return
     */
    CategoryVo findCategoryById(Long categoryId);

    /**
     * 查询所有
     * @return
     */
    Result findAll();

    /**
     * 查询分类
     * @return
     */
    Result findAllDetail();

    /**
     * 查询分类 by id
     * @param id
     * @return
     */
    Result categoryDetailById(Long id);
}
