package com.inyaw.admin.sys.service;


import com.inyaw.admin.sys.bean.InyaaSysDept;
import com.inyaw.admin.sys.dao.InyaaSysDeptDao;
import com.inyaw.admin.sys.dto.InyaaSysDeptDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InyaaSysDeptService {

    private final InyaaSysDeptDao inyaaSysDeptDao;

    public void save(InyaaSysDept dept) {
        if (dept.getCreateTime() == null) {
            dept.setCreateTime(LocalDateTime.now());
        }
        if (dept.getParentId() == null) {
            dept.setParentId(0);
        }
        inyaaSysDeptDao.save(dept);
    }

    public void delete(InyaaSysDept dept) {
        inyaaSysDeptDao.delete(dept);
    }

    public List<InyaaSysDeptDto> findDeptTree(InyaaSysDept dept) {
        dept.setParentId(0);
        List<InyaaSysDept> deptList = findDeptList(dept);
        return findDeptList(dept, deptList);
    }

    public List<InyaaSysDept> findDeptList(InyaaSysDept dept) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        Example<InyaaSysDept> ex = Example.of(dept, matcher);
        Sort sort = Sort.by("sort").ascending();
        return inyaaSysDeptDao.findAll(ex, sort);
    }

    private List<InyaaSysDeptDto> findDeptList(InyaaSysDept params, List<InyaaSysDept> initDataList) {
        List<InyaaSysDeptDto> deptResp = new ArrayList<>();
        initDataList.forEach(dept -> {
            InyaaSysDeptDto dto = new InyaaSysDeptDto();
            BeanUtils.copyProperties(dept, dto);

            params.setParentId(dept.getId());
            List<InyaaSysDept> chindres = findDeptList(params);
            if (chindres.size() > 0) {
                dto.setChildren(findDeptList(params, chindres));
            }
            deptResp.add(dto);
        });
        return deptResp;
    }
}
