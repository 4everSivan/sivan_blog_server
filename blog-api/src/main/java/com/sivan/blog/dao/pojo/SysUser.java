package com.sivan.blog.dao.pojo;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/1/16 20:09
 */
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class SysUser {

    // 用户多了就需要进行分表操作，id就需要分布式id
    //@TableId(type = IdType.ASSIGN_ID) 默认id类型
    //@TableId(type = IdType.AUTO) 数据库自增

    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String account;

    private Integer admin;

    private String avatar;

    private Long createDate;

    private Integer deleted;

    private String email;

    private Long lastLogin;

    private String mobilePhoneNumber;

    private String nickname;

    private String password;

    private String salt;

    private String status;
}
