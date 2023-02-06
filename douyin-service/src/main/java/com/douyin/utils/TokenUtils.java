package com.douyin.utils;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.douyin.common.dto.UserLoginDTO;
import org.springframework.util.StringUtils;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author:WJ
 * Date:2023/2/4 16:05
 * Description:<>
 */
public class TokenUtils {

    // 设置过期时间
    private static final long EXPIRE_DATE = 30 * 60 * 100000;
    // token秘钥
    private static final String TOKEN_SECRET = "ZCfasfhuaUUHufguGuwu2020BQWE";

    public static String token(UserLoginDTO userLoginDTO) {

        String token = "";
        try {
            // 过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_DATE);
            // 秘钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            // 设置头部信息
            Map<String, Object> header = new HashMap<>();
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            // 携带username，password信息，生成签名
            token = JWT.create()
                    .withHeader(header)
                    .withClaim("loginUser", JSON.toJSONString(userLoginDTO)).withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return token;
    }

    public static boolean verify(String token) {
        /**
         * @desc 验证token，通过返回true
         * @params [token]需要校验的串
         **/
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);

            // System.out.println(jwt.getClaims().get("username").asString());
            // System.out.println(jwt.getClaims().get("password").asString());

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static UserLoginDTO getLoginUserDTO(String token) {
        if (StringUtils.isEmpty(token)) return null;
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        return JSON.parseObject(jwt.getClaims().get("loginUser").asString(), UserLoginDTO.class);
    }
}
