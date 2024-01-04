package com.inyaa.oauth.sys.bean;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class InyaaSysUser {

    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 账号是否未过期
     */
    private Boolean accountNonExpired;
    /**
     * 账号是否未锁定
     */
    private Boolean accountNonLocked;
    /**
     * 账号是否可用
     */
    private Boolean enabled;
    /**
     * 账号凭证是否未过期
     */
    private Boolean credentialsNonExpired;
    /**
     * 角色
     */
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<SysAppRole> roleList;
    /**
     * 用户详情资料
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private InyaaSysUserDetail inyaaSysUserDetail;

    @OneToOne(fetch = FetchType.LAZY)
    private InyaaSysRole inyaaSysRole;
}
