package com.heartcloud.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zhuZhaoYang
 * @date 2022/5/21 16:53
 */
@Data
public class DestroyDTO {
    @NotNull(message = "recycleId不能为空")
    private Long recycleId;
}
