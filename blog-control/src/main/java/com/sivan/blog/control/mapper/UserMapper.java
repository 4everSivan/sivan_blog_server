package com.sivan.blog.control.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sivan.blog.control.pojo.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/4/2 15:58
 */

public interface UserMapper extends BaseMapper<SysUser> {

    List<SysUser> searchUserByUsername(String username);

}
