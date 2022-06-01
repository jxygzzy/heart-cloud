package com.heartcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heartcloud.pojo.entity.FileEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhuZhaoYang
 * @date 2022/5/15 15:51
 */
@Mapper
public interface FileDao extends BaseMapper<FileEntity> {
}
