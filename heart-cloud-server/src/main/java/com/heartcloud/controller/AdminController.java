package com.heartcloud.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.heartcloud.pojo.dto.LoginDTO;
import com.heartcloud.pojo.dto.UAndP;
import com.heartcloud.pojo.entity.SysLogEntity;
import com.heartcloud.pojo.entity.UserEntity;
import com.heartcloud.pojo.vo.LoginVO;
import com.heartcloud.pojo.vo.UserVO;
import com.heartcloud.service.impl.SysLogServiceImpl;
import com.heartcloud.service.impl.UserServiceImpl;
import com.heartcloud.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author zhuZhaoYang
 * @date 2022/5/20 15:56
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserServiceImpl userService;

    private SysLogServiceImpl sysLogService;

    @Autowired
    private void setSysLogService(SysLogServiceImpl sysLogService) {
        this.sysLogService = sysLogService;
    }

    @PostMapping("/login")
    public SaResult login(@Valid @RequestBody LoginDTO login) {
        UserEntity user = userService.getUserByUsername(login.getUsername());
        if (!user.getPassword().equals(PasswordUtil.md5BySalt(new UAndP(login.getUsername(), login.getPassword())))) {
            return SaResult.error("用户名或密码错误");
        }
        if (!user.getRole().equals(1)) {
            return SaResult.error("请使用管理员账号登录");
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
    @SaCheckRole("admin")
    @GetMapping("/user/list")
    public SaResult getUserList() {
        List<UserVO> users = userService.getUserList();
        return SaResult.data(users);
    }

    @SaCheckLogin
    @SaCheckRole("admin")
    @GetMapping("/user/audit/{userId}")
    public SaResult allowUser(@PathVariable Long userId,
                              @NotNull(message = "审核结果不能为空") @RequestParam Integer code) {
        userService.updateStatus(userId, code);
        return SaResult.ok("审核成功");
    }

    @SaCheckLogin
    @SaCheckRole("admin")
    @GetMapping("/log")
    public SaResult getLog() {
        List<SysLogEntity> logs = sysLogService.getLogs();
        return SaResult.data(logs);
    }
}
