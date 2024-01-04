package cn.inyaa.picture.file.dao;

import cn.inyaa.picture.file.bean.InyaaSysFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author: yuxh
 * @date: 2021/3/12 1:16
 */
public interface InyaaSysFileDao extends JpaRepository<InyaaSysFile, Integer> {

    @Query(value = "select * FROM inyaa_sys_file where type = ?1 ORDER BY RAND() LIMIT 1", nativeQuery = true)
    InyaaSysFile getRandImg(int type);
}
