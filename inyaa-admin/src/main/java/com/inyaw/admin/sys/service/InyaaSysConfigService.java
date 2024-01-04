package com.inyaw.admin.sys.service;

import com.inyaw.admin.config.RedisService;
import com.inyaw.admin.sys.bean.InyaaSysConfig;
import com.inyaw.admin.sys.dao.InyaaSysConfigDao;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InyaaSysConfigService {

    private final InyaaSysConfigDao inyaaSysConfigDao;
    private final RedisService redisService;

    public void save(Map<String, String> req) {
        for (String key : req.keySet()) {
            if ("type".equals(key)) {
                continue;
            }
            InyaaSysConfig config = inyaaSysConfigDao.getByConfigKey(key);
            if (config == null) {
                config = new InyaaSysConfig();
            }
            config.setConfigType(Integer.parseInt(req.get("type")));
            config.setConfigKey(key);
            config.setConfigValue(req.get(key));
            inyaaSysConfigDao.save(config);
            redisService.hset("INYAA_CONFIG_" + config.getConfigType(), key, req.get(key));
        }
    }

    public List<InyaaSysConfig> findAll(Integer type) {
        InyaaSysConfig params = new InyaaSysConfig();
        params.setConfigType(type);
        ExampleMatcher matcher = ExampleMatcher.matching();
        Example<InyaaSysConfig> ex = Example.of(params, matcher);
        return inyaaSysConfigDao.findAll(ex);
    }
}
