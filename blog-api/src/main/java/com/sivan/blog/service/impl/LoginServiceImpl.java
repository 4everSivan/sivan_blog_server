package com.sivan.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.sivan.blog.dao.mapper.ISysUserMapper;
import com.sivan.blog.dao.pojo.SysUser;
import com.sivan.blog.service.ILoginService;
import com.sivan.blog.service.ISysUserService;
import com.sivan.blog.utils.JWTUtils;
import com.sivan.blog.vo.ErrorCode;
import com.sivan.blog.vo.Params.LoginParam;
import com.sivan.blog.vo.Result;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/1 14:41
 */

@Service
@Transactional
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysUserMapper sysUserMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //加密盐
    private static final  String salt = "Sivan819!@#";

    /**
     * 1. 检查参数是否合法
     * 2. 根据用户名和密码去user表中查询是否存在
     * 3. 如不存在 登录失败
     * 4. 如存在 使用jwt生成token返回前端
     * 5. token放入Redis当中 Redis中存储 token:user信息 设置过期时间（登录认证的时候先认证token字符串是否合法,去Redis认证是否存在）
     * @param loginParam
     * @return
     */
    @Override
    public Result login(LoginParam loginParam) {

        String account = loginParam.getAccount();
        String password = loginParam.getPassword();

        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        String pwd = DigestUtils.md5Hex(password + salt);
        SysUser sysUser = sysUserService.findUser(account,pwd);
        if (sysUser == null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        sysUser.setLastLogin(System.currentTimeMillis());
        this.sysUserMapper.updateById(sysUser);

        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        if (StringUtils.isBlank(token)){
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null){
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN" + token);
        if (StringUtils.isBlank(userJson)){
            return null;
        }
        return JSON.parseObject(userJson, SysUser.class);
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN" + token);
        return Result.success(null);
    }
}
