package com.dxj.module.security.domain.entity;

import com.dxj.module.security.config.SecurityProperties;
import com.dxj.module.security.domain.dto.OnlineUserDTO;
import com.dxj.module.security.service.OnlineUserService;
import com.dxj.util.SpringContextHolder;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author /
 */
@Slf4j
public class TokenFilter extends GenericFilterBean {

    private final TokenProvider tokenProvider;

    TokenFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String token = resolveToken(httpServletRequest);
        String requestRri = httpServletRequest.getRequestURI();
        // 验证 token 是否存在
        OnlineUserDTO onlineUserDTO = null;
        try {
            SecurityProperties properties = SpringContextHolder.getBean(SecurityProperties.class);
            OnlineUserService onlineUserService = SpringContextHolder.getBean(OnlineUserService.class);
            onlineUserDTO = onlineUserService.getOne(properties.getOnlineKey() + token);
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage());
        }
        if (onlineUserDTO != null && StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("set Authentication to security context for '{}', uri: {}", authentication.getName(), requestRri);
        } else {
            log.debug("no valid JWT token found, uri: {}", requestRri);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String resolveToken(HttpServletRequest request) {
        SecurityProperties properties = SpringContextHolder.getBean(SecurityProperties.class);
        String bearerToken = request.getHeader(properties.getHeader());
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(properties.getTokenStartWith())) {
            // 去掉令牌前缀
            return bearerToken.replace(properties.getTokenStartWith(), "");
        }
        return null;
    }
}
