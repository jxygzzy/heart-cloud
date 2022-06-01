package com.heartcloud.service;

import com.heartcloud.pojo.entity.FileEntity;

/**
 * @author zhuZhaoYang
 * @date 2022/5/15 15:52
 */
public interface FileService {
    void addFile(FileEntity fileEntity);

    FileEntity getFile(Long fileId);

    void deleteFile(Long fileId,Long userId);

    FileEntity getFileByKey(String key);

    void saveShare(Long shareId, Long dirId, Long userId);

    void moveFile(Long fileId, Long dirId);

    void recovery(Long fileId);

    void destroy(Long fileId);


    FileEntity hasName(String name, Long dirId, Long userId);

    void update(FileEntity fileEntity);
}
