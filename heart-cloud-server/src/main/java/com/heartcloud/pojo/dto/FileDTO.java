package com.heartcloud.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhuZhaoYang
 * @date 2022/5/15 16:58
 */
@Data
public class FileDTO {
    @NotBlank(message = "文件名不能为空")
    private String name;
    @NotBlank(message = "文件key值不能为空")
    private String key;
    private Long dirId;
}
