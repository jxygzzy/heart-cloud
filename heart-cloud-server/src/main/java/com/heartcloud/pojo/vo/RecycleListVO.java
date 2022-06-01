package com.heartcloud.pojo.vo;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * @author zhuZhaoYang
 * @date 2022/5/23 14:39
 */
@Data
public class RecycleListVO {
    private Long recycleId;
    private String name;
    private Integer type;
}
