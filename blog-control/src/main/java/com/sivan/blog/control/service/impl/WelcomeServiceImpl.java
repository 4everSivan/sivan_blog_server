package com.sivan.blog.control.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sivan.blog.control.mapper.ArticleMapper;
import com.sivan.blog.control.mapper.MessageMapper;
import com.sivan.blog.control.mapper.WelcomeMapper;
import com.sivan.blog.control.pojo.Article;
import com.sivan.blog.control.pojo.Message;
import com.sivan.blog.control.service.IArticleService;
import com.sivan.blog.control.service.IWelcomeService;
import com.sivan.blog.control.vo.Result;
import com.sivan.blog.control.vo.StatisticalVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/4/10 19:20
 */

@Service
public class WelcomeServiceImpl implements IWelcomeService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public Result getStatistical() {
        StatisticalVo statisticalVo = new StatisticalVo();
        Integer countView = 0;
        LambdaQueryWrapper<Article> articleQueryWrapper = new LambdaQueryWrapper<>();
        List<Article> articles = articleMapper.selectList(articleQueryWrapper);
        statisticalVo.setCountArticle(articles.size());
        for (Article article: articles) {
            countView += article.getViewCounts();
        }
        statisticalVo.setCountView(countView);
        LambdaQueryWrapper<Message> messageQueryWrapper = new LambdaQueryWrapper<>();
        messageQueryWrapper.select();
        List<Message> messages = messageMapper.selectList(messageQueryWrapper);
        statisticalVo.setCountMessage(messages.size());
        return Result.success(statisticalVo);

    }
}
