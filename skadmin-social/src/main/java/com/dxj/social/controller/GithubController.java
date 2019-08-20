package com.dxj.social.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.dxj.admin.config.JwtTokenUtils;
import com.dxj.common.constant.CommonConstant;
import com.dxj.social.domain.Github;
import com.dxj.social.service.GithubService;
import com.dxj.social.vo.GithubUserInfo;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @AUTHOR: sinkiang
 * @DATE: 2019-08-02 18:53
 */
@Slf4j
@Api(description = "Github登录接口")
@RequestMapping("/xboot/social/github")
@RestController
public class GithubController {

    @Value("${xboot.social.github.clientId}")
    private String clientId;

    @Value("${xboot.social.github.clientSecret}")
    private String clientSecret;

    @Value("${xboot.social.github.callbackUrl}")
    private String callbackUrl;

    @Value("${xboot.social.callbackFeUrl}")
    private String callbackFeUrl;

    @Value("${xboot.social.callbackFeRelateUrl}")
    private String callbackFeRelateUrl;

    @Autowired
    private GithubService githubService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    /**
     * Github认证服务器地址
     */
    private static final String AUTHORIZE_URL = "https://github.com/login/oauth/authorize";

    /**
     * 申请令牌地址
     */
    private static final String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";

    /**
     * 获取用户信息地址
     */
    private static final String GET_USERINFO_URL = "https://api.github.com/user?access_token=";

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ApiOperation(value = "获取github认证链接")
    @ResponseBody
    public ResponseEntity<String> login() {

        // 生成并保存 state 忽略该参数有可能导致CSRF攻击
        String state = String.valueOf(System.currentTimeMillis());
        redisTemplate.opsForValue().set(CommonConstant.GITHUB_STATE + state, "VALID", 3L, TimeUnit.MINUTES);

        // 传递参数 response_type、client_id、state、redirect_uri
        String url = AUTHORIZE_URL + "?response_type=code&" + "client_id=" + clientId + "&state=" + state
                + "&redirect_uri=" + callbackUrl;

        return new ResponseEntity<>(url, HttpStatus.OK);
    }

    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    @ApiOperation(value = "获取accessToken")
    public String getAccessToken(@RequestParam(required = false) String code,
                                 @RequestParam(required = false) String state) throws UnsupportedEncodingException {

        if (StrUtil.isBlank(code)) {
            return "redirect:" + callbackFeUrl + "?error=" + URLEncoder.encode("您未同意授权", "utf-8");
        }
        // 验证 state
        String v = redisTemplate.opsForValue().get(CommonConstant.GITHUB_STATE + state);
        redisTemplate.delete(CommonConstant.GITHUB_STATE + state);
        if (StrUtil.isBlank(v)) {
            return "redirect:" + callbackFeUrl + "?error=" + URLEncoder.encode("授权超时或state不正确", "utf-8");
        }

        // 传递参数 grant_type、code、redirect_uri、client_id
        String params = "grant_type=authorization_code&code=" + code + "&redirect_uri=" +
                callbackUrl + "&client_id=" + clientId + "&client_secret=" + clientSecret;

        // 申请令牌 post 请求
        String result = HttpUtil.post(ACCESS_TOKEN_URL, params);

        if (!result.contains("access_token=")) {
            return "redirect:" + callbackFeUrl + "?error=" + URLEncoder.encode("获取access_token失败", "utf-8");
        }

        String accessToken = StrUtil.subBetween(result, "access_token=", "&scope");
        // 获取用户信息
        String userInfo = HttpUtil.get(GET_USERINFO_URL + accessToken);
        GithubUserInfo gu = new Gson().fromJson(userInfo, GithubUserInfo.class);
        // 存入数据库
        Github github = githubService.findByOpenId(gu.getId());
        if (github == null) {
            Github g = new Github();
            g.setOpenId(gu.getId());
            g.setUsername(gu.getLogin());
            g.setAvatar(gu.getAvatar_url());
            github = githubService.save(g);
        }

        String url;
        // 判断是否绑定账号
        if (github.getIsRelated() && StrUtil.isNotBlank(github.getRelateUsername())) {
            // 已绑定 直接登录
            Map<String, Object> claims = new HashMap<>();
            String JWT = jwtTokenUtils.doGenerateToken(claims, github.getRelateUsername());
            // 存入 redis
            String JWTKey = UUID.randomUUID().toString().replace("-", "");
            redisTemplate.opsForValue().set(JWTKey, JWT, 2L, TimeUnit.MINUTES);
            url = callbackFeUrl + "?related=1&JWTKey=" + JWTKey;
        } else {
            // 未绑定 Redis 存入 id
            String idToken = UUID.randomUUID().toString().replace("-", "");
            redisTemplate.opsForValue().set(idToken, github.getId(), 5L, TimeUnit.MINUTES);
            url = callbackFeRelateUrl + "?socialType=" + CommonConstant.SOCIAL_TYPE_GITHUB + "&id=" + idToken;
        }
        return "redirect:" + url;
    }
}
