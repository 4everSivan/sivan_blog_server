package com.sivan.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/1/16 20:19
 */

@Data
@AllArgsConstructor
public class Result {

    private Boolean success;

    private int code;

    private String msg;

    private Object data;

    /**
     * 成功时返回的数据
     * @param data
     * @return
     */
    public static Result success(Object data){
        return new Result(true,200,"success",data);
    }

    /**
     * 失败时返回的数据
     * @param code
     * @param msg
     * @return
     */
    public static Result fail(int code,String msg){
        return new Result(false,code,msg,null);
    }
}
