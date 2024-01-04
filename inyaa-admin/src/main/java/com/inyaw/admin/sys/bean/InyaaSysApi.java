package com.inyaw.admin.sys.bean;

import lombok.Data;
import jakarta.persistence.*;


@Data
@Entity
@Table(name="inyaa_sys_api")
public class InyaaSysApi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String url;

    /**
     * 请求类型，0-无需登陆访问，1-登陆后访问，2-根据特殊角色访问
     */
    private Integer type;

    /**
     * 当requestType为2时，根据此字段访问
     */
    private String roleId;
}
