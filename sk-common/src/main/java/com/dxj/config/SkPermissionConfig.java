package com.dxj.config;

import com.dxj.util.SecurityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sinkiang
 */
@Service(value = "sk")
public class SkPermissionConfig {

    public boolean check(String... permissions) {
        // 获取当前用户的所有权限
        List<String> skPermissions = SecurityUtils.getCurrentUser().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        // 判断当前用户的所有权限是否包含接口上定义的权限
        return skPermissions.contains("admin") || Arrays.stream(permissions).anyMatch(skPermissions::contains);
    }
}
