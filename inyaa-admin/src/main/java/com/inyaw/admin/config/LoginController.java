package com.inyaw.admin.config;

import com.inyaw.admin.sys.bean.InyaaSysRole;
import com.inyaw.admin.sys.bean.InyaaSysUser;
import com.inyaw.admin.sys.bean.InyaaSysUserDetail;
import com.inyaw.admin.sys.bean.SysAppRole;
import com.inyaw.admin.sys.service.InyaaSysUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final RedisService redisService;
    private final InyaaSysUserService inyaaSysUserService;
    @Value("${messages.login-uri}")
    private String loginUrl;

    @GetMapping("/toLogin")
    public void toLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String referer = request.getHeader("referer");
        if (StringUtils.isBlank(referer)) {
            referer = "https://www.inyaw.com";
        }
        log.info("toLogin获取referer: " + referer);
        redisService.set("oauth-referer", referer);
        response.sendRedirect(loginUrl);
    }

    @GetMapping("/userInit")
    public String userInit() {
        InyaaSysUser inyaaSysUser = new InyaaSysUser();
        inyaaSysUser.setUsername("a3318375");
        inyaaSysUser.setPassword("a3391926");
        InyaaSysUserDetail detail = new InyaaSysUserDetail();
        detail.setName("yoyo");
        inyaaSysUser.setInyaaSysUserDetail(detail);

        InyaaSysRole inyaaSysRole = new InyaaSysRole();
        inyaaSysRole.setId(1);
        inyaaSysRole.setRoleName("超管");
        inyaaSysRole.setRoleKey("SUPER_ADMIN");
        inyaaSysUser.setInyaaSysRole(inyaaSysRole);

        SysAppRole sysAppRole = new SysAppRole();
        sysAppRole.setRoleName("博客");
        sysAppRole.setRoleKey("ROLE_USER");
        List<SysAppRole> roleList = new ArrayList<>();
        roleList.add(sysAppRole);

        inyaaSysUser.setRoleList(roleList);

        inyaaSysUserService.save(inyaaSysUser, true);
        return "success";
    }
}
