package com.heartcloud.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpPasswordDTO {
    @NotBlank(message = "请输入您的旧密码")
    private String password;
    @NotBlank(message = "请输入你的新密码")
    private String newpassword;
}
