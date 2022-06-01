package com.heartcloud.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhuZhaoYang
 * @date 2022/5/20 14:05
 */
@Data
public class SaveShareDTO {
    @NotBlank(message = "token不能为空")
    private String token;
    private Long dirId;
}
