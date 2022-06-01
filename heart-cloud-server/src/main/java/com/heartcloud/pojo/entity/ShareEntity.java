package com.heartcloud.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author zhuZhaoYang
 * @date 2022/5/15 22:09
 */
@Data
@TableName("share")
public class ShareEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("fileId")
    private Long fileId;
    @TableField("password")
    private String password;
    @TableField("type")
    private Integer type;
    @TableField("status")
    private Integer status;
    @TableField("createTime")
    private Date createTime;
    @TableField("expireTime")
    private Date expireTime;
    @TableField("userId")
    private Long userId;
    @TableField("token")
    private String token;
}
