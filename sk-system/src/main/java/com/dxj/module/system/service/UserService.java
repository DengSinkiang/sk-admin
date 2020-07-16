package com.dxj.module.system.service;

import com.dxj.module.system.domain.dto.UserDTO;
import com.dxj.module.system.domain.entity.User;
import com.dxj.module.system.domain.query.UserQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Sinkiang
 * @date 2018-11-23
 */
public interface UserService {

    /**
     * 根据ID查询
     * @param id ID
     * @return /
     */
    UserDTO findById(long id);

    /**
     * 新增用户
     * @param resources /
     */
    void create(User resources);

    /**
     * 编辑用户
     * @param resources /
     */
    void update(User resources);

    /**
     * 删除用户
     * @param ids /
     */
    void delete(Set<Long> ids);

    /**
     * 根据用户名查询
     * @param userName /
     * @return /
     */
    UserDTO findByName(String userName);

    /**
     * 修改密码
     * @param username 用户名
     * @param encryptPassword 密码
     */
    void updatePass(String username, String encryptPassword);

    /**
     * 修改头像
     * @param file 文件
     * @return /
     */
    Map<String, String> updateAvatar(MultipartFile file);

    /**
     * 修改邮箱
     * @param username 用户名
     * @param email 邮箱
     */
    void updateEmail(String username, String email);

    /**
     * 查询全部
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(UserQuery criteria, Pageable pageable);

    /**
     * 查询全部不分页
     * @param criteria 条件
     * @return /
     */
    List<UserDTO> queryAll(UserQuery criteria);

    /**
     * 导出数据
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<UserDTO> queryAll, HttpServletResponse response) throws IOException;

    /**
     * 用户自助修改资料
     * @param resources /
     */
    void updateCenter(User resources);
}
