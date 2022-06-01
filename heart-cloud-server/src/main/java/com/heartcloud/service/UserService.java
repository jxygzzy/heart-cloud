package com.heartcloud.service;

import com.heartcloud.pojo.dto.RegisterDTO;
import com.heartcloud.pojo.entity.UserEntity;
import com.heartcloud.pojo.vo.UserVO;

import java.util.List;


/**
 * @author zhuZhaoYang
 * @date 2022/5/14 21:03
 */

public interface UserService {

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户实体
     */
    UserEntity getUserByUsername(String username);

    void saveUser(RegisterDTO registerDTO);

    void updateName(String name);

    void updatePassword(String password);

    List<UserVO> getUserList();

    void updateStatus(Long userId, Integer code);
}
