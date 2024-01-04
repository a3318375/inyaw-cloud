package com.inyaw.admin.sys.dao;

import com.inyaw.admin.sys.bean.InyaaSysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InyaaSysRoleDao extends JpaRepository<InyaaSysRole, Integer> {

    @Query(value = "select role_key from inyaa_sys_role where id in (select role_id from inyaa_sys_user_role where user_id = ?1)", nativeQuery = true)
    List<String> findRoleKeyByUserId(Integer userId);

    @Query("select roleKey from InyaaSysRole where id in (select roleId from InyaaSysRolePermission where permissionId = ?1)")
    List<String> findRoleKeyList(Integer id);
}
