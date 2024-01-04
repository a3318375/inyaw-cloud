package com.inyaw.admin.blog.dao;

import com.inyaw.admin.blog.bean.InyaaBlog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InyaaBlogDao extends JpaRepository<InyaaBlog, Integer> {

    InyaaBlog findTopByIdLessThanOrderByCreateTimeDesc(int id);

    InyaaBlog findTopByIdGreaterThanOrderByCreateTimeAsc(int id);

}
