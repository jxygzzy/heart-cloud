package com.heartcloud.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhuZhaoYang
 * @date 2022/5/14 20:52
 * 存储用户名和密码
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UAndP {
    private String username;
    private String password;
}
