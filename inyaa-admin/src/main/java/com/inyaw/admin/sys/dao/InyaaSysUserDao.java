package com.inyaw.admin.sys.dao;


import com.inyaw.admin.sys.bean.InyaaSysUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InyaaSysUserDao extends JpaRepository<InyaaSysUser, Integer> {

    InyaaSysUser getByUsername(String username);

}
