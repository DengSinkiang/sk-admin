package com.dxj.module.security.service;

import com.dxj.exception.EntityNotFoundException;
import com.dxj.exception.SkException;
import com.dxj.module.security.config.bean.LoginProperties;
import com.dxj.module.security.domain.dto.JwtUserDTO;
import com.dxj.module.system.service.DataService;
import com.dxj.module.system.service.RoleService;
import com.dxj.module.system.service.UserService;
import com.dxj.module.system.domain.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author Sinkiang
 * @date 2018-11-22
 */
@RequiredArgsConstructor
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;
    private final RoleService roleService;
    private final DataService dataService;
    private final LoginProperties loginProperties;
    public void setEnableCache(boolean enableCache) {
        this.loginProperties.setCacheEnable(enableCache);
    }

    /**
     * 用户信息缓存
     *
     * @see
     */
    public static Map<String, JwtUserDTO> userDtoCache = new ConcurrentHashMap<>();

    @Override
    public JwtUserDTO loadUserByUsername(String username) {
        boolean searchDb = true;
        JwtUserDTO jwtUserDto = null;
        if (loginProperties.isCacheEnable() && userDtoCache.containsKey(username)) {
            jwtUserDto = userDtoCache.get(username);
            searchDb = false;
        }
        if (searchDb) {
            UserDTO user;
            try {
                user = userService.findByName(username);
            } catch (EntityNotFoundException e) {
                // SpringSecurity会自动转换UsernameNotFoundException为BadCredentialsException
                throw new UsernameNotFoundException("", e);
            }
            if (user == null) {
                throw new UsernameNotFoundException("");
            } else {
                if (!user.getEnabled()) {
                    throw new SkException("账号未激活");
                }
                jwtUserDto = new JwtUserDTO(
                        user,
                        dataService.getDeptIds(user),
                        roleService.mapToGrantedAuthorities(user)
                );
                userDtoCache.put(username, jwtUserDto);
            }
        }
        return jwtUserDto;
    }
}
