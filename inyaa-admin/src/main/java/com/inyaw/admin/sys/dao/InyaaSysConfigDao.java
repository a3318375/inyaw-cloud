package com.inyaw.admin.sys.dao;


import com.inyaw.admin.sys.bean.InyaaSysConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InyaaSysConfigDao extends JpaRepository<InyaaSysConfig, Integer> {
    InyaaSysConfig getByConfigKey(String key);
}
