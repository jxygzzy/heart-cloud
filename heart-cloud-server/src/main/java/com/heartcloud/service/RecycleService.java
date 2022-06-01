package com.heartcloud.service;

import com.heartcloud.pojo.entity.RecycleEntity;
import com.heartcloud.pojo.vo.RecycleListVO;

import java.util.List;

/**
 * @author zhuZhaoYang
 * @date 2022/5/15 15:53
 */
public interface RecycleService {
    List<RecycleListVO> getList(Long userId);

    RecycleEntity getById(Long recycleId);

    void recovery(Long recycleId);

    void destroy(Long recycleId);
}
