package com.inyaw.admin.config;

import com.inyaw.admin.sys.bean.InyaaSysApi;
import com.inyaw.admin.sys.bean.InyaaSysConfig;
import com.inyaw.admin.sys.bean.InyaaSysRole;
import com.inyaw.admin.sys.dao.InyaaSysApiDao;
import com.inyaw.admin.sys.service.InyaaSysConfigService;
import com.inyaw.admin.sys.service.InyaaSysPermissionService;
import com.inyaw.admin.sys.service.InyaaSysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 缓存部分，配置文件缓存
 * @author: yuxh
 * @date: 2021/4/5 23:44
 */
@Service
@RequiredArgsConstructor
public class CacheService {

    private final RedisService redisService;
    private final InyaaSysPermissionService inyaaSysPermissionService;
    private final InyaaSysApiDao inyaaSysApiDao;
    private final InyaaSysRoleService inyaaSysRoleService;
    private final InyaaSysConfigService inyaaSysConfigService;

    public Map<String, Collection<ConfigAttribute>> getConfigAttributeMap() {
        String key = "CONFIG_ATTRIBUTE";
        Map<String, Collection<ConfigAttribute>> map = redisService.authMapGet(key);
        if (map == null || map.size() < 1) {
            map = new HashMap<>();
            List<InyaaSysApi> list = inyaaSysApiDao.findAll();
            for (InyaaSysApi api : list) {
                List<ConfigAttribute> configAttributeList = new ArrayList<>();
                ConfigAttribute configAttribute;
                switch (api.getType()) {
                    case 0 -> configAttribute = new SecurityConfig("ROLE_ANY");
                    case 1 -> configAttribute = new SecurityConfig("ROLE_LOGIN");
                    case 2 -> {
                        InyaaSysRole role = inyaaSysRoleService.getById(api.getId());
                        configAttribute = new SecurityConfig(role.getRoleKey());
                    }
                    default -> throw new IllegalStateException("错误的类型: " + api.getType());
                }
                configAttributeList.add(configAttribute);
                map.put(api.getUrl(), configAttributeList);
                redisService.authMapSet(key, map);
            }
        }
        return map;
    }

    public Map<String, Object> getConfig(Integer type) {
        Map<String, Object> configMap = redisService.hmget("INYAA_CONFIG_" + type);
        if (configMap == null || configMap.size() < 1) {
            configMap = new HashMap<>();
            List<InyaaSysConfig> configList = inyaaSysConfigService.findAll(type);
            for (InyaaSysConfig bean : configList) {
                configMap.put(bean.getConfigKey(), bean.getConfigValue());
            }
            redisService.hmset("INYAA_CONFIG_" + type, configMap);
        }
        return configMap;
    }
}
