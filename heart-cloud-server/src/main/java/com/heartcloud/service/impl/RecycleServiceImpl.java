package com.heartcloud.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.heartcloud.mapper.RecycleDao;
import com.heartcloud.pojo.entity.RecycleEntity;
import com.heartcloud.pojo.vo.RecycleListVO;
import com.heartcloud.service.RecycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhuZhaoYang
 * @date 2022/5/15 15:54
 */
@Service
public class RecycleServiceImpl implements RecycleService {
    @Autowired
    private RecycleDao recycleDao;

    @Autowired
    private FileServiceImpl fileService;
    @Autowired
    private DirServiceImpl dirService;

    @Override
    public List<RecycleListVO> getList(Long userId) {
        return recycleDao.getList(userId);
    }

    @Override
    public void destroy(Long recycleId) {
        RecycleEntity recycleEntity = recycleDao.selectById(recycleId);
        recycleEntity.setStatus(1);
        if (recycleEntity.getFileId() != null) {
            fileService.destroy(recycleEntity.getFileId());
        }
        if (recycleEntity.getDirId() != null) {
            dirService.destroy(recycleEntity.getDirId());
        }
        recycleDao.updateById(recycleEntity);
    }

    @Override
    public void recovery(Long recycleId) {
        RecycleEntity recycleEntity = recycleDao.selectById(recycleId);
        recycleEntity.setStatus(2);
        if (recycleEntity.getFileId() != null) {
            fileService.recovery(recycleEntity.getFileId());
        }
        if (recycleEntity.getDirId() != null) {
            dirService.recovery(recycleEntity.getDirId());
        }
        recycleDao.updateById(recycleEntity);
    }

    @Override
    public RecycleEntity getById(Long recycleId) {
        return recycleDao.selectById(recycleId);
    }
}
