package com.inyaw.admin.sys.bean;

import lombok.Data;

import jakarta.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "inyaa_sys_config")
public class InyaaSysConfig implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String configKey;

    private String configValue;

    private Integer configType;
}
