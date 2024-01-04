package com.inyaw.admin.sys.dto;

import com.inyaw.admin.sys.bean.InyaaSysUser;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class LoginUser extends InyaaSysUser implements Serializable {

    private static final long serialVersionUID = -1312531418281641819L;
    private String token;

}
