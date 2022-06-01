package com.heartcloud.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.temp.SaTempUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.heartcloud.mapper.ShareDao;
import com.heartcloud.pojo.entity.ShareEntity;
import com.heartcloud.pojo.vo.ShareListVO;
import com.heartcloud.pojo.vo.ShareVO;
import com.heartcloud.service.ShareService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author zhuZhaoYang
 * @date 2022/5/15 22:14
 */
@Service
public class ShareServiceImpl implements ShareService {

    @Value("${sharePrefix}")
    private String sharePrefix;

    @Autowired
    private ShareDao shareDao;

    @Override
    public ShareVO shareFile(Long fileId, Long userId, Integer shareDays, String password) {
        ShareEntity shareEntity = new ShareEntity();
        shareEntity.setFileId(fileId);
        shareEntity.setUserId(userId);
        shareEntity.setCreateTime(new Date());
        shareEntity.setExpireTime(DateUtil.offsetDay(shareEntity.getCreateTime(), shareDays));
        shareEntity.setStatus(0);
        shareEntity.setToken(RandomStringUtils.randomAlphanumeric(8));
        if (password == null) {
            shareEntity.setType(0);
        } else {
            shareEntity.setType(1);
            shareEntity.setPassword(password);
        }
        shareDao.insert(shareEntity);
        ShareVO shareVO = new ShareVO();
        shareVO.setPassword(password);
        shareVO.setToken(shareEntity.getToken());
        return shareVO;
    }

    @Override
    public ShareEntity getShareByToken(String token) {
        ShareEntity shareEntity = shareDao.selectOne(new QueryWrapper<ShareEntity>().eq("token", token).eq("status", 0));
        if (DateUtil.compare(new Date(), shareEntity.getExpireTime()) >= 0) {
            // 过期
            shareEntity.setStatus(1);
            shareDao.updateById(shareEntity);
            return null;
        }
        return shareEntity;
    }

    @Override
    public List<ShareListVO> getShareList(Long userId) {
        return shareDao.getList(userId);
    }


    @Override
    public ShareEntity getShare(Long shareId) {
        return shareDao.selectById(shareId);
    }

    @Override
    public void updateShare(ShareEntity shareEntity) {
        shareDao.updateById(shareEntity);
    }
}
