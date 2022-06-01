package com.heartcloud.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.heartcloud.pojo.dto.*;
import com.heartcloud.pojo.entity.SysLogEntity;
import com.heartcloud.pojo.entity.UserEntity;
import com.heartcloud.pojo.vo.LoginVO;
import com.heartcloud.pojo.vo.UserInfoVO;
import com.heartcloud.service.impl.SysLogServiceImpl;
import com.heartcloud.service.impl.UserServiceImpl;
import com.heartcloud.util.PasswordUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.UpdatableSqlQuery;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author zhuZhaoYang
 * @date 2022/5/14 21:49
 */
@RestController
@RequestMapping("/user")
public class UserController {


    private UserServiceImpl userService;

    @Autowired
    private void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    private SysLogServiceImpl sysLogService;

    @Autowired
    private void setSysLogService(SysLogServiceImpl sysLogService) {
        this.sysLogService = sysLogService;
    }

    @GetMapping("/info")
    public SaResult getUserInfo() {
        String username = (String) StpUtil.getLoginId();
        UserEntity user = userService.getUserByUsername(username);
        UserInfoVO res = new UserInfoVO();
        BeanUtils.copyProperties(user, res);
        return SaResult.data(res);
    }

    @PostMapping("/login")
    public SaResult login(@Valid @RequestBody LoginDTO login) {
        UserEntity user = userService.getUserByUsername(login.getUsername());
        if (!user.getPassword().equals(
                PasswordUtil.md5BySalt(new UAndP(login.getUsername(),
                        login.getPassword())))) {
            return SaResult.error("用户名或密码错误");
        }
        if (user.getStatus().equals(0)) {
            return SaResult.error("账号等待审核");
        }
        if (user.getStatus().equals(2)) {
            return SaResult.error("账号审核未通过");
        }
        if (user.getEnable().equals(0)) {
            return SaResult.error("账号被禁用");
        }
        StpUtil.login(login.getUsername());
        // 插入日志
        SysLogEntity sysLogEntity = new SysLogEntity();
        sysLogEntity.setLoginTime(new Date());
        sysLogEntity.setUserId(user.getId());
        sysLogService.addLog(sysLogEntity);
        // 返回token信息
        LoginVO res = new LoginVO();
        res.setTokenName(StpUtil.getTokenName());
        res.setTokenValue(StpUtil.getTokenValue());
        return SaResult.data(res);
    }

    @SaCheckLogin
    @PostMapping("/logout")
    public SaResult logout() {
        StpUtil.logout();
        return SaResult.ok("退出登录成功");
    }

    @SaCheckLogin
    @PostMapping("/register")
    public SaResult register(@Valid @RequestBody RegisterDTO registerDTO) {
        UserEntity checkusername = userService.getUserByUsername(registerDTO.getUsername());
        if (checkusername != null) {
            return SaResult.error("用户名已存在，请重试！");
        }
        userService.saveUser(registerDTO);
        return SaResult.ok("已提交申请，请等待审核");

    }

    @SaCheckLogin
    @PostMapping("/name")
    public SaResult updateName(@Valid @RequestBody UpNameDTO upNameDTO) {
        String username = (String) StpUtil.getLoginId();
        UserEntity user = userService.getUserByUsername(username);
        if (!user.getName().equals(upNameDTO.getName())) {
            userService.updateName(upNameDTO.getName());
            return SaResult.ok("修改昵称成功！");
        } else {
            return SaResult.error("修改昵称与之前一致，请重试！");
        }
    }

    @SaCheckLogin
    @PostMapping("/password")
    public SaResult updatePassword(@Valid @RequestBody UpPasswordDTO upPasswordDTO) {
        String username = (String) StpUtil.getLoginId();
        UserEntity user = userService.getUserByUsername(username);
        if (!PasswordUtil.md5BySalt(new UAndP(username, upPasswordDTO.getPassword())).equals(PasswordUtil.md5BySalt(new UAndP(username, upPasswordDTO.getNewpassword())))) {
            if (user.getPassword().equals(PasswordUtil.md5BySalt(new UAndP(username, upPasswordDTO.getPassword())))) {
                userService.updatePassword(upPasswordDTO.getNewpassword());
                return SaResult.ok("修改密码成功!");
            }
            return SaResult.error("旧密码错误，请重试！");
        } else {
            return SaResult.error("修改密码与之前一致，请重试！");
        }
    }
}
