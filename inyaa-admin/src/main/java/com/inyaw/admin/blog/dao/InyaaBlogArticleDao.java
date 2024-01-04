package com.inyaw.admin.blog.dao;

import com.inyaw.admin.blog.bean.InyaaBlogArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InyaaBlogArticleDao extends JpaRepository<InyaaBlogArticle, Integer> {

}
