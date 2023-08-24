package com.sivan.blog.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.sivan.blog.dao.mapper.IArticleMapper;
import com.sivan.blog.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/3 19:50
 */

@Component
public class ThreadService {


    /**
     * 期望此操作在线程池 执行 不会影响原有的主线程
     * @param articleMapper
     * @param article
     */
    @Async("taskExecutor")
    public void updateArticleViewCount(IArticleMapper articleMapper, Article article) {
        int viewCounts = article.getViewCounts();
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(viewCounts + 1);
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId,article.getId());
        //乐观锁 为在多线程环境下 线程安全
        updateWrapper.eq(Article::getViewCounts,article.getViewCounts());
        //update my_article set view_count = 100 where view_count = 99 and id = 11
        articleMapper.update(articleUpdate,updateWrapper);
        try {
            Thread.sleep(5000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
