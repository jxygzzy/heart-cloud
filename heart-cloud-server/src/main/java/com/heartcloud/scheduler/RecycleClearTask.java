package com.heartcloud.scheduler;

import cn.hutool.core.date.DateUtil;
import com.heartcloud.mapper.RecycleDao;
import com.heartcloud.pojo.entity.RecycleEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author zhuZhaoYang
 * @date 2022/5/23 15:02
 */
@Component
@Slf4j
public class RecycleClearTask {
    @Autowired
    private RecycleDao recycleDao;

    @Scheduled(cron = "0 */1 * * * ? ")
    public void execute() {
        log.info("清理回收站文件开始=={}==", System.currentTimeMillis());
        List<RecycleEntity> recycleEntities = recycleDao.selectList(null);
        recycleEntities.stream().peek(recycleEntity -> {
            if (DateUtil.compare(new Date(), recycleEntity.getClearTime()) > 0) {
                recycleEntity.setStatus(1);
                recycleDao.updateById(recycleEntity);
            }
        }).close();
        log.info("清理回收站文件结束=={}==", System.currentTimeMillis());
    }
}
