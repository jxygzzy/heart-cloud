package com.heartcloud.service;

import com.alibaba.fastjson.JSONObject;
import com.heartcloud.pojo.vo.QiniuPolicyVO;
import com.qiniu.storage.model.FileInfo;

/**
 * @author zhuZhaoYang
 * @date 2022/5/15 16:24
 */
public interface QiniuServrice {

    /**
     * 删除七牛云文件
     */
    Boolean deleteFile(String key);

    /**
     * 检查文件是否存在于七牛云
     */
    FileInfo checkFile(String key);

    /**
     * 前端上传凭证
     */
    QiniuPolicyVO getQiniuPolicy(String loginId);

    String getDownloadUrl(String key,String name);
}
