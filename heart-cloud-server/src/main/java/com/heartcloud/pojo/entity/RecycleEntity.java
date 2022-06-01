package com.heartcloud.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author zhuZhaoYang
 * @date 2022/5/15 15:33
 */
@Data
@TableName("recycle")
public class RecycleEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("userId")
    private Long userId;
    @TableField("fileId")
    private Long fileId;
    @TableField("dirId")
    private Long dirId;
    @TableField("createTime")
    private Date createTime;
    @TableField("clearTime")
    private Date clearTime;
    @TableField("status")
    private Integer status;
}
