package com.inyaa.oauth.sys.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
public class SysAppRole {

    @Id
    @GeneratedValue
    private Integer id;

    private String roleName;

    private String roleKey;

    private String description;

}
