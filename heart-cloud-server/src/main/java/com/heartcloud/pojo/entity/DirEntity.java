package com.heartcloud.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author zhuZhaoYang
 * @date 2022/5/15 15:20
 */
@Data
@TableName("dir")
public class DirEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("name")
    private String name;
    @TableField("parentId")
    private Long parentId;
    @TableField("userId")
    private Long userId;
    @TableField("status")
    private Integer status;
}
