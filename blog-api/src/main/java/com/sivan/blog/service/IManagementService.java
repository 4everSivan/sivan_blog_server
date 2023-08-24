package com.sivan.blog.service;

import com.sivan.blog.dao.pojo.SysUser;
import com.sivan.blog.vo.Params.PageParam;
import com.sivan.blog.vo.Result;

/**
 * @author Sivan
 * @version 1.0
 * @Date 2022/5/21 21:37
 */

public interface IManagementService {

    /**
     * 用户中心 分页查询 用户文章
     * @param pageParam
     * @return
     */
    Result listMyArticle(PageParam pageParam);

    /**
     * 用户中心 修改用户信息
     * @param user
     * @return
     */
    Result updateUserInfo(SysUser user);

    /**
     * 用户中心 统计数据
     * @return
     */
    Result listStatistical(Long id);

    /**
     * 用户中心 模糊查询
     * @param title
     * @return
     */
    Result searchUserArticle(String title);
}
