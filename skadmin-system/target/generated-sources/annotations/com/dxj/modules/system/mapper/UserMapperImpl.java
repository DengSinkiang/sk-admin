package com.dxj.modules.system.mapper;

import com.dxj.modules.system.domain.Role;
import com.dxj.modules.system.domain.User;
import com.dxj.modules.system.dto.RoleDTO;
import com.dxj.modules.system.dto.UserDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-05-14T10:00:51+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_192 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private JobMapper jobMapper;

    @Override
    public User toEntity(UserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setId( dto.getId() );
        user.setUsername( dto.getUsername() );
        user.setAvatar( dto.getAvatar() );
        user.setEmail( dto.getEmail() );
        user.setPhone( dto.getPhone() );
        user.setEnabled( dto.getEnabled() );
        user.setPassword( dto.getPassword() );
        user.setCreateTime( dto.getCreateTime() );
        user.setLastPasswordResetTime( dto.getLastPasswordResetTime() );
        user.setRoles( roleDTOSetToRoleSet( dto.getRoles() ) );
        user.setJob( jobMapper.toEntity( dto.getJob() ) );
        user.setDept( deptMapper.toEntity( dto.getDept() ) );

        return user;
    }

    @Override
    public UserDTO toDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( entity.getId() );
        userDTO.setUsername( entity.getUsername() );
        userDTO.setAvatar( entity.getAvatar() );
        userDTO.setEmail( entity.getEmail() );
        userDTO.setPhone( entity.getPhone() );
        userDTO.setEnabled( entity.getEnabled() );
        userDTO.setPassword( entity.getPassword() );
        userDTO.setCreateTime( entity.getCreateTime() );
        userDTO.setLastPasswordResetTime( entity.getLastPasswordResetTime() );
        userDTO.setRoles( roleSetToRoleDTOSet( entity.getRoles() ) );
        userDTO.setJob( jobMapper.toDto( entity.getJob() ) );
        userDTO.setDept( deptMapper.toDto( entity.getDept() ) );

        return userDTO;
    }

    @Override
    public List<User> toEntity(List<UserDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( dtoList.size() );
        for ( UserDTO userDTO : dtoList ) {
            list.add( toEntity( userDTO ) );
        }

        return list;
    }

    @Override
    public List<UserDTO> toDto(List<User> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>( entityList.size() );
        for ( User user : entityList ) {
            list.add( toDto( user ) );
        }

        return list;
    }

    protected Set<Role> roleDTOSetToRoleSet(Set<RoleDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Role> set1 = new HashSet<Role>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( RoleDTO roleDTO : set ) {
            set1.add( roleMapper.toEntity( roleDTO ) );
        }

        return set1;
    }

    protected Set<RoleDTO> roleSetToRoleDTOSet(Set<Role> set) {
        if ( set == null ) {
            return null;
        }

        Set<RoleDTO> set1 = new HashSet<RoleDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Role role : set ) {
            set1.add( roleMapper.toDto( role ) );
        }

        return set1;
    }
}
