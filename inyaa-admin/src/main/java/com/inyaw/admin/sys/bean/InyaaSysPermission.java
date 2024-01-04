package com.inyaw.admin.sys.bean;

import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class InyaaSysPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单路径
     */
    private String path;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 微信菜单图标
     */
    private String wxIcon;
    /**
     * 是否显示
     */
    private Boolean isShow;
    /**
     * 菜单类型，0-目录，1-菜单，2-按钮
     */
    private Integer type;
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
