package com.douyin.utils;

/**
 * Author:WJ
 * Date:2023/2/21 10:53
 * Description:<OSS云存储>
 */

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;


public class AliOSSUtils {
    // Endpoint外网访问域名，以上海为例。
    private static String endpoint = "oss-cn-beijing.aliyuncs.com";
    // accessKeyId 和 accessKeySecret 是先前创建用户生成的
    private static  String accessKeyId = "LTAI5t6vMHQHiMbs9DXMDA84";
    private static String accessKeySecret = "ebZoU1Q9JsD7i1umX3jbGDJJnFJR5i";
    private static String bucketName="doushengnanjing";
    public static void uploadFile(String fileName,String saveFileName) throws FileNotFoundException {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 上传文件流。
        InputStream inputStream = new FileInputStream(fileName);
        ossClient.putObject(bucketName, saveFileName, inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 用视频流上传
     * @param file
     * @param saveFileName
     * @throws IOException
     */
    public static void uploadFileStream(MultipartFile file, String saveFileName) throws IOException {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 上传文件流。
        //将接收到的MultipartFile 转成 流
        byte [] byteArr=file.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArr);
        ossClient.putObject(bucketName, saveFileName, inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
    }
    // public static void main(String[] args) throws FileNotFoundException {
    //     uploadFile("C:/Users/wangjun/Desktop/bear.mp4","bear.mp4");
    // }

}