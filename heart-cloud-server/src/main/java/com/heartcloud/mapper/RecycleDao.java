package com.heartcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heartcloud.pojo.entity.RecycleEntity;
import com.heartcloud.pojo.vo.RecycleListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zhuZhaoYang
 * @date 2022/5/15 15:51
 */
@Mapper
public interface RecycleDao extends BaseMapper<RecycleEntity> {
    @Select("select id as recycleId ," +
            "IF(fileId is not null," +
            "(select name from file where file.id=fileId)," +
            "(select name from dir where dir.id = dirId)) as name ," +
            "IF(fileId is null,0,1) as type " +
            "from recycle " +
            "where userId = #{userId} " +
            "and status = 0 " +
            "and clearTime > now() " +
            "order by type,name")
    List<RecycleListVO> getList(Long userId);
}
