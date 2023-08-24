package com.sivan.blog.vo.Params;

import com.sivan.blog.vo.CategoryVo;
import com.sivan.blog.vo.TagVo;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/5 14:53
 */

@Data
@ToString
public class ArticleParam {

    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private Integer weight;

    private Integer primaryId;

    private List<TagVo> tags;

    private String title;

    private Long bodyId;
}
