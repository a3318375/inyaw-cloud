package com.inyaw.admin.sys.vo;

import com.inyaw.admin.sys.bean.InyaaSysRole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class InyaaSysRoleVo extends InyaaSysRole {

    private List<Integer> menuList;

}
