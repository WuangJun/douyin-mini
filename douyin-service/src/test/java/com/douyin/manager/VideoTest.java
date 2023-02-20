package com.douyin.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.douyin.common.constants.PathConstant;
import com.douyin.entity.Video;
import com.douyin.exception.CommonException;
import com.douyin.service.VideoService;
import com.douyin.utils.DateUtils;
import com.douyin.utils.VideoUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * Author:WJ
 * Date:2023/2/19 22:19
 * Description:<>
 */
@Slf4j
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class VideoTest {

    @Autowired
    private VideoService videoService;

    @Test
    public void dateConvert() throws ParseException {
        // String date = videoService.ge(1627290531398959106L).getCreatedTime().toString();
        LambdaQueryWrapper<Video> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Video::getTitle,"hhhhhh");
        Video video = videoService.getOne(queryWrapper);
        Date createdTime = video.getCreatedTime();
        String date = DateUtils.getcst(createdTime);
        log.info("最后一个视频的时间为：{}",date);
        Long nextTime = DateUtils.getLongDate(date);
        log.info("最后一个视频的时间LONG类型为：{}",nextTime.toString());
        Date timeYMDHHmmss = DateUtils.getLong2Date(nextTime);
        log.info("最后一个视频的时间为：{}",timeYMDHHmmss);
    }

    /**
     * 上传视频测试
     * @throws IOException
     */
    @Test
    public void videoAction() throws IOException{

        Long userId = 1627290531398959106L;
        String title = "指南村222！！！";
        String fileName = "d421c54dfd03d0e198a731ee42e3d5b9.mp4";
        // 视频byte流
        File file = new File("C:/Users/wangjun/Desktop/",fileName);
        MultipartFile video = new MockMultipartFile(
                fileName, //文件名
                fileName, //originalName 相当于上传文件在客户机上的文件名
                // ContentType.APPLICATION_OCTET_STREAM.toString(), //文件类型
                "application/octet-stream",
                new FileInputStream(file) //文件流
        );

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
        String coverUrl = PathConstant.coverSavePath + '/' + coverName;
        log.info("视频封面上传开始，文件名:{}", coverName);
        try {
            VideoUtils.fetchPic(videoFile, coverUrl);
        } catch (Exception e) {
            throw new CommonException("上传视频封面失败");
        }
        // 保存视频实体到数据库
        Video videoEntity = new Video();
        videoEntity.setAuthorId(userId);
        videoEntity.setCoverUrl(PathConstant.coverBathPath + coverName);
        videoEntity.setPlayUrl(PathConstant.videoBathPath + video.getOriginalFilename());
        videoEntity.setTitle(title);
        log.info("视频封面上传开始，文件名:{}", title);
        videoService.save(videoEntity);
    }
}
