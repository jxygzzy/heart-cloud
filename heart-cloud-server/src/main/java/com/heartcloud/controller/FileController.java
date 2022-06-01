package com.heartcloud.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.heartcloud.pojo.dto.*;
import com.heartcloud.pojo.entity.DirEntity;
import com.heartcloud.pojo.entity.FileEntity;
import com.heartcloud.pojo.entity.ShareEntity;
import com.heartcloud.pojo.entity.UserEntity;
import com.heartcloud.pojo.vo.*;
import com.heartcloud.service.impl.*;
import com.qiniu.storage.model.FileInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author zhuZhaoYang
 * @date 2022/5/15 16:01
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private QiniuServiceImpl qiniuService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private FileServiceImpl fileService;

    @Autowired
    private DirServiceImpl dirService;

    @Autowired
    private ShareServiceImpl shareService;

    @SaCheckLogin
    @SaCheckRole("user")
    @GetMapping("/check/{key}")
    public SaResult checkFile(@PathVariable String key) {
        // date为null说明不存在
        FileEntity fileEntity = fileService.getFileByKey(key);
        if (fileEntity != null) {
            FileInfo fileInfo = qiniuService.checkFile(key);
            FileInfoVO fileInfoVO = new FileInfoVO();
            fileInfoVO.setFileInfo(fileInfo);
            return SaResult.data(fileInfoVO);
        }
        // data为null 说明文件不存在
        return SaResult.ok();
    }

    @SaCheckLogin
    @SaCheckRole("user")
    @GetMapping("/policy")
    public SaResult getPolicy() {
        String loginId = (String) StpUtil.getLoginId();
        UserEntity user = userService.getUserByUsername(loginId);
        QiniuPolicyVO policy = qiniuService.getQiniuPolicy(user.getId().toString());
        return SaResult.data(policy);
    }

    @SaCheckLogin
    @SaCheckRole("user")
    @PostMapping("/save")
    public SaResult addFile(@Valid @RequestBody FileDTO file) {
        String loginId = (String) StpUtil.getLoginId();
        UserEntity user = userService.getUserByUsername(loginId);
        if (file.getDirId() != null) {
            DirEntity dir = dirService.getDir(file.getDirId());
            if (!dir.getUserId().equals(user.getId())) {
                return SaResult.error("目录不属于你，保存文件失败");
            }
        }
        FileEntity fileEntity = fileService.hasName(file.getName(), file.getDirId(), user.getId());
        if (fileEntity == null) {
            fileEntity = new FileEntity();
            BeanUtils.copyProperties(file, fileEntity);
            fileEntity.setUserId(user.getId());
            fileEntity.setCreateTime(new Date());
            fileEntity.setStatus(0);
            fileService.addFile(fileEntity);
        } else {
            fileEntity.setKey(file.getKey());
            fileService.update(fileEntity);
        }
        return SaResult.ok("上传文件成功");
    }

    @SaCheckLogin
    @SaCheckRole("user")
    @GetMapping("/url/{fileId}")
    public SaResult getDownloadUrl(@PathVariable Long fileId) {
        FileEntity file = fileService.getFile(fileId);
        String url = qiniuService.getDownloadUrl(file.getKey(), file.getName());
        return SaResult.ok().set("url", url);
    }


    @SaCheckLogin
    @SaCheckRole("user")
    @GetMapping("/list")
    public SaResult getFileList(@RequestParam(required = false) Long dirId) {
        String username = (String) StpUtil.getLoginId();
        UserEntity user = userService.getUserByUsername(username);
        // dirId为空表示根目录
        List<FileListVO> fileListVO = dirService.getFileList(dirId, user.getId());
        return SaResult.data(fileListVO);
    }

    @SaCheckLogin
    @SaCheckRole("user")
    @PostMapping("/dir")
    public SaResult addDir(@Valid @RequestBody DirDTO dirDTO) {
        String username = (String) StpUtil.getLoginId();
        UserEntity user = userService.getUserByUsername(username);
        if (dirDTO.getParentId() != null) {
            DirEntity dir = dirService.getDir(dirDTO.getParentId());
            if (!dir.getUserId().equals(user.getId())) {
                return SaResult.error("父目录不属于你，新建失败");
            }
        }
        Boolean hasName = dirService.hasName(dirDTO.getName(), dirDTO.getParentId(), user.getId());
        if (hasName) {
            return SaResult.error("目录名已存在");
        }
        dirService.save(dirDTO.getName(), dirDTO.getParentId(), user.getId());
        return SaResult.ok("新建目录成功");
    }

    @SaCheckLogin
    @SaCheckRole("user")
    @DeleteMapping("/{fileId}")
    public SaResult deleteFile(@NotNull(message = "文件Id不能为空") @PathVariable Long fileId) {
        String username = (String) StpUtil.getLoginId();
        UserEntity user = userService.getUserByUsername(username);
        FileEntity fileEntity = fileService.getFile(fileId);
        if (!user.getId().equals(fileEntity.getUserId())) {
            return SaResult.error("文件不属于你，删除失败");
        }
        fileService.deleteFile(fileId, user.getId());
        return SaResult.ok("文件已保存至回收站，30天内可还原");
    }

    @SaCheckLogin
    @SaCheckRole("user")
    @DeleteMapping("/dir/{dirId}")
    public SaResult deleteDir(@NotNull(message = "目录Id不能为空") @PathVariable Long dirId) {
        String username = (String) StpUtil.getLoginId();
        UserEntity user = userService.getUserByUsername(username);
        DirEntity dirEntity = dirService.getDir(dirId);
        if (!user.getId().equals(dirEntity.getUserId())) {
            return SaResult.error("文件夹不属于你，删除失败");
        }
        dirService.deleteDir(dirId, user.getId());
        return SaResult.ok("文件夹已保存至回收站，30天内可还原");
    }


    @SaCheckLogin
    @SaCheckRole("user")
    @PostMapping("/share")
    public SaResult shareFile(@Valid @RequestBody ShareDTO shareDTO) {
        String username = (String) StpUtil.getLoginId();
        UserEntity user = userService.getUserByUsername(username);
        FileEntity fileEntity = fileService.getFile(shareDTO.getFileId());
        if (!user.getId().equals(fileEntity.getUserId())) {
            return SaResult.error("文件不属于你，分享失败");
        }
        if (!fileEntity.getStatus().equals(0)) {
            return SaResult.error("文件不存在或已删除，分享失败");
        }
        ShareVO shareVO = shareService.shareFile(shareDTO.getFileId(), user.getId(), shareDTO.getShareDays(), shareDTO.getPassword());
        return SaResult.data(shareVO);
    }

    @GetMapping("/share/type/{token}")
    public SaResult getShareType(@PathVariable String token) {
        ShareEntity shareEntity = shareService.getShareByToken(token);
        if (shareEntity == null) {
            return SaResult.error("分享不存在或已过期");
        }
        FileEntity file = fileService.getFile(shareEntity.getFileId());
        if (!file.getStatus().equals(0)) {
            return SaResult.error("文件不存在或已被删除");
        }
        return SaResult.ok().set("type", shareEntity.getType());
    }


    @GetMapping("/share/{token}")
    public SaResult getShareFile(@PathVariable String token,
                                 @RequestParam(required = false) String password) {
        ShareEntity shareEntity = shareService.getShareByToken(token);
        if (shareEntity == null) {
            return SaResult.error("分享不存在或已过期");
        }
        if (shareEntity.getPassword() != null) {
            if (password == null) {
                return SaResult.error("请输入密码");
            }
            if (!password.equals(shareEntity.getPassword())) {
                return SaResult.error("密码错误");
            }
        }
        FileEntity file = fileService.getFile(shareEntity.getFileId());
        if (!file.getStatus().equals(0)) {
            return SaResult.error("文件不存在或已被删除");
        }
        return SaResult.data(file);
    }

    @SaCheckLogin
    @SaCheckRole("user")
    @GetMapping("/share/list")
    public SaResult getShareList() {
        String username = (String) StpUtil.getLoginId();
        UserEntity user = userService.getUserByUsername(username);
        List<ShareListVO> shares = shareService.getShareList(user.getId());
        return SaResult.data(shares);
    }

    @SaCheckLogin
    @PostMapping("/share/undo/{shareId}")
    public SaResult undoShare(@PathVariable Long shareId) {
        String username = (String) StpUtil.getLoginId();
        UserEntity user = userService.getUserByUsername(username);
        ShareEntity shareEntity = shareService.getShare(shareId);
        if (!shareEntity.getUserId().equals(user.getId())) {
            return SaResult.error("分享不是你的，撤销失败");
        }
        if (shareEntity.getStatus().equals(1)) {
            return SaResult.error("分享已过期,撤销失败");
        }
        if (shareEntity.getStatus().equals(2)) {
            return SaResult.error("分享已撤销，请不要重复操作");
        }
        shareEntity.setStatus(2);
        shareService.updateShare(shareEntity);
        return SaResult.ok("撤销分享成功'");
    }

    @SaCheckLogin
    @SaCheckRole("user")
    @PostMapping("/share/save")
    public SaResult saveShareFile(@Valid @RequestBody SaveShareDTO saveShareDTO) {
        String username = (String) StpUtil.getLoginId();
        UserEntity user = userService.getUserByUsername(username);
        if (saveShareDTO.getDirId() != null) {
            DirEntity dir = dirService.getDir(saveShareDTO.getDirId());
            if (!dir.getUserId().equals(user.getId())) {
                return SaResult.error("目录不属于你，转存失败");
            }
        }
        ShareEntity share = shareService.getShareByToken(saveShareDTO.getToken());
        if (share == null) {
            return SaResult.error("分享不存在或已过期");
        }
        fileService.saveShare(share.getId(), saveShareDTO.getDirId(), user.getId());
        return SaResult.ok("转存成功");
    }

    @SaCheckLogin
    @SaCheckRole("user")
    @PostMapping("/move")
    public SaResult moveFile(@Valid @RequestBody FileMoveDTO fileMoveDTO) {
        String username = (String) StpUtil.getLoginId();
        UserEntity user = userService.getUserByUsername(username);
        FileEntity file = fileService.getFile(fileMoveDTO.getFileId());
        if (!file.getUserId().equals(user.getId())) {
            return SaResult.error("文件不属于你，移动失败");
        }
        if (fileMoveDTO.getDirId() != null) {
            DirEntity dir = dirService.getDir(fileMoveDTO.getDirId());
            if (!dir.getUserId().equals(user.getId())) {
                return SaResult.error("目录不属于你，转存失败");
            }
        }
        fileService.moveFile(file.getId(), fileMoveDTO.getDirId());
        return SaResult.ok("移动文件成功");
    }


}
