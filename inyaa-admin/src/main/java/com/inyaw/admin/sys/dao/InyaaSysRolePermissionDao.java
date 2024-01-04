package com.inyaw.admin.sys.dao;

import com.inyaw.admin.sys.bean.InyaaSysRolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InyaaSysRolePermissionDao extends JpaRepository<InyaaSysRolePermission, Integer> {

    @Query("select permissionId from InyaaSysRolePermission where roleId = ?1")
    List<Integer> findPermissionIdByRoleId(Integer roleId);

    void deleteByRoleId(Integer id);
}
