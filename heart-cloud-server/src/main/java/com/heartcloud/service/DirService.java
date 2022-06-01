package com.heartcloud.service;

import com.heartcloud.pojo.entity.DirEntity;
import com.heartcloud.pojo.vo.FileListVO;

import java.util.List;

/**
 * @author zhuZhaoYang
 * @date 2022/5/15 15:52
 */
public interface DirService {
    /**
     * 获取当前目录下文件和子目录
     *
     * @param dirId  目录Id
     * @param userId 用户Id
     * @return 目录信息
     */
    List<FileListVO> getFileList(Long dirId, Long userId);

    DirEntity getDir(Long dirId);

    void deleteDir(Long dirId, Long userId);

    void save(String name, Long parentId, Long userId);

    List<DirEntity> getDirList(Long parentId, Long userId);

    void recovery(Long dirId);

    void destroy(Long dirId);

    Boolean hasName(String name, Long parentId, Long userId);
}
