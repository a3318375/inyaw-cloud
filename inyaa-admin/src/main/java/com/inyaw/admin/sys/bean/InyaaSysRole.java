package com.inyaw.admin.sys.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name="inyaa_sys_role")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class InyaaSysRole {

    @Id
    @GeneratedValue
    private Integer id;

    private String roleName;

    private String roleKey;

    private String description;

}
