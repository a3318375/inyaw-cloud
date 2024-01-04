package com.inyaw.admin.sys.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class InyaaAdminMenuVo {

    private String title;
    private Integer value;
    private List<InyaaAdminMenuVo> children;

}
