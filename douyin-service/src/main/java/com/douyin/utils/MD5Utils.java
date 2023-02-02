package com.douyin.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Author:WJ
 * Date:2023/2/1 12:49
 * Description:<>
 */
public class MD5Utils {

    public static String parseStrToMd5L32(String str) {
        // 将字符串转换为32位小写MD5
        String reStr = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(str.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bytes) {
                int bt = b&0xff;
                if (bt < 16) {
                    stringBuffer.append(0);
                }
                stringBuffer.append(Integer.toHexString(bt));
            }
            reStr = stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return reStr;
    }
}
