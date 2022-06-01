package com.heartcloud.service.impl;

import cn.dev33.satoken.exception.SaTokenException;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.heartcloud.config.QiniuConfig;
import com.heartcloud.pojo.vo.QiniuPolicyVO;
import com.heartcloud.service.QiniuServrice;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.DownloadUrl;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author zhuZhaoYang
 * @date 2022/5/15 16:26
 */
@Service
@Slf4j
public class QiniuServiceImpl implements QiniuServrice {


    @Autowired
    private BucketManager bucketManager;

    @Autowired
    private Auth auth;

    @Autowired
    private QiniuConfig qiniuConfig;


    /**
     * 删除
     *
     * @param key
     * @return
     */
    @Override
    public Boolean deleteFile(String key) {
        if (checkFile(key) == null) {
            return true;
        } else {
            try {
                bucketManager.delete(qiniuConfig.getBucket(), key);
                return true;
            } catch (QiniuException e) {
                log.error(e.getMessage());
                throw new SaTokenException("云存储服务故障");
            }
        }
    }

    /**
     * 检查文件是否存在于七牛云
     *
     * @param key
     * @return
     */
    @Override
    public FileInfo checkFile(String key) {
        FileInfo fileInfo = null;
        try {
            fileInfo = bucketManager.stat(qiniuConfig.getBucket(), key);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return fileInfo;
    }


    /**
     * 获取上传凭证
     *
     * @param loginId 用户名
     * @return 上传凭证
     */
    @Override
    public QiniuPolicyVO getQiniuPolicy(String loginId) {
        StringMap putPolicy = new StringMap();
        putPolicy.put("callbackBody", "{\"key\":\"$(key)\"," +
                "\"hash\":\"$(etag)\"," +
                "\"bucket\":\"$(bucket)\"," +
                "\"fsize\":$(fsize)}");
        putPolicy.put("callbackBodyType", "application/json");
        QiniuPolicyVO qiniuPolicyVO = new QiniuPolicyVO();
        qiniuPolicyVO.setToken(auth.uploadToken(qiniuConfig.getBucket(),
                null, qiniuConfig.getExpireSeconds(), putPolicy));
        qiniuPolicyVO.setUrl(qiniuConfig.getPath());
        qiniuPolicyVO.setDirPrefix("user" + loginId + "/");
        return qiniuPolicyVO;
    }

    @Override
    public String getDownloadUrl(String key, String name) {
        // domain   下载 domain, eg: qiniu.com【必须】
        // useHttps 是否使用 https【必须】
        // key      下载资源在七牛云存储的 key【必须】
        DownloadUrl url = new DownloadUrl(qiniuConfig.getDownload(), false, key);
        // 配置 attname
        url.setAttname(name);
        // 带有效期
        long expireInSeconds = qiniuConfig.getExpireSeconds();
        Auth auth = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());
        String urlString = null;
        try {
            urlString = url.buildURL(auth, System.currentTimeMillis() + expireInSeconds);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return urlString;
    }

}
