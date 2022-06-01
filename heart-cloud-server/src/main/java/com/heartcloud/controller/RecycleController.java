package com.heartcloud.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.heartcloud.pojo.dto.DestroyDTO;
import com.heartcloud.pojo.dto.RecoveryDTO;
import com.heartcloud.pojo.entity.RecycleEntity;
import com.heartcloud.pojo.entity.UserEntity;
import com.heartcloud.pojo.vo.RecycleListVO;
import com.heartcloud.service.impl.RecycleServiceImpl;
import com.heartcloud.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author zhuZhaoYang
 * @date 2022/5/21 16:01
 */
@RestController
@RequestMapping("/recycle")
public class RecycleController {
    @Autowired
    private RecycleServiceImpl recycleService;

    @Autowired
    private UserServiceImpl userService;

    @SaCheckLogin
    @SaCheckRole("user")
    @GetMapping("/list")
    public SaResult getRecycleList() {
        String username = (String) StpUtil.getLoginId();
        UserEntity user = userService.getUserByUsername(username);
        List<RecycleListVO> recycleList = recycleService.getList(user.getId());
        return SaResult.data(recycleList);
    }

    @SaCheckLogin
    @SaCheckRole("user")
    @PostMapping("/recovery")
    public SaResult recovery(@Valid @RequestBody RecoveryDTO recoveryDTO) {
        String username = (String) StpUtil.getLoginId();
        UserEntity user = userService.getUserByUsername(username);
        RecycleEntity recycleEntity = recycleService.getById(recoveryDTO.getRecycleId());
        if (!user.getId().equals(recycleEntity.getUserId())) {
            return SaResult.error("userId不一致");
        }
        recycleService.recovery(recoveryDTO.getRecycleId());
        return SaResult.ok("已恢复");
    }

    @SaCheckRole("user")
    @SaCheckLogin
    @PostMapping("/destroy")
    public SaResult destroy(@Valid @RequestBody DestroyDTO destroyDTO) {
        String username = (String) StpUtil.getLoginId();
        UserEntity user = userService.getUserByUsername(username);
        RecycleEntity recycleEntity = recycleService.getById(destroyDTO.getRecycleId());
        if (!user.getId().equals(recycleEntity.getUserId())) {
            return SaResult.error("userId不一致");
        }
        recycleService.destroy(destroyDTO.getRecycleId());
        return SaResult.ok("已彻底删除");
    }
}
