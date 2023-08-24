package com.sivan.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sivan.blog.dao.mapper.ISysUserMapper;
import com.sivan.blog.dao.pojo.SysUser;
import com.sivan.blog.service.ILoginService;
import com.sivan.blog.service.ISysUserService;
import com.sivan.blog.vo.ErrorCode;
import com.sivan.blog.vo.LoginUserVo;
import com.sivan.blog.vo.Result;
import com.sivan.blog.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/1/16 21:39
 */

@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private ISysUserMapper sysUserMapper;

    @Autowired
    private ILoginService loginService;

    @Override
    public UserVo findUserVoById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null){
            sysUser = new SysUser();
            sysUser.setNickname("sivan");
            sysUser.setId(1L);
            sysUser.setAvatar("/static/img/logo.png");
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(sysUser,userVo);
        return userVo;
    }

    @Override
    public Result selectOne(Long id) {
        if (id == null){
            return Result.fail(400,"无效用户");
        }
        return Result.success(this.findUserVoById(id));
    }

    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null){
            sysUser = new SysUser();
            sysUser.setNickname("sivan");
        }
        return sysUser;
    }

    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getPassword,password);
        queryWrapper.select(SysUser::getAccount,SysUser::getId,SysUser::getAvatar,SysUser::getNickname);
        queryWrapper.last("limit 1");

        return sysUserMapper.selectOne(queryWrapper);
    }


    /**
     * 1. token合法性校验
     *      1. 是否为空
     *      2. 解析是否成功
     *      3. redis是否存在
     *      4. 如果校验失败： 返回错误
     *      5. 如果成功： 返回对应的结果 LoginUserVo
     * @param token
     * @return
     */
    @Override
    public Result findUserByToken(String token) {
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null){
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(sysUser.getId());
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setAvatar(sysUser.getAvatar());
        loginUserVo.setNickname(sysUser.getNickname());
        return Result.success(loginUserVo) ;
    }

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.last("limit 1");
        return this.sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public void save(SysUser sysUser) {
        /**
         * mybatis
         * id 会自动生成，默认生成的id是分布式id 雪花算法
         */
        this.sysUserMapper.insert(sysUser);
    }
}
