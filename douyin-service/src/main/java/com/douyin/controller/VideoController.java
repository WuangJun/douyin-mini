package com.douyin.controller;

import com.douyin.aspect.ClientLogin;
import com.douyin.common.constants.PathConstant;
import com.douyin.common.dto.UserLoginDTO;
import com.douyin.common.vo.BaseResponseVO;
import com.douyin.common.vo.FeedResponseVO;
import com.douyin.common.vo.VideoVO;
import com.douyin.entity.Video;
import com.douyin.exception.CommonException;
import com.douyin.manager.VideoManager;
import com.douyin.service.UserService;
import com.douyin.service.VideoService;
import com.douyin.utils.AliOSSUtils;
import com.douyin.utils.DateUtils;
import com.douyin.utils.TokenUtils;
import com.douyin.utils.VideoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;


/**
 * Author:WJ
 * Date:2023/2/18 13:07
 * Description:<视频相关接口>
 */
@Slf4j
@RestController
public class VideoController {

    @Autowired
    private VideoManager videoManager;

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserService userService;

    /**
     * 获取视频流
     *
     * @param latestTime
     * @param token
     * @return
     * @throws ParseException
     */
    @GetMapping("/feed")
    public FeedResponseVO feed(@RequestParam(value = "latest_time", required = false) Long latestTime,
                               @RequestParam(value = "token", required = false) String token) throws ParseException {
        Long userId = null;
        if (token != null) {
            if (!TokenUtils.verify(token)) {
                throw new CommonException("用户登录信息不正确");
            }
            userId = TokenUtils.getLoginUserDTO(token).getId();
        }
        List<VideoVO> videoVOList = videoManager.getVideoVOList(latestTime, userId);
        log.info("获取视频流成功！");
        Long nextTime = DateUtils.curTimeMillis();
        if (!videoVOList.isEmpty()) {
            VideoVO lastVideoVO = videoVOList.get(videoVOList.size() - 1);
            Date date = videoService.getById(lastVideoVO.getId()).getCreatedTime();
            log.info("最后一个视频的时间为：{}", date);
            nextTime = DateUtils.getDate2Long(date);
        }
        return FeedResponseVO.success(videoVOList, nextTime);
    }

    /**
     * 获取用户视频列表
     *
     * @param userId
     * @param token
     * @param userLoginDTO
     * @return
     */
    @ClientLogin
    @GetMapping("/publish/list/")
    public FeedResponseVO getPublishList(@RequestParam("user_id") Long userId, @RequestParam("token") String token, UserLoginDTO userLoginDTO) {
        List<VideoVO> videoVOList = videoManager.getVideoVOByUserId(userId, userLoginDTO.getId());
        log.info("获取用户视频成功");
        return FeedResponseVO.success(videoVOList, null);
    }

    /**
     * 发表视频
     *
     * @param video
     * @param token
     * @param title
     * @param userLoginDTO
     * @return
     */
    @ClientLogin
    @PostMapping("/publish/action/")
    public BaseResponseVO uploadVideo(@RequestParam("data") MultipartFile video, @RequestParam("token") String token,
                                      @RequestParam("title") String title, UserLoginDTO userLoginDTO) {

        // 保存视频到本地
        // 设置上传至项目文件夹下的uploadFile文件夹中，没有文件夹则创建
        File dir = new File(PathConstant.videoSavePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File videoFile = new File(dir, video.getOriginalFilename());


        log.info("视频上传开始，文件名:{}", video.getOriginalFilename());

        try {
            video.transferTo(videoFile);
        } catch (IOException e) {
            throw new CommonException("上传视频失败");
        }

        // 保存视频封面到本地
        // 创建封面名字

        String coverName = video.getOriginalFilename().substring(0, video.getOriginalFilename().lastIndexOf("."))
                + "Cover.jpg";
        String coverPath = PathConstant.coverSavePath + '/' + coverName;
        log.info("视频封面上传开始，文件名:{}", coverName);
        try {
            VideoUtils.fetchPic(videoFile, coverPath);
        } catch (Exception e) {
            throw new CommonException("上传视频封面失败");
        }

        // 保存视频到OSS
        log.info("视频上传到OSS，文件名:{}", video.getOriginalFilename());

        String videoOSSPath = "video/" + video.getOriginalFilename();// 保存在OSS中的路径
        String videoOSSUrl = PathConstant.videoOSSPath + video.getOriginalFilename();// 视频访问路径
        try {
            AliOSSUtils.uploadFile(videoFile.getAbsolutePath(), videoOSSPath);
        } catch (IOException e) {
            throw new CommonException("上传视频到OSS失败");
        }
        // 保存视频封面到OSS
        log.info("视频封面上传到OSS，文件名:{}", video.getOriginalFilename());

        String coverOSSPath = "cover/" + coverName;// 保存在OSS中的路径
        String coverOSSUrl = PathConstant.coverOSSPath + coverName;// 视频封面访问路径
        try {
            AliOSSUtils.uploadFile(coverPath, coverOSSPath);
        } catch (IOException e) {
            throw new CommonException("上传视频封面到OSS失败");
        }

        // 保存视频实体到数据库
        Video videoEntity = new Video();
        videoEntity.setAuthorId(userLoginDTO.getId());
        videoEntity.setCoverUrl(coverOSSUrl);
        videoEntity.setPlayUrl(videoOSSUrl);
        videoEntity.setTitle(title);
        log.info("保存视频实体到数据库，文件名:{}", title);
        videoService.save(videoEntity);

        userService.updateWorkCountById(1,userLoginDTO.getId());
        return BaseResponseVO.success();

    }
}
