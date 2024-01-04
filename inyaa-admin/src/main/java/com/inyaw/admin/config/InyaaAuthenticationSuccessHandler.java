package com.inyaw.admin.config;

import com.inyaw.admin.sys.bean.InyaaSysUser;
import com.inyaw.admin.sys.dto.LoginUser;
import com.inyaw.admin.sys.service.InyaaSysUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * oauth授权成功部门
 */
@Service
public class InyaaAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Resource
    private ObjectMapper objectMapper; //springmvc启动时自动装配json处理类
    @Resource
    private RedisService redisService;
    @Resource
    private InyaaSysUserService inyaaSysUserService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Map<String, Object> rsp = new HashMap<>();
        rsp.put("code", HttpStatus.OK.value());
        rsp.put("message", "登录成功");
        Map<String, Object> tokenMap = new HashMap<>();
        LoginUser loginUser = (LoginUser) redisService.get(authentication.getName());
        if (loginUser != null) {
            redisService.set(loginUser.getToken(), authentication, 3600);
            tokenMap.put("token", loginUser.getToken());

            WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
            InyaaSysUser newUser = inyaaSysUserService.getByUsername(loginUser.getUsername());
            if (newUser != null) {
                newUser.getInyaaSysUserDetail().setLoginIp(details.getRemoteAddress());
                newUser.getInyaaSysUserDetail().setLoginDate(LocalDateTime.now());
                inyaaSysUserService.save(newUser, false);
            }
        } else {
            throw new InsufficientAuthenticationException("找不到账号");
        }
        rsp.put("data", tokenMap);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(rsp));
        response.getWriter().flush();
        response.getWriter().close();
    }

}
