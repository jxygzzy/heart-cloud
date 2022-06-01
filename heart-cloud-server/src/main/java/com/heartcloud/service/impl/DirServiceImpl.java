package com.heartcloud.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.heartcloud.mapper.DirDao;
import com.heartcloud.mapper.FileDao;
import com.heartcloud.mapper.RecycleDao;
import com.heartcloud.pojo.entity.DirEntity;
import com.heartcloud.pojo.entity.FileEntity;
import com.heartcloud.pojo.entity.RecycleEntity;
import com.heartcloud.pojo.vo.FileListVO;
import com.heartcloud.service.DirService;
import com.zaxxer.hikari.util.FastList;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zhuZhaoYang
 * @date 2022/5/15 15:53
 */
@Service
public class DirServiceImpl implements DirService {
    @Autowired
    private DirDao dirDao;

    @Autowired
    private FileDao fileDao;

    @Autowired
    private RecycleDao recycleDao;

    @Override
    public List<FileListVO> getFileList(Long dirId, Long userId) {
        List<FileListVO> fileListVO = new LinkedList<>();
        List<DirEntity> dirEntities;
        List<FileEntity> fileEntities;
        if (dirId != null) {
            dirEntities = dirDao.selectList(new QueryWrapper<DirEntity>()
                    .eq("parentId", dirId).eq("userId", userId)
                    .eq("status", 0).orderByAsc("name"));

            fileEntities = fileDao.selectList(new QueryWrapper<FileEntity>()
                    .eq("dirId", dirId).eq("userId", userId)
                    .eq("status", 0).orderByAsc("name"));

        } else {
            dirEntities = dirDao.selectList(new QueryWrapper<DirEntity>()
                    .eq("status", 0)
                    .eq("userId", userId).isNull("parentId").orderByAsc("name"));
            fileEntities = fileDao.selectList(new QueryWrapper<FileEntity>()
                    .eq("status", 0)
                    .eq("userId", userId).isNull("dirId").orderByAsc("name"));
        }
        for (DirEntity dirEntity : dirEntities) {
            FileListVO file = new FileListVO();
            BeanUtils.copyProperties(dirEntity, file);
            file.setType(0);
            fileListVO.add(file);
        }
        for (FileEntity fileEntity : fileEntities) {
            FileListVO file = new FileListVO();
            BeanUtils.copyProperties(fileEntity, file);
            file.setType(1);
            file.setParentId(fileEntity.getDirId());
            fileListVO.add(file);
        }
        return fileListVO;
    }

    @Override
    public DirEntity getDir(Long dirId) {
        return dirDao.selectById(dirId);
    }

    @Override
    public void deleteDir(Long dirId, Long userId) {
        DirEntity dirEntity = dirDao.selectById(dirId);
        dirEntity.setStatus(1);
        dirDao.updateById(dirEntity);
        RecycleEntity recycleEntity = new RecycleEntity();
        recycleEntity.setDirId(dirId);
        recycleEntity.setUserId(userId);
        recycleEntity.setStatus(0);
        recycleEntity.setCreateTime(new Date());
        recycleEntity.setClearTime(DateUtil.offsetDay(recycleEntity.getCreateTime(), 30));
        recycleDao.insert(recycleEntity);
    }

    @Override
    public List<DirEntity> getDirList(Long parentId, Long userId) {
        return dirDao.selectList(new QueryWrapper<DirEntity>()
                .eq("parentId", parentId).eq("userId", userId)
                .eq("status", 0));
    }

    @Override
    public Boolean hasName(String name, Long parentId, Long userId) {

        DirEntity dirEntity;
        if (parentId != null) {
            dirEntity = dirDao.selectOne(new QueryWrapper<DirEntity>().eq("name", name).eq("parentId", parentId).eq("userId", userId));
        } else {
            dirEntity = dirDao.selectOne(new QueryWrapper<DirEntity>().eq("name", name).eq("userId", userId).isNull("parentId"));
        }
        return dirEntity != null;
    }

    @Override
    public void destroy(Long dirId) {
        DirEntity dirEntity = dirDao.selectById(dirId);
        dirEntity.setStatus(2);
        dirDao.updateById(dirEntity);
    }

    @Override
    public void recovery(Long dirId) {
        DirEntity dirEntity = dirDao.selectById(dirId);
        dirEntity.setStatus(0);
        dirDao.updateById(dirEntity);
    }

    @Override
    public void save(String name, Long parentId, Long userId) {
        DirEntity dirEntity = new DirEntity();
        dirEntity.setUserId(userId);
        dirEntity.setParentId(parentId);
        dirEntity.setName(name);
        dirEntity.setStatus(0);
        dirDao.insert(dirEntity);
    }
}
