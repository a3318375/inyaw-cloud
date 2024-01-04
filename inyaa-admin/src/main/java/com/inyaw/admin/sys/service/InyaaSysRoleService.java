package com.inyaw.admin.sys.service;

import com.inyaw.admin.sys.bean.InyaaSysRole;
import com.inyaw.admin.sys.bean.InyaaSysRolePermission;
import com.inyaw.admin.sys.dao.InyaaSysRoleDao;
import com.inyaw.admin.sys.dao.InyaaSysRolePermissionDao;
import com.inyaw.admin.sys.vo.InyaaSysRoleVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InyaaSysRoleService {

    private final InyaaSysRoleDao inyaaSysRoleDao;
    private final InyaaSysRolePermissionDao inyaaSysRolePermissionDao;

    public void save(InyaaSysRoleVo roleInfo) {
        InyaaSysRole role = new InyaaSysRole();
        BeanUtils.copyProperties(roleInfo, role);
        inyaaSysRoleDao.save(role);
        inyaaSysRolePermissionDao.deleteByRoleId(role.getId());
        if (roleInfo.getMenuList() != null && roleInfo.getMenuList().size() > 0) {
            List<InyaaSysRolePermission> rolePermissionList = new ArrayList<>();
            roleInfo.getMenuList().forEach(menuId -> {
                InyaaSysRolePermission rolePermission = new InyaaSysRolePermission();
                rolePermission.setRoleId(role.getId());
                rolePermission.setPermissionId(menuId);
                rolePermissionList.add(rolePermission);
            });
            inyaaSysRolePermissionDao.saveAll(rolePermissionList);
        }
    }

    public void delete(InyaaSysRole roleInfo) {
        inyaaSysRoleDao.deleteById(roleInfo.getId());
    }

    public List<InyaaSysRole> list() {
        return inyaaSysRoleDao.findAll();
    }

    public InyaaSysRole getById(Integer roleId) {
        return inyaaSysRoleDao.getById(roleId);
    }

    public List<String> findRoleKeyList(Integer id) {
        return inyaaSysRoleDao.findRoleKeyList(id);
    }

    public List<InyaaSysRole> findAll(InyaaSysRole role) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        Example<InyaaSysRole> ex = Example.of(role, matcher);
        return inyaaSysRoleDao.findAll(ex);
    }

    public List<InyaaSysRoleVo> findRoleList(InyaaSysRole role) {
        List<InyaaSysRole> lit = findAll(role);
        List<InyaaSysRoleVo> respList = new ArrayList<>();
        lit.forEach(roleBean -> {
            InyaaSysRoleVo vo = new InyaaSysRoleVo();
            BeanUtils.copyProperties(roleBean, vo);
            List<Integer> permissionList = inyaaSysRolePermissionDao.findPermissionIdByRoleId(vo.getId());
            vo.setMenuList(permissionList);
            respList.add(vo);
        });
        return respList;
    }
}
