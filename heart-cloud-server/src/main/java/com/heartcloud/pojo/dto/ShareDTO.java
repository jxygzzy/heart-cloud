package com.heartcloud.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zhuZhaoYang
 * @date 2022/5/16 19:51
 */
@Data
public class ShareDTO {
    @NotNull(message = "文件Id不能为空")
    private Long fileId;
    @NotNull(message = "分享天数不能为空")
    private Integer shareDays;

    private String password;
}
