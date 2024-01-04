package com.inyaa.oauth.sys.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class InyaaSysUserDetail {

    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 最后登录IP
     */
    private String loginIp;
    /**
     * 最后登录日期
     */
    private LocalDateTime loginDate;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 账号的创建日期
     */
    private LocalDateTime createTime;
}
