package com.inyaw.admin.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录验证成功，认证无效
 */

@Component
@Slf4j
public class InyaaAccessDeniedHandler implements AccessDeniedHandler {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        Map<String, String> rsp = new HashMap<>();
        response.setContentType("application/json;charset=UTF-8");

        response.setStatus(HttpStatus.FORBIDDEN.value());

        rsp.put("code", HttpStatus.FORBIDDEN.value() + "");
        rsp.put("msg", "无权访问");

        log.error("无权访问", accessDeniedException);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(rsp));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
