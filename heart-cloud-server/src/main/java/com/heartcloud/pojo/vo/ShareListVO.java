package com.heartcloud.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author zhuZhaoYang
 * @date 2022/5/23 15:49
 */
@Data
public class ShareListVO {
    private Long shareId;
    private String fileName;
    private Date expireTime;
    private String token;
}
