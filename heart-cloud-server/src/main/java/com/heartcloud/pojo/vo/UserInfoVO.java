package com.heartcloud.pojo.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhuZhaoYang
 * @date 2022/5/14 22:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVO {
    private String username;
    private String name;
    private String email;
    private String tel;
}
