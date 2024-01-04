package com.inyaw.admin.config;

import com.inyaw.admin.sys.bean.InyaaSysUser;
import com.inyaw.admin.sys.dao.InyaaSysUserDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class Oauth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final InyaaSysUserDao inyaaSysUserDao;
    private final RedisService redisService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        //账号缓存
        String token = UUID.randomUUID().toString();
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        if (oAuth2User != null) {
            InyaaSysUser user = inyaaSysUserDao.getByUsername(oAuth2User.getName());
            redisService.set(oAuth2User.getName(), user);
            Object tokenObj = redisService.get(oAuth2User.getName() + "_TOKEN_KEY");
            token = (tokenObj == null ? "" : String.valueOf(tokenObj));
            if (StringUtils.isBlank(token)) {
                token = UUID.randomUUID().toString();
                redisService.set(oAuth2User.getName() + "_TOKEN_KEY", token);
            }
            redisService.set(token, authentication, 3600);
        }
        Object refer = redisService.get("oauth-referer");
        if (refer != null && StringUtils.isNotBlank(String.valueOf(refer))) {
            String url = String.valueOf(refer);
            if (url.contains("www.inyaw.com")) {
                response.sendRedirect("https://www.inyaw.com/login/" + token);
            } else if (url.contains("localhost:3000")) {
                response.sendRedirect("http://localhost:3000/login/" + token);
            } else {
                if (url.endsWith("/")) {
                    String jump = url + "login/" + token;
                    response.sendRedirect(jump);
                } else {
                    if (url.contains("localhost")) {
                        String end = "";
                        if (url.contains("3333")) {
                            response.sendRedirect("http://localhost:3333/login?code=" + token + "&redirect=" + end);
                        } else {
                            response.sendRedirect("http://localhost:3000/login/" + token);
                        }
                    } else {
                        if (url.contains("admin.inyaw.com")) {
                            response.sendRedirect("https://admin.inyaw.com/login/" + token);
                        } else {
                            response.sendRedirect("https://www.inyaw.com/login/" + token);
                        }
                    }
                }
            }
        } else {
            response.sendRedirect("/inyaa-admin/authorize?grant_type=client_credentials");
        }
    }

}
