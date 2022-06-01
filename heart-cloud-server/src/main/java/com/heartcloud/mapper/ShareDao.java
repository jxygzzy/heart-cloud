package com.heartcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heartcloud.pojo.entity.ShareEntity;
import com.heartcloud.pojo.vo.ShareListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zhuZhaoYang
 * @date 2022/5/15 22:13
 */
@Mapper
public interface ShareDao extends BaseMapper<ShareEntity> {

    @Select("select id as shareId ," +
            "(select name from file where file.id = fileId) as fileName ," +
            "token," +
            "expireTime " +
            "from share " +
            "where userId = #{userId} " +
            "and status=0 " +
            "order by expireTime desc")
    List<ShareListVO> getList(Long userId);
}
