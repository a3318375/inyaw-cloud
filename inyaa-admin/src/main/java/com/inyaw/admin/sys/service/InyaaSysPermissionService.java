package com.inyaw.admin.sys.service;

import com.inyaw.admin.config.RedisService;
import com.inyaw.admin.sys.bean.InyaaSysPermission;
import com.inyaw.admin.sys.dao.InyaaSysPermissionDao;
import com.inyaw.admin.sys.dto.InyaaSysPermissionDto;
import com.inyaw.admin.sys.vo.InyaaSysPermissionVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InyaaSysPermissionService {

    private final InyaaSysPermissionDao inyaaSysPermissionDao;
    private final RedisService redisService;

    public List<InyaaSysPermission> findAll() {
        return inyaaSysPermissionDao.findAll();
    }

    public List<InyaaSysPermission> findMenuList(InyaaSysPermission permission) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        Example<InyaaSysPermission> ex = Example.of(permission, matcher);
        Sort sort = Sort.by("sort").ascending();
        return inyaaSysPermissionDao.findAll(ex, sort);
    }

    public void delete(InyaaSysPermission permission) {
        inyaaSysPermissionDao.deleteById(permission.getId());
        String key = "CONFIG_ATTRIBUTE";
        redisService.del(key);
    }

    public void save(InyaaSysPermission permission) {
        if (permission.getParentId() == null) {
            permission.setParentId(0);
        }
        inyaaSysPermissionDao.save(permission);
        String key = "CONFIG_ATTRIBUTE";
        redisService.del(key);
    }

    private List<InyaaSysPermission> findMenuList(Integer pid, Boolean enable) {
        InyaaSysPermission params = new InyaaSysPermission();
        if (enable != null && enable) {
            params.setIsShow(true);
        }
        params.setParentId(pid);
        return findMenuList(params);
    }

    private List<InyaaSysPermissionDto> findMenuList(int roleId, List<InyaaSysPermission> list, Integer isWx) {
        List<InyaaSysPermissionDto> menuVOS = new ArrayList<>();
        list.forEach(menuInfo -> {
            InyaaSysPermissionDto dto = new InyaaSysPermissionDto();
            BeanUtils.copyProperties(menuInfo, dto);

            List<InyaaSysPermission> chindres = inyaaSysPermissionDao.findMenuList(menuInfo.getId(), roleId);
            if (chindres.size() > 0) {
                dto.setChildren(findMenuList(roleId, chindres, isWx));
            }
            menuVOS.add(dto);
        });
        return menuVOS;
    }

    public List<InyaaSysPermissionDto> findMenuList(int roleId, Integer isWx) {
        List<InyaaSysPermission> list = inyaaSysPermissionDao.findMenuList(0, roleId);
        return findMenuList(roleId, list, isWx);
    }

    public List<String> findMenuPathList(int roleId) {
        return inyaaSysPermissionDao.findMenuPathList(roleId);
    }

    public List<String> findButtonList(Integer roleId) {
        return inyaaSysPermissionDao.findButtonList(true, roleId);
    }

    private List<InyaaSysPermissionVo> findPermissionList(List<InyaaSysPermission> list, Boolean enable) {
        List<InyaaSysPermissionVo> menuVOS = new ArrayList<>();
        list.forEach(menuInfo -> {
            InyaaSysPermissionVo vo = new InyaaSysPermissionVo();
            BeanUtils.copyProperties(menuInfo, vo);

            List<InyaaSysPermission> chindres = findMenuList(menuInfo.getId(), enable);
            if (chindres.size() > 0) {
                vo.setChildren(findPermissionList(chindres, enable));
            }
            menuVOS.add(vo);
        });
        return menuVOS;
    }

    public List<InyaaSysPermissionVo> findPermissionList(Boolean enable) {
        return findPermissionList(findMenuList(0, enable), enable);
    }
}
