package com.heartcloud.service;

import com.heartcloud.pojo.entity.ShareEntity;
import com.heartcloud.pojo.vo.ShareListVO;
import com.heartcloud.pojo.vo.ShareVO;

import java.util.List;

/**
 * @author zhuZhaoYang
 * @date 2022/5/15 22:14
 */
public interface ShareService {
    /**
     * 分享文件
     *
     * @param fileId    文件
     * @param userId    分享人
     * @param shareDays 分享时间
     * @param password  密码 可空
     * @return 成功信息
     */
    ShareVO shareFile(Long fileId, Long userId, Integer shareDays, String password);

    ShareEntity getShareByToken(String token);

    List<ShareListVO> getShareList(Long userId);

    ShareEntity getShare(Long shareId);

    void updateShare(ShareEntity shareEntity);
}
