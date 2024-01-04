package cn.inyaa.picture.file.service;

import cn.inyaa.picture.file.bean.InyaaSysFile;
import cn.inyaa.picture.file.dao.InyaaSysFileDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/3/29 23:20
 */
@Service
@RequiredArgsConstructor
public class InyaaSysFileService {

    private final InyaaSysFileDao inyaaSysFileDao;

    public void save(String url, int uploadType, Integer type) {
        InyaaSysFile sysFile = new InyaaSysFile();
        sysFile.setUrl(url);
        sysFile.setType(type == null ? 0 : type);
        sysFile.setUploadType(uploadType);
        sysFile.setCreateTime(LocalDateTime.now());
        inyaaSysFileDao.save(sysFile);
    }

    public List<InyaaSysFile> findAll() {
        return inyaaSysFileDao.findAll();
    }

    public InyaaSysFile getRandImg(int type) {
        return inyaaSysFileDao.getRandImg(type);
    }

    public InyaaSysFile getOne(Integer id) {
        return inyaaSysFileDao.getOne(id);
    }
}
