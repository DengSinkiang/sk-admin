package com.dxj.util;

import cn.hutool.json.JSONObject;
import com.dxj.exception.SkException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 获取当前登录的用户
 *
 * @author Sinkiang
 * @date 2019-01-17
 */
public class SecurityUtils {

    public static UserDetails getUserDetails() {
        UserDetails userDetails;
        try {
            userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new SkException(HttpStatus.UNAUTHORIZED, "登录状态过期");
        }
        return userDetails;
    }

    /**
     * 获取系统用户名称
     *
     * @return 系统用户名称
     */
    public static String getUsername() {
        Object obj = getUserDetails();
        return new JSONObject(obj).get("username", String.class);
    }

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }
}
