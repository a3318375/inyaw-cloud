package com.inyaw.admin.sys.dao;

import com.inyaw.admin.sys.bean.InyaaSysApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InyaaSysApiDao extends JpaRepository<InyaaSysApi, Integer> {

}
