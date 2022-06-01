package com.heartcloud.pojo.vo;

import lombok.Data;

/**
 * @author zhuZhaoYang
 * @date 2022/5/15 16:49
 */
@Data
public class QiniuPolicyVO {
    private String token;
    private String url;
    private String dirPrefix;
}
