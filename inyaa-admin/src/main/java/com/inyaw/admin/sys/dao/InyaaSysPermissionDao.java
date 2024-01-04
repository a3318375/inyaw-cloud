package com.inyaw.admin.sys.dao;


import com.inyaw.admin.sys.bean.InyaaSysPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InyaaSysPermissionDao extends JpaRepository<InyaaSysPermission, Integer> {

    public List<InyaaSysPermission> findByTypeNotAndIsShowIsAndParentIdIsOrderBySortAsc(int type, boolean isShow, int parentId);

    @Query("select u from InyaaSysPermission u where u.type <> 2 and u.isShow = true and u.parentId = ?1 and u.id in (select permissionId from InyaaSysRolePermission where roleId = ?2) order by u.sort")
    public List<InyaaSysPermission> findMenuList(Integer parentId, Integer roleId);

    @Query("select path from InyaaSysPermission where type = 2 and isShow = ?1 and id in (select permissionId from InyaaSysRolePermission where roleId = ?2) order by sort")
    public List<String> findButtonList(boolean isShow, Integer roleId);

    @Query("select u.path from InyaaSysPermission u where u.type = 1 and u.isShow = true and u.id in (select permissionId from InyaaSysRolePermission where roleId = ?1) order by u.sort")
    public List<String> findMenuPathList(Integer roleId);
}
