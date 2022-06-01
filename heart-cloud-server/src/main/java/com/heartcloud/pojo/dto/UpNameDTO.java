package com.heartcloud.pojo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpNameDTO {
    @NotBlank(message = "修改的昵称不能为空")
    private String name;
}
