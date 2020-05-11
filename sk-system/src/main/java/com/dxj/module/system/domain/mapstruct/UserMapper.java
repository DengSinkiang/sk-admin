package com.dxj.module.system.domain.mapstruct;

import com.dxj.base.BaseMapper;
import com.dxj.module.system.domain.entity.User;
import com.dxj.module.system.domain.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * @author Sinkiang
 */
@Mapper(componentModel = "spring",uses = {RoleMapper.class, DeptMapper.class, JobMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends BaseMapper<UserDTO, User> {

    /**
     * 转换
     * @param user 原始数据
     * @return /
     */
    @Override
    @Mapping(source = "user.userAvatar.realName",target = "avatar")
    UserDTO toDto(User user);
}
