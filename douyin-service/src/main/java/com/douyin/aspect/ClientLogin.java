package com.douyin.aspect;

import java.lang.annotation.*;

/**
 * 登录拦截
 *
 * @author wangjun
 * @date 2023/2/1 14:19
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ClientLogin {

}
