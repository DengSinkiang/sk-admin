package com.dxj.util;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dxj.exception.SkException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * 获取当前登录的用户
 *
 * @author Sinkiang
 * @date 2019-01-17
 */
@Slf4j
public class SecurityUtils {

    /**
     * 获取当前登录的用户
     *
     * @return UserDetails
     */
    public static UserDetails getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new SkException(HttpStatus.UNAUTHORIZED, "当前登录状态过期");
        }
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            UserDetailsService userDetailsService = SpringContextHolder.getBean(UserDetailsService.class);
            return userDetailsService.loadUserByUsername(userDetails.getUsername());
        }
        throw new SkException(HttpStatus.UNAUTHORIZED, "找不到当前登录的信息");
    }

    /**
     * 获取系统用户名称
     *
     * @return 系统用户名称
     */
    public static String getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new SkException(HttpStatus.UNAUTHORIZED, "当前登录状态过期");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    /**
     * 获取系统用户ID
     *
     * @return 系统用户ID
     */
    public static Long getCurrentUserId() {
        UserDetails userDetails = getCurrentUser();
        return new JSONObject(new JSONObject(userDetails).get("user")).get("id", Long.class);
    }

    /**
     * 获取当前用户的数据权限
     *
     * @return /
     */
    public static List<Long> getCurrentUserDataScope() {
        UserDetails userDetails = getCurrentUser();
        JSONArray array = JSONUtil.parseArray(new JSONObject(userDetails).get("dataScopes"));
        return JSONUtil.toList(array, Long.class);
    }

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }
}
