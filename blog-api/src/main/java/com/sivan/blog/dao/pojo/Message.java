package com.sivan.blog.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/19 15:54
 */

@Data
public class Message {

    //@TableId(type = IdType.AUTO) 数据库自增
    @TableId(type = IdType.AUTO)
    private Long id;

    private String content;

    private Long createDate;

    private Long parentId;

    private Integer foreignId;

    private String nickname;

    private String email;
}
