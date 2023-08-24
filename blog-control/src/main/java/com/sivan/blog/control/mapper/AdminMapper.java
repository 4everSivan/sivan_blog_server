package com.sivan.blog.control.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sivan.blog.control.pojo.Admin;
import com.sivan.blog.control.pojo.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/11 15:55
 */
public interface AdminMapper extends BaseMapper<Admin> {

    @Select("SELECT * FROM my_permission where id in (select permission_id FROM my_admin_permission where admin_id = #{adminId})")
    List<Permission> findPermissionByAdminId(Long adminId);

}
