package com.dxj.module.system.domain.mapstruct;

import com.dxj.module.system.domain.dto.DeptSmallDTO;
import com.dxj.module.system.domain.dto.JobSmallDTO;
import com.dxj.module.system.domain.dto.RoleSmallDTO;
import com.dxj.module.system.domain.dto.UserDTO;
import com.dxj.module.system.domain.entity.Dept;
import com.dxj.module.system.domain.entity.Job;
import com.dxj.module.system.domain.entity.Role;
import com.dxj.module.system.domain.entity.User;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-07-16T19:44:22+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_202 (Oracle Corporation)"
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

        user.setCreateBy( dto.getCreateBy() );
        user.setUpdatedBy( dto.getUpdatedBy() );
        user.setCreateTime( dto.getCreateTime() );
        user.setUpdateTime( dto.getUpdateTime() );
        user.setId( dto.getId() );
        user.setRoles( roleSmallDTOSetToRoleSet( dto.getRoles() ) );
        user.setJobs( jobSmallDTOSetToJobSet( dto.getJobs() ) );
        user.setDept( deptSmallDTOToDept( dto.getDept() ) );
        user.setUsername( dto.getUsername() );
        user.setNickName( dto.getNickName() );
        user.setEmail( dto.getEmail() );
        user.setPhone( dto.getPhone() );
        user.setGender( dto.getGender() );
        user.setAvatarName( dto.getAvatarName() );
        user.setAvatarPath( dto.getAvatarPath() );
        user.setPassword( dto.getPassword() );
        user.setEnabled( dto.getEnabled() );
        user.setIsAdmin( dto.getIsAdmin() );
        user.setPwdResetTime( dto.getPwdResetTime() );

        return user;
    }

    @Override
    public UserDTO toDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setCreateBy( entity.getCreateBy() );
        userDTO.setUpdatedBy( entity.getUpdatedBy() );
        userDTO.setCreateTime( entity.getCreateTime() );
        userDTO.setUpdateTime( entity.getUpdateTime() );
        userDTO.setId( entity.getId() );
        userDTO.setRoles( roleSetToRoleSmallDTOSet( entity.getRoles() ) );
        userDTO.setJobs( jobSetToJobSmallDTOSet( entity.getJobs() ) );
        userDTO.setDept( deptToDeptSmallDTO( entity.getDept() ) );
        userDTO.setUsername( entity.getUsername() );
        userDTO.setNickName( entity.getNickName() );
        userDTO.setEmail( entity.getEmail() );
        userDTO.setPhone( entity.getPhone() );
        userDTO.setGender( entity.getGender() );
        userDTO.setAvatarName( entity.getAvatarName() );
        userDTO.setAvatarPath( entity.getAvatarPath() );
        userDTO.setPassword( entity.getPassword() );
        userDTO.setEnabled( entity.getEnabled() );
        userDTO.setIsAdmin( entity.getIsAdmin() );
        userDTO.setPwdResetTime( entity.getPwdResetTime() );

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

    protected Role roleSmallDTOToRole(RoleSmallDTO roleSmallDTO) {
        if ( roleSmallDTO == null ) {
            return null;
        }

        Role role = new Role();

        role.setId( roleSmallDTO.getId() );
        role.setName( roleSmallDTO.getName() );
        role.setDataScope( roleSmallDTO.getDataScope() );
        role.setLevel( roleSmallDTO.getLevel() );

        return role;
    }

    protected Set<Role> roleSmallDTOSetToRoleSet(Set<RoleSmallDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Role> set1 = new HashSet<Role>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( RoleSmallDTO roleSmallDTO : set ) {
            set1.add( roleSmallDTOToRole( roleSmallDTO ) );
        }

        return set1;
    }

    protected Job jobSmallDTOToJob(JobSmallDTO jobSmallDTO) {
        if ( jobSmallDTO == null ) {
            return null;
        }

        Job job = new Job();

        job.setId( jobSmallDTO.getId() );
        job.setName( jobSmallDTO.getName() );

        return job;
    }

    protected Set<Job> jobSmallDTOSetToJobSet(Set<JobSmallDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Job> set1 = new HashSet<Job>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( JobSmallDTO jobSmallDTO : set ) {
            set1.add( jobSmallDTOToJob( jobSmallDTO ) );
        }

        return set1;
    }

    protected Dept deptSmallDTOToDept(DeptSmallDTO deptSmallDTO) {
        if ( deptSmallDTO == null ) {
            return null;
        }

        Dept dept = new Dept();

        dept.setId( deptSmallDTO.getId() );
        dept.setName( deptSmallDTO.getName() );

        return dept;
    }

    protected RoleSmallDTO roleToRoleSmallDTO(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleSmallDTO roleSmallDTO = new RoleSmallDTO();

        roleSmallDTO.setId( role.getId() );
        roleSmallDTO.setName( role.getName() );
        roleSmallDTO.setLevel( role.getLevel() );
        roleSmallDTO.setDataScope( role.getDataScope() );

        return roleSmallDTO;
    }

    protected Set<RoleSmallDTO> roleSetToRoleSmallDTOSet(Set<Role> set) {
        if ( set == null ) {
            return null;
        }

        Set<RoleSmallDTO> set1 = new HashSet<RoleSmallDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Role role : set ) {
            set1.add( roleToRoleSmallDTO( role ) );
        }

        return set1;
    }

    protected JobSmallDTO jobToJobSmallDTO(Job job) {
        if ( job == null ) {
            return null;
        }

        JobSmallDTO jobSmallDTO = new JobSmallDTO();

        jobSmallDTO.setId( job.getId() );
        jobSmallDTO.setName( job.getName() );

        return jobSmallDTO;
    }

    protected Set<JobSmallDTO> jobSetToJobSmallDTOSet(Set<Job> set) {
        if ( set == null ) {
            return null;
        }

        Set<JobSmallDTO> set1 = new HashSet<JobSmallDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Job job : set ) {
            set1.add( jobToJobSmallDTO( job ) );
        }

        return set1;
    }

    protected DeptSmallDTO deptToDeptSmallDTO(Dept dept) {
        if ( dept == null ) {
            return null;
        }

        DeptSmallDTO deptSmallDTO = new DeptSmallDTO();

        deptSmallDTO.setId( dept.getId() );
        deptSmallDTO.setName( dept.getName() );

        return deptSmallDTO;
    }
}
