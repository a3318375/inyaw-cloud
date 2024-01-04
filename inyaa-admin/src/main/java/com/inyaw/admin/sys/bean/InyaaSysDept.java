package com.inyaw.admin.sys.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "inyaa_sys_dept")
@Accessors(chain = true)
public class InyaaSysDept {

    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 上级部门
     */
    private Integer parentId;

    /**
     * 启用/停用
     */
    private Boolean status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 排序
     */
    private Integer sort;
}
