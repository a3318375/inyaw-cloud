package com.inyaw.admin.blog.vo;

import com.inyaw.admin.blog.bean.InyaaBlogTag;
import com.inyaw.admin.blog.bean.InyaaBlogType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class InyaaBlogWebVo extends InyaaBlogVo {

    /**
     * 博客分类
     */
    private InyaaBlogType type;

    /**
     * 博客标签
     */
    private List<InyaaBlogTag> tagList;
}
