package com.sivan.blog.control.service;

import com.sivan.blog.control.model.params.PageParam;
import com.sivan.blog.control.pojo.Permission;
import com.sivan.blog.control.vo.Result;
import org.springframework.stereotype.Service;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/11 15:07
 */

public interface IPermissionService {

    /**
     * 后台管理 用户表的所有字段 Permission
     * @param pageParam
     * @return
     */
    Result listPermission(PageParam pageParam);

    /**
     * 添加
     * @param permission
     * @return
     */
    Result add(Permission permission);

    /**
     * 修改
     * @param permission
     * @return
     */
    Result update(Permission permission);

    /**
     * 删除
     * @param id
     * @return
     */
    Result delete(Long id);
}
