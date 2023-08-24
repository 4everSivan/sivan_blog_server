package com.sivan.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.sivan.blog.dao.pojo.SysUser;
import com.sivan.blog.service.IRegisterService;
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

import java.util.concurrent.TimeUnit;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/2 15:53
 */

@Service
@Transactional
public class RegisterServiceImpl implements IRegisterService {

    @Autowired
    private ISysUserService sysUserService;

    //加密盐
    private static final  String salt = "Sivan819!@#";

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 1. 判断参数 是否合法
     * 2. 判断账户 是否存在
     *      存在：返回已经被注册
     *      不存在： 注册用户
     * 3. 注册成功 生成token 存入redis 并返回
     * 4. 加上事务 一旦中间任何事务出现问题 注册的用户需要rollback
     * @param loginParam
     * @return
     */
    @Override
    public Result register(LoginParam loginParam) {
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();
        if (StringUtils.isBlank(account)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(nickname)
        ){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        SysUser sysUser = sysUserService.findUserByAccount(account);
        if (sysUser != null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(), ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        sysUser = new SysUser();
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password + salt));
        sysUser.setNickname(nickname);
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("http://img.sweetbabywow.club/userAvatar.png");
        sysUser.setAdmin(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        this.sysUserService.save(sysUser);

        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }
}
