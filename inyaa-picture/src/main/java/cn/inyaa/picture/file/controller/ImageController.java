package cn.inyaa.picture.file.controller;

import cn.inyaa.picture.base.BaseResult;
import cn.inyaa.picture.config.RedisService;
import cn.inyaa.picture.file.bean.InyaaSysFile;
import cn.inyaa.picture.file.service.InyaaBaseUploadService;
import cn.inyaa.picture.file.service.InyaaSysFileService;
import com.alibaba.fastjson.JSON;
import com.upyun.RestManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@Slf4j
public class ImageController {

    private final InyaaSysFileService inyaaSysFileService;
    private final Map<String, InyaaBaseUploadService> uploadMap;
    private final RedisService redisService;

    @GetMapping("/list")
    public BaseResult<List<InyaaSysFile>> getMobile() {
        List<InyaaSysFile> list = inyaaSysFileService.findAll();
        return BaseResult.success(list);
    }

    @GetMapping("/image")
    public BaseResult<String> getPc(Integer type) {
        InyaaSysFile file = inyaaSysFileService.getRandImg(type);
        return BaseResult.success(file.getUrl());
    }

    @PostMapping("/delete")
    public BaseResult<String> delete(@RequestBody InyaaSysFile inyaaSysFile) {
        InyaaSysFile bean = inyaaSysFileService.getOne(inyaaSysFile.getId());
        if (bean != null) {
            Map<String, Object> configMap = redisService.hmget("INYAA_CONFIG_1");
            int uploadType = Integer.parseInt(String.valueOf(configMap.get("UPLOAD_TYPE")));
            switch (uploadType) {
                case 1:
                    uploadMap.get("qniUploadService").deleteFile(bean.getUrl(), configMap);
                    break;
                case 2:
                    uploadMap.get("upaiUploadService").deleteFile(bean.getUrl(), configMap);
                    break;
            }
        }
        return BaseResult.success();
    }

    @PostMapping("/upload")
    public BaseResult<String> upload(@RequestParam("file") MultipartFile file, @RequestParam("type") Integer type) throws IOException {
        Map<String, Object> configMap = redisService.hmget("INYAA_CONFIG_1");
        int uploadType = Integer.parseInt(String.valueOf(configMap.get("UPLOAD_TYPE")));
        String url = null;
        switch (uploadType) {
            case 0:
                url = uploadMap.get("costUploadService").uploadFile(file, type, configMap);
                break;
            case 1:
                url = uploadMap.get("qniUploadService").uploadFile(file, type, configMap);
                break;
            case 2:
                url = uploadMap.get("upaiUploadService").uploadFile(file, type, configMap);
                break;
        }
        return BaseResult.success(url);
    }

    @GetMapping("/fileList")
    public String upload() {
        Map<String, Object> configMap = redisService.hmget("INYAA_CONFIG_1");
        String username = String.valueOf(configMap.get("UPLOAD_UPAI_USERNAME"));
        String password = String.valueOf(configMap.get("UPLOAD_UPAI_PASSWORD"));
        String bucket = String.valueOf(configMap.get("UPLOAD_UPAI_BUCKET"));
        RestManager manager = new RestManager(bucket, username, password);

        try {
            Map<String, String> params = new HashMap<>();
//            if (StringUtils.isNotBlank(start)) {
//                params.put("x-list-iter", start);
//            }
            Response resp = manager.readDirIter("/cover", params);
            if (resp != null && resp.body() != null && resp.isSuccessful()) {
                return JSON.toJSONString(resp.body());
            }
        } catch (Exception e) {
            log.error("读取图片目录异常", e);
        }
        return null;
    }

}
