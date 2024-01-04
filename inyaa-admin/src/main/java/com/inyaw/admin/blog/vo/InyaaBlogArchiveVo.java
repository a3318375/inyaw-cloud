package com.inyaw.admin.blog.vo;

import com.inyaw.admin.blog.bean.InyaaBlog;
import lombok.Data;

import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/3/15 0:27
 */
@Data
public class InyaaBlogArchiveVo {

    private String archiveDate;

    private Integer articleTotal;

    private List<InyaaBlog> archivePosts;

}
