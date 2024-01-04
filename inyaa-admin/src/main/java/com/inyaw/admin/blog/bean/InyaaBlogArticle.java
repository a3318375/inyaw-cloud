package com.inyaw.admin.blog.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class InyaaBlogArticle {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//主键生成策略
    private Integer id;

    /**
     * 文章内容
     */
    @Lob
    private String context;
}
