package com.sivan.blog.handler;

import com.sivan.blog.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/2/28 15:44
 */

/** @ControllerAdvice
 *  对加了@Controller注解的方法进行拦截处理 （AOP的实现）
 */
@ControllerAdvice
public class AllExceptionHandler {

    //进行异常处理，处理Exception.class的异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result doException(Exception ex){
        ex.printStackTrace();
        return Result.fail(-999,"系统异常");
    }

}
