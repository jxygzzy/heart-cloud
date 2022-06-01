package com.heartcloud.service;

import com.heartcloud.pojo.entity.SysLogEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhuZhaoYang
 * @date 2022/5/15 15:45
 */
public interface SysLogService {
    /**
     * 新增日志
     *
     * @param sysLogEntity 日志实体
     */
    void addLog(SysLogEntity sysLogEntity);

    List<SysLogEntity> getLogs();
}
