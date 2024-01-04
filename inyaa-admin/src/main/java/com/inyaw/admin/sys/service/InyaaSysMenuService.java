package com.inyaw.admin.sys.service;

import com.inyaw.admin.sys.bean.InyaaSysMenu;
import com.inyaw.admin.sys.dao.InyaaSysMenuDao;
import com.inyaw.admin.sys.vo.InyaaSysMenuVo;
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
public class InyaaSysMenuService {

    private final InyaaSysMenuDao inyaaSysMenuDao;

    public List<InyaaSysMenu> findAll() {
        return inyaaSysMenuDao.findAll();
    }

    public List<InyaaSysMenu> findMenuList(InyaaSysMenu permission) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        Example<InyaaSysMenu> ex = Example.of(permission, matcher);
        Sort sort = Sort.by("sort").ascending();
        return inyaaSysMenuDao.findAll(ex, sort);
    }

    public void delete(InyaaSysMenu permission) {
        inyaaSysMenuDao.delete(permission);
    }

    public void save(InyaaSysMenu permission) {
        if (permission.getParentId() == null) {
            permission.setParentId(0);
        }
        inyaaSysMenuDao.save(permission);
    }

    private List<InyaaSysMenu> findMenuList(Integer pid, Boolean enable) {
        InyaaSysMenu params = new InyaaSysMenu();
        if (enable != null ) {
            params.setIsShow(enable);
        }
        params.setParentId(pid);
        return findMenuList(params);
    }

    private List<InyaaSysMenuVo> findMenuList(List<InyaaSysMenu> list, Boolean enable) {
        List<InyaaSysMenuVo> menuVOS = new ArrayList<>();
        list.forEach(menuInfo -> {
            InyaaSysMenuVo vo = new InyaaSysMenuVo();
            BeanUtils.copyProperties(menuInfo, vo);

            List<InyaaSysMenu> chindres = findMenuList(menuInfo.getId(), enable);
            if (chindres.size() > 0) {
                vo.setChildren(findMenuList(chindres, enable));
            }
            menuVOS.add(vo);
        });
        return menuVOS;
    }

    public List<InyaaSysMenuVo> findMenuList(Boolean enable) {
        return findMenuList(findMenuList(0, enable), enable);
    }

}
