package com.heartcloud.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhuZhaoYang
 * @date 2022/5/14 22:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVO {
    private String tokenName;
    private String tokenValue;
}
