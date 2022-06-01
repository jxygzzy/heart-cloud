package com.heartcloud.util;

import cn.dev33.satoken.secure.SaSecureUtil;
import com.heartcloud.pojo.dto.UAndP;
import org.springframework.stereotype.Component;

/**
 * @author zhuZhaoYang
 * @date 2022/5/14 20:48
 */
@Component
public class PasswordUtil {

    public static String md5BySalt(UAndP uAndP) {
        // md5加盐加密: md5(md5(str) + md5(salt)) password+username
        return SaSecureUtil.md5BySalt(uAndP.getPassword(), uAndP.getUsername());
    }

}
