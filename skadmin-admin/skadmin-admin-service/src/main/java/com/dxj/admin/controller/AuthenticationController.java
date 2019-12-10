package com.dxj.admin.controller;

import com.dxj.admin.config.JwtTokenUtils;
import com.dxj.admin.entity.AuthenticationInfo;
import com.dxj.admin.entity.AuthorizationUser;
import com.dxj.admin.entity.JwtUser;
import com.dxj.common.util.SecurityContextHolder;
import com.dxj.log.annotation.LoginLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author dxj
 * @date 2019-03-23
 * 授权、根据token获取用户详细信息
 */
@Slf4j
@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Value("${jwt.header}")
    private String tokenHeader;

    private final JwtTokenUtils jwtTokenUtils;

    private final UserDetailsService userDetailsService;

    @Autowired
    public AuthenticationController(JwtTokenUtils jwtTokenUtils, @Qualifier("jwtUserDetailsService") UserDetailsService userDetailsService) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.userDetailsService = userDetailsService;
    }

    /**
     * 登录授权
     *
     * @param authorizationUser
     * @return
     */

    @LoginLog("登录")
    @PostMapping(value = "${jwt.auth.path}")
    public ResponseEntity<AuthenticationInfo> login(@Validated @RequestBody AuthorizationUser authorizationUser) {

        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(authorizationUser.getUsername());

        //验证密码
        if (!jwtUser.getPassword().equals(authorizationUser.getPassword())) {
            throw new AccountExpiredException("密码错误");
        }

        //账号是否锁定
        if (!jwtUser.isEnabled()) {
            throw new AccountExpiredException("账号已停用，请联系管理员");
        }

        // 生成令牌
        final String token = jwtTokenUtils.generateToken(jwtUser);

        // 返回 token
        return ResponseEntity.ok(new AuthenticationInfo(token, jwtUser));
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping(value = "${jwt.auth.info}")
    public ResponseEntity<JwtUser> getUserInfo() {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityContextHolder.getUsername());
        return ResponseEntity.ok(jwtUser);
    }

}
