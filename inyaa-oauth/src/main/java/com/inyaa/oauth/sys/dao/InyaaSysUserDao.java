package com.inyaa.oauth.sys.dao;

import com.inyaa.oauth.sys.bean.InyaaSysUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InyaaSysUserDao extends JpaRepository<InyaaSysUser, Integer> {

    InyaaSysUser getByUsername(String username);

}
