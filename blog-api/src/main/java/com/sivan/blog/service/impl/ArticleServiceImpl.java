package com.sivan.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sivan.blog.dao.doc.Archives;
import com.sivan.blog.dao.mapper.IArticleMapper;
import com.sivan.blog.dao.mapper.IArticleTagMapper;
import com.sivan.blog.dao.mapper.ICommentsMapper;
import com.sivan.blog.dao.pojo.*;
import com.sivan.blog.dao.mapper.IArticleBodyMapper;
import com.sivan.blog.service.*;
import com.sivan.blog.utils.UserThreadLocal;
import com.sivan.blog.vo.ArticleBodyVo;
import com.sivan.blog.vo.ArticleVo;
import com.sivan.blog.vo.Params.ArticleParam;
import com.sivan.blog.vo.Params.PageParams;
import com.sivan.blog.vo.Result;
import com.sivan.blog.vo.TagVo;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/1/16 20:29
 */

@Service
public class ArticleServiceImpl implements IArticleService {

    @Autowired
    private IArticleMapper articleMapper;

    @Autowired
    private ITagService tagService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private IArticleTagMapper articleTagMapper;

    @Autowired
    private ICommentsMapper commentsMapper;

    /**
     * 分页查询article数据库表
     * @param pageParams
     * @return
     */

    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());

        IPage<Article> articleIPage = articleMapper.listArticle(page,
                pageParams.getCategoryId(),
                pageParams.getTagId(),
                pageParams.getYear(),
                pageParams.getMonth());
        List<Article> records = articleIPage.getRecords();
        return Result.success(copyList(records,true,true));

    }

    /**
     * 查询 最热文章
     * @param limit
     * @return
     */
    @Override
    public Result hotArticle(int limit) {
        //查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //select id,title from article order by view_counts desc limit 5
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.eq(Article::getPrimaryId,0);
        queryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);

        return Result.success(copyList(articles,false,false));
    }

    /**
     * 查询 最新文章
     * @param limit
     * @return
     */
    @Override
    public Result newArticle(int limit) {
        //查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //select id,title from article order by create_date desc limit 5
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.eq(Article::getPrimaryId,0);
        queryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);

        return Result.success(copyList(articles,false,false));
    }

    /**
     * 文章归档
     * @return
     */
    @Override
    public Result listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }


    @Autowired
    private ThreadService threadService;

    /**
     * 1. 根据 id 查询文章信息
     * 2. 根据bodyId 和 categoryId 去做关联查询
     * @return
     */
    @Override
    public Result findArticleById(Long articleId) {

        Article article = this.articleMapper.selectById(articleId);
        ArticleVo articleVo = copy(article, true, true,true,true);
        articleVo.setAvatar(sysUserService.findUserById(article.getAuthorId()).getAvatar());
        threadService.updateArticleViewCount(articleMapper,article);

        return Result.success(articleVo);
    }


    /**
     * 发布文章
     * 1. 构建article对象
     * 2. 获取作者id 当前的登录用户
     * 3. 获取标签 将标签加入到关联列表中
     * 4. body 内容存储 article bodyId
     * 5.
     * 注意：该接口需加入拦截器中拦截
     * @param articleParam
     * @return
     */
    @Override
    public Result publish(ArticleParam articleParam) {
        SysUser sysUser = UserThreadLocal.get();
        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        //设置权重 默认不置顶
        article.setWeight(Article.ARTICLE_COMMON);
        article.setViewCounts(0);
        article.setTitle(articleParam.getTitle());
        article.setSummary(articleParam.getSummary());
        article.setCommentCounts(0);
        article.setCreateDate(System.currentTimeMillis());
        article.setCategoryId(articleParam.getCategory().getId());

        //插入后会生成一个文章id
        this.articleMapper.insert(article);
        List<TagVo> tags = articleParam.getTags();
        if (tags != null){
            for (TagVo tag : tags) {
                Long articleId = article.getId();
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(tag.getId());
                articleTag.setArticleId(articleId);
                articleTagMapper.insert(articleTag);
            }
        }
        //body
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);

        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);

//        Map<String,String> map = new HashMap<>();
//        map.put("id",article.getId().toString());
//        return Result.success(map);

        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(article.getId());
        return Result.success(articleVo);
    }

    /**
     * 更新文章
     * @param articleParam
     * @return
     */
    @Override
    public Result updateArticle(ArticleParam articleParam) {

        ArticleVo oldArticle = (ArticleVo) this.findArticleById(articleParam.getId()).getData();
        Article article = new Article();
        article.setAuthorId(oldArticle.getAuthorId());
        article.setId(oldArticle.getId());
        article.setPrimaryId(articleParam.getPrimaryId());

        //设置权重 默认不置顶
//        article.setWeight(Article.ARTICLE_COMMON);
        article.setViewCounts(oldArticle.getViewCounts());
        article.setTitle(articleParam.getTitle());
        article.setSummary(articleParam.getSummary());
        article.setCommentCounts(oldArticle.getCommentCounts());
        article.setWeight(articleParam.getWeight());

        //转为毫秒值
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date parse = sf.parse(oldArticle.getCreateDate());
            article.setCreateDate(parse.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        article.setCategoryId(articleParam.getCategory().getId());

        List<TagVo> tags = articleParam.getTags();

        // 删除原有的tag
        LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleTagLambdaQueryWrapper.eq(ArticleTag::getArticleId,articleParam.getId());
        List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);

        if (articleTags.size() != 0){
            for (ArticleTag articleTag : articleTags){
                articleTagMapper.deleteById(articleTag.getId());
            }
        }

        // 添加新的tag
        if (tags != null){
            for (TagVo tag : tags) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(tag.getId());
                articleTag.setArticleId(oldArticle.getId());
                articleTagMapper.insert(articleTag);
            }
        }

        LambdaQueryWrapper<ArticleBody> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleBody::getId,articleParam.getBodyId());
        ArticleBody articleBody = this.articleBodyMapper.selectOne(queryWrapper);

        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());

        article.setBodyId(articleBody.getId());

        UpdateWrapper<Article> updateArticleWrapper = new UpdateWrapper<>();
        updateArticleWrapper.eq("id",oldArticle.getId());
        this.articleMapper.update(article,updateArticleWrapper);

        UpdateWrapper<ArticleBody> updateBodyWrapper = new UpdateWrapper<>();
        updateBodyWrapper.eq("id",articleBody.getId());
        this.articleBodyMapper.update(articleBody,updateBodyWrapper);

        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(article.getId());
        return Result.success(articleVo);

    }

    /**
     * 删除文章 (包括文章的内容 评论 子评论 标签)
     * @param articleId
     * @return
     */
    @Override
    public Result deleteArticle(Long articleId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,articleId);
        List<Comment> comments = commentsMapper.selectList(queryWrapper);
        if (comments.size() != 0){
            for (Comment comment : comments){
                commentsMapper.deleteById(comment.getId());
            }
        }
        articleMapper.deleteArticle(articleId);
        return Result.success(null);
    }

    @Override
    public Result searchArticle(String title) {
        List<Article> articleList = articleMapper.searchByTitle(title);
        return Result.success(copyList(articleList,true,true));
    }


    private List<ArticleVo>  copyList(List<Article> records,boolean isTag,boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,false,false));
        }
        return articleVoList;
    }

    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,isBody,isCategory));
        }
        return articleVoList;
    }

    @Autowired
    private ICategoryService categoryService;

    private ArticleVo copy(Article article,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //控制是否显示标签或者作者
        if (isTag){
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if (isAuthor){
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }
        if (isBody){
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }
        if (isCategory){
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }
        return articleVo;
    }

    @Autowired
    private IArticleBodyMapper articleBodyMapper;

    private ArticleBodyVo  findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());

        return articleBodyVo;
    }

}
