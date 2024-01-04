package com.inyaw.admin.sys.bean;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name="inyaa_sys_role_permission")
public class InyaaSysRolePermission {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private Integer roleId;

    private Integer permissionId;
}
