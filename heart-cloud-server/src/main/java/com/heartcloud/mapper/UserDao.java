package com.heartcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heartcloud.pojo.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhuZhaoYang
 * @date 2022/5/14 21:02
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {
}
