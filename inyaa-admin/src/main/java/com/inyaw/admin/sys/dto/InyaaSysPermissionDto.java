package com.inyaw.admin.sys.dto;

import com.inyaw.admin.sys.bean.InyaaSysPermission;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class InyaaSysPermissionDto extends InyaaSysPermission {

    private List<InyaaSysPermissionDto> children;

}
