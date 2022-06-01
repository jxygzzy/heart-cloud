package com.heartcloud.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.heartcloud.mapper.DirDao;
import com.heartcloud.mapper.FileDao;
import com.heartcloud.mapper.RecycleDao;
import com.heartcloud.mapper.ShareDao;
import com.heartcloud.pojo.entity.DirEntity;
import com.heartcloud.pojo.entity.FileEntity;
import com.heartcloud.pojo.entity.RecycleEntity;
import com.heartcloud.pojo.entity.ShareEntity;
import com.heartcloud.service.FileService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zhuZhaoYang
 * @date 2022/5/15 15:53
 */
@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileDao fileDao;

    @Autowired
    private RecycleDao recycleDao;

    @Autowired
    private ShareDao shareDao;

    @Override
    public void addFile(FileEntity fileEntity) {
        fileDao.insert(fileEntity);
    }

    @Override
    public FileEntity getFile(Long fileId) {
        return fileDao.selectById(fileId);
    }

    @Override
    public void deleteFile(Long fileId, Long userId) {
        FileEntity fileEntity = fileDao.selectById(fileId);
        fileEntity.setStatus(1);
        fileDao.updateById(fileEntity);
        RecycleEntity recycleEntity = new RecycleEntity();
        recycleEntity.setFileId(fileId);
        recycleEntity.setUserId(userId);
        recycleEntity.setCreateTime(new Date());
        // 30天后的时间戳
        recycleEntity.setClearTime(DateUtil.offsetDay(recycleEntity.getCreateTime(), 30));
        recycleEntity.setStatus(0);
        recycleDao.insert(recycleEntity);
    }

    @Override
    public FileEntity getFileByKey(String key) {
        List<FileEntity> files = fileDao.selectList(new QueryWrapper<FileEntity>().eq("hash", key));
        if (files.size() > 0) {
            return files.get(0);
        }
        return null;
    }

    @Override
    public void saveShare(Long shareId, Long dirId, Long userId) {
        ShareEntity shareEntity = shareDao.selectById(shareId);
        FileEntity fileEntity = fileDao.selectById(shareEntity.getFileId());
        FileEntity file = new FileEntity();
        BeanUtils.copyProperties(fileEntity, file);
        file.setId(null);
        file.setDirId(dirId);
        file.setUserId(userId);
        file.setCreateTime(new Date());
        fileDao.insert(file);
    }

    @Override
    public void moveFile(Long fileId, Long dirId) {
        FileEntity fileEntity = fileDao.selectById(fileId);
        fileEntity.setDirId(dirId);
        fileDao.updateById(fileEntity);
    }

    @Override
    public FileEntity hasName(String name, Long dirId, Long userId) {
        FileEntity fileEntity;
        if (dirId != null) {
            fileEntity = fileDao.selectOne(new QueryWrapper<FileEntity>().eq("name", name).eq("userId", userId).eq("dirId", dirId).eq("status", 0));
        } else {
            fileEntity = fileDao.selectOne(new QueryWrapper<FileEntity>().eq("name", name).eq("userId", userId).isNull("dirId").eq("status", 0));
        }
        return fileEntity;
    }

    @Override
    public void destroy(Long fileId) {
        FileEntity fileEntity = fileDao.selectById(fileId);
        fileEntity.setStatus(2);
        fileDao.updateById(fileEntity);
    }

    @Override
    public void recovery(Long fileId) {
        FileEntity fileEntity = fileDao.selectById(fileId);
        fileEntity.setStatus(0);
        fileDao.updateById(fileEntity);
    }

    @Override
    public void update(FileEntity fileEntity) {
        fileDao.updateById(fileEntity);
    }
}
