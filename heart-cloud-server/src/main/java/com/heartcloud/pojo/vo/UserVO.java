package com.heartcloud.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhuZhaoYang
 * @date 2022/5/20 16:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    private Long id;
    private String username;
    private String name;
    private String email;
    private String tel;
    private Integer status;
    private Integer enable;
}
