package com.heartcloud.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zhuZhaoYang
 * @date 2022/5/16 22:34
 */
@Data
public class DirDTO {
    @NotBlank(message = "目录名不能为空")
    private String name;
    private Long parentId;
}
