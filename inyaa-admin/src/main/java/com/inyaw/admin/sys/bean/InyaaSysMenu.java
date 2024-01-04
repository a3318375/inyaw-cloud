package com.inyaw.admin.sys.bean;

import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "inyaa_sys_menu")
public class InyaaSysMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 菜单code
     */
    private String name;
    /**
     * 菜单code
     */
    private String path;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 路由地址
     */
    private String component;
    /**
     * 是否显示
     */
    private Boolean isShow;
    /**
     * 菜单父id
     */
    private Integer parentId;
    /**
     * 是否跳转外部链接
     */
    private Boolean isExt;
    /**
     * 菜单排序
     */
    private Integer sort;
    /**
     * 创建日期
     */
    private LocalDateTime createTime;
    /**
     * 更新日期
     */
    private LocalDateTime updateTime;

}
