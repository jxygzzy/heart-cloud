package com.heartcloud;

import com.heartcloud.pojo.dto.UAndP;
import com.heartcloud.util.PasswordUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zhuZhaoYang
 * @date 2022/5/14 20:54
 */
@SpringBootTest
public class PasswordGenTest {

    @Test
    void genMd5() {
        String heart = PasswordUtil.md5BySalt(new UAndP("heart", "123456"));
        String heart_admin = PasswordUtil.md5BySalt(new UAndP("heartadmin", "123456"));
        System.out.println("heart=>" + heart);
        System.out.println("heartadmin=>" + heart_admin);
    }
}
