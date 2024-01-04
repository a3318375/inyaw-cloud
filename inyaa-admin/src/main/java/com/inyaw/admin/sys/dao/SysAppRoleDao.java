package com.inyaw.admin.sys.dao;

import com.inyaw.admin.sys.bean.InyaaSysUser;
import com.inyaw.admin.sys.bean.SysAppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysAppRoleDao extends JpaRepository<SysAppRole, Integer> {

}
