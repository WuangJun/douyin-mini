package com.douyin.aspect;

import com.alibaba.fastjson.JSON;
import com.douyin.common.dto.UserLoginDTO;
import com.douyin.common.vo.BaseResponseVO;
import com.douyin.exception.CommonException;
import com.douyin.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 登录拦截
 *
 * @author wangjun
 * @date 2023/2/1 14:19
 */
@Slf4j
@Aspect
@Component
public class ClientLoginAspect {

    @Around("@annotation(clientLogin)")
    public Object loginCheck(ProceedingJoinPoint joinPoint, ClientLogin clientLogin) throws Throwable {
        String[] argsName = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        int tokenIndex = -1;
        for (int i = 0; i < argsName.length; i++) {
            if (argsName[i] != null && argsName[i].equals("token")) {
                tokenIndex = i;
                break;
            }
        }
        // 判断是否存在参数token
        if (tokenIndex == -1) {
            throw new CommonException("token不存在");
        }
        // 查找token
        Object[] args = joinPoint.getArgs();
        String tokenParam = args[tokenIndex] == null ? null : (String) args[tokenIndex];
        log.info("tokenParam:{}", tokenParam);
        if (StringUtils.isEmpty(tokenParam)) {
            throw new CommonException("token为空");
        }
        boolean flag = TokenUtils.verify(tokenParam);
        log.info("token验证：{}", flag);
        if (!flag) {
            throw new CommonException("登录已过期，请重新登录");
        }

        // 判断登录的id和token中保存的id是否一致
        UserLoginDTO loginUser = TokenUtils.getLoginUserDTO(tokenParam);
        int userIdIndex = -1;
        for (int i = 0; i < argsName.length; i++) {
            if (argsName[i] != null && argsName[i].equals("user_id")) {
                userIdIndex = i;
                break;
            }
        }
        if (userIdIndex != -1 && args[userIdIndex] != null && !args[userIdIndex].equals(loginUser.getId())) {
            log.info("非法请求,登录用户{}和入参{}不一致", loginUser.getId(), args[userIdIndex]);
            throw new CommonException("非法请求");
        }

        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg != null && arg.getClass() == UserLoginDTO.class) {
                args[i] = loginUser;
            }
        }
        return joinPoint.proceed(args);
    }


}
