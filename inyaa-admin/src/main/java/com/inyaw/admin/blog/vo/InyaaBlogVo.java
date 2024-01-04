package com.inyaw.admin.blog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
public class InyaaBlogVo {

    private Integer id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 封面图
     */
    private String cover;

    /**
     * 评论数
     */
    private Integer comments;

    /**
     * 发布状态
     */
    private Boolean status;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 浏览次数
     */
    private Integer views;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 用户id，作者
     */
    private Integer userId;

    /**
     * 是否打开评论
     */
    private Boolean isComment;

    /**
     * 是否热门
     */
    private Boolean isHot;

}
