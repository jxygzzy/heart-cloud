package com.heartcloud.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author zhuZhaoYang
 * @date 2022/5/15 15:35
 */
@Data
@TableName("sys_log")
public class SysLogEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("userId")
    private Long userId;
    @TableField("loginTime")
    private Date loginTime;
}
