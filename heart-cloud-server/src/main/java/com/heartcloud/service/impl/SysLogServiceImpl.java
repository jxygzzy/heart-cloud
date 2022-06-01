package com.heartcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.heartcloud.mapper.SysLogDao;
import com.heartcloud.pojo.entity.SysLogEntity;
import com.heartcloud.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhuZhaoYang
 * @date 2022/5/15 15:45
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    private SysLogDao sysLogDao;

    @Autowired
    private void setSysLogDao(SysLogDao sysLogDao) {
        this.sysLogDao = sysLogDao;
    }

    @Override
    public void addLog(SysLogEntity sysLogEntity) {
        sysLogDao.insert(sysLogEntity);
    }

    @Override
    public List<SysLogEntity> getLogs() {
        return sysLogDao.selectList(new QueryWrapper<SysLogEntity>().orderByDesc("loginTime"));
    }
}
