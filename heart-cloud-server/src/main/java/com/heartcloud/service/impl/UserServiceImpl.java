package com.heartcloud.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.heartcloud.mapper.UserDao;
import com.heartcloud.pojo.dto.RegisterDTO;
import com.heartcloud.pojo.dto.UAndP;
import com.heartcloud.pojo.entity.UserEntity;
import com.heartcloud.pojo.vo.UserVO;
import com.heartcloud.service.UserService;
import com.heartcloud.util.PasswordUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zhuZhaoYang
 * @date 2022/5/14 21:03
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserEntity getUserByUsername(String username) {
        return userDao.selectOne(new QueryWrapper<UserEntity>().eq("username", username));
    }

    @Override
    public void saveUser(RegisterDTO registerDTO) { //存储用户注册信息
        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(registerDTO, user);
        user.setStatus(0);  //状态默认设置为未审核
        user.setRole(0);    //角色设为用户
        user.setPassword(PasswordUtil.md5BySalt(new UAndP(user.getUsername(), user.getPassword())));
        userDao.insert(user);
    }

    @Override
    public void updateName(String name) {    //用户修改昵称
        UpdateWrapper<UserEntity> updataWrapper = new UpdateWrapper<>();
        String username = (String) StpUtil.getLoginId();
        updataWrapper.eq("username", username).set("name", name);
        userDao.update(null, updataWrapper);
    }

    @Override
    public void updatePassword(String password) {    //用户修改密码
        UpdateWrapper<UserEntity> updataWrapper = new UpdateWrapper<>();
        String username = (String) StpUtil.getLoginId();
        updataWrapper.eq("username", username).set("password", PasswordUtil.md5BySalt(new UAndP(username, password)));
        userDao.update(null, updataWrapper);
    }

    @Override
    public List<UserVO> getUserList() {
        List<UserEntity> userEntities = userDao.selectList(new QueryWrapper<UserEntity>().eq("role", 0));
        List<UserVO> users = new LinkedList<>();
        for (UserEntity userEntity : userEntities) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(userEntity, userVO);
            users.add(userVO);
        }
        return users;
    }

    @Override
    public void updateStatus(Long userId, Integer code) {
        UserEntity userEntity = userDao.selectById(userId);
        if (!userEntity.getStatus().equals(0)) {
            return;
        }
        userEntity.setStatus(code);
        userDao.updateById(userEntity);
    }
}
