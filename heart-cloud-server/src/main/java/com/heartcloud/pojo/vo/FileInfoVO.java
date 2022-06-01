package com.heartcloud.pojo.vo;

import com.qiniu.storage.model.FileInfo;
import lombok.Data;

/**
 * @author zhuZhaoYang
 * @date 2022/5/16 19:43
 */
@Data
public class FileInfoVO {
    private FileInfo fileInfo;
    private String hash;
}
