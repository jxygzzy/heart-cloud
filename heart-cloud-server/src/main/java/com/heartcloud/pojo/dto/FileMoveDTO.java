package com.heartcloud.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zhuZhaoYang
 * @date 2022/5/17 22:19
 */
@Data
public class FileMoveDTO {
    @NotNull(message = "文件Id不能为空")
    private Long fileId;
    private Long dirId;
}
