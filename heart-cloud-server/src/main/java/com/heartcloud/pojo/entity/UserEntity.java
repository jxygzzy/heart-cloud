package com.heartcloud.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhuZhaoYang
 * @date 2022/5/14 20:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class UserEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("username")
    private String username;
    @TableField("password")
    private String password;
    @TableField("name")
    private String name;
    @TableField("status")
    private Integer status;
    @TableField("role")
    private Integer role;
    @TableField("email")
    private String email;
    @TableField("tel")
    private String tel;
    @TableField("enable")
    private Integer enable;
}
