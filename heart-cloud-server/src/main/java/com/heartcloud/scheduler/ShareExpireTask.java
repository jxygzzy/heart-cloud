package com.heartcloud.scheduler;

import cn.hutool.core.date.DateUtil;
import com.heartcloud.mapper.ShareDao;
import com.heartcloud.pojo.entity.ShareEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author zhuZhaoYang
 * @date 2022/5/23 15:57
 */
@Component
@Slf4j
public class ShareExpireTask {
    @Autowired
    private ShareDao shareDao;

    @Scheduled(cron = "0 */1 * * * ? ")
    public void execute() {
        log.info("清理分享文件开始=={}==", System.currentTimeMillis());
        List<ShareEntity> shareEntities = shareDao.selectList(null);
        shareEntities.stream().peek(shareEntity -> {
            if (DateUtil.compare(new Date(), shareEntity.getExpireTime()) > 0) {
                shareEntity.setStatus(1);
                shareDao.updateById(shareEntity);
            }
        }).close();
        log.info("清理分享文件结束=={}==", System.currentTimeMillis());
    }
}
