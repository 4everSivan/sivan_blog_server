package com.sivan.blog.control.service;

import com.sivan.blog.control.vo.Result;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/4/10 19:18
 */

public interface IWelcomeService {

    /**
     * 欢迎页 统计功能的数据
     * @return
     */
    Result getStatistical();
}
