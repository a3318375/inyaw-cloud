package cn.inyaa.picture.file.bean;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: yuxh
 * @date: 2021/3/29 23:14
 */
@Data
@Entity
@Table(name="inyaa_sys_file")
public class InyaaSysFile {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//主键生成策略
    private Integer id;

    /**
     * 图片链接
     */
    private String url;

    /**
     * 0-封面
     */
    private Integer type;

    /**
     * 上传方式，0-本地，1-七牛，2-又拍
     */
    private Integer uploadType;

    /**
     * 上传时间
     */
    private LocalDateTime createTime;
}
