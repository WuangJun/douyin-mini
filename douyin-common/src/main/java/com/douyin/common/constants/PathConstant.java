package com.douyin.common.constants;

/**
 * 视频保存及访问的地址
 */
public interface PathConstant {

    // 视频保存至本地的路径
    String videoSavePath = "D:\\douyin\\douyin-mini\\douyin-service\\src\\main\\resources\\static\\video";
    // 视频封面保存至本地的路径
    String coverSavePath = "D:\\douyin\\douyin-mini\\douyin-service\\src\\main\\resources\\static\\cover";

    // String videoBathPath = "http://192.168.0.103:8080/douyin/video/";
    //
    // String coverBathPath = "http://192.168.0.103:8080/douyin/cover/";

    // 视频保存至OSS的路径
    String videoOSSPath = "https://doushengnanjing.oss-cn-beijing.aliyuncs.com/video/";
    // 视频封面保存至OSS的路径
    String coverOSSPath = "https://doushengnanjing.oss-cn-beijing.aliyuncs.com/cover/";


}
