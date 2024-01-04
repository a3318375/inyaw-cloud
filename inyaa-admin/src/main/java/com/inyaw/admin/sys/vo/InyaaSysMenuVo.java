package com.inyaw.admin.sys.vo;

import com.inyaw.admin.sys.bean.InyaaSysMenu;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class InyaaSysMenuVo extends InyaaSysMenu {

    private List<InyaaSysMenuVo> children;

}
