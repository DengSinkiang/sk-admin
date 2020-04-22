package com.dxj.module.security.service;

import com.dxj.exception.SkException;
import com.dxj.module.security.domain.dto.JwtUserDTO;
import com.dxj.module.system.service.RoleService;
import com.dxj.module.system.service.UserService;
import com.dxj.module.system.domain.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Sinkiang
 * @date 2018-11-22
 */
@Service("userDetailsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    private final RoleService roleService;

    public UserDetailsServiceImpl(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public JwtUserDTO loadUserByUsername(String username) {
        UserDTO user = userService.findByName(username);
        if (user == null) {
            throw new SkException("账号不存在");
        } else {
            if (!user.getEnabled()) {
                throw new SkException("账号未激活");
            }
            return new JwtUserDTO(
                    user,
                    roleService.mapToGrantedAuthorities(user)
            );
        }
    }
}

