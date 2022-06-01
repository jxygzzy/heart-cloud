package com.heartcloud.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @author zhuZhaoYang
 * @date 2022/5/15 15:24
 */
@Data
@TableName("file")
public class FileEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("name")
    private String name;
    @TableField("`key`")
    private String key;
    @TableField(value = "dirId", updateStrategy = FieldStrategy.IGNORED)
    private Long dirId;
    @TableField("userId")
    private Long userId;
    @TableField("createTime")
    private Date createTime;
    @TableField("status")
    private Integer status;
}
