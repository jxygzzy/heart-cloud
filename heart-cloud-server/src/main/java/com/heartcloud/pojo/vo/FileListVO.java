package com.heartcloud.pojo.vo;


import lombok.Data;

import java.util.Date;

/**
 * @author zhuZhaoYang
 * @date 2022/5/15 20:53
 */
@Data
public class FileListVO {
    private Long id;
    private String name;
    private Long parentId;
    private String key;
    private Long userId;
    private Date createTime;
    private Integer type;
}
