package com.dxj.modules.system.rest;

import com.dxj.aop.log.Log;
import com.dxj.config.DataScope;
import com.dxj.domain.Picture;
import com.dxj.domain.VerificationCode;
import com.dxj.enums.EntityEnums;
import com.dxj.modules.system.domain.User;
import com.dxj.exception.BadRequestException;
import com.dxj.modules.system.service.DeptService;
import com.dxj.modules.system.service.UserService;
import com.dxj.service.PictureService;
import com.dxj.service.VerificationCodeService;
import com.dxj.utils.*;
import com.dxj.modules.system.dto.UserDTO;
import com.dxj.modules.system.query.UserQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

/**
 * @author dxj
 * @date 2018-11-23
 */
@RestController
@RequestMapping("api")
public class UserController {

    private final UserService userService;

    private final UserQueryService userQueryService;

    private final PictureService pictureService;

    private final DataScope dataScope;

    private final DeptService deptService;

    private final VerificationCodeService verificationCodeService;

    @Autowired
    public UserController(UserService userService, UserQueryService userQueryService, PictureService pictureService, DataScope dataScope, DeptService deptService, VerificationCodeService verificationCodeService) {
        this.userService = userService;
        this.userQueryService = userQueryService;
        this.pictureService = pictureService;
        this.dataScope = dataScope;
        this.deptService = deptService;
        this.verificationCodeService = verificationCodeService;
    }

    @Log("查询用户")
    @GetMapping(value = "/users")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    public ResponseEntity<Object> getUsers(UserDTO userDTO, Pageable pageable){
        Set<Long> deptSet = new HashSet<>();
        Set<Long> result = new HashSet<>();

        if (!ObjectUtils.isEmpty(userDTO.getDeptId())) {
            deptSet.add(userDTO.getDeptId());
            deptSet.addAll(dataScope.getDeptChildren(deptService.findByPid(userDTO.getDeptId())));
        }

        // 数据权限
        Set<Long> deptIds = dataScope.getDeptIds();

        // 查询条件不为空并且数据权限不为空则取交集
        if (!CollectionUtils.isEmpty(deptIds) && !CollectionUtils.isEmpty(deptSet)){

            // 取交集
            result.addAll(deptSet);
            result.retainAll(deptIds);

            // 若无交集，则代表无数据权限
            if(result.size() == 0){
                return new ResponseEntity<>(PageUtil.toPage(null,0),HttpStatus.OK);
            } else return new ResponseEntity<>(userQueryService.queryAll(userDTO,result,pageable),HttpStatus.OK);
        // 否则取并集
        } else {
            result.addAll(deptSet);
            result.addAll(deptIds);
            return new ResponseEntity<>(userQueryService.queryAll(userDTO,result,pageable),HttpStatus.OK);
        }
    }

    @Log("新增用户")
    @PostMapping(value = "/users")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_CREATE')")
    public ResponseEntity<UserDTO> create(@Validated @RequestBody User resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ EntityEnums.USER_ENTITY +" cannot already have an ID");
        }
        return new ResponseEntity<>(userService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改用户")
    @PutMapping(value = "/users")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_EDIT')")
    public ResponseEntity<Void> update(@Validated(User.Update.class) @RequestBody User resources){
        userService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除用户")
    @DeleteMapping(value = "/users/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_DELETE')")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**zx
     * 验证密码
     * @param pass
     * @return
     */
    @GetMapping(value = "/users/validPass/{pass}")
    public ResponseEntity<Map<String, Integer>> validPass(@PathVariable String pass){
        UserDetails userDetails = SecurityContextHolder.getUserDetails();
        Map<String, Integer> map = new HashMap<>();
        map.put("status", 200);
        if (!userDetails.getPassword().equals(EncryptUtils.encryptPassword(pass))) {
           map.put("status", 400);
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * 修改密码
     * @param pass
     * @return
     */
    @GetMapping(value = "/users/updatePass/{pass}")
    public ResponseEntity<Void> updatePass(@PathVariable String pass){
        UserDetails userDetails = SecurityContextHolder.getUserDetails();
        if(userDetails.getPassword().equals(EncryptUtils.encryptPassword(pass))){
            throw new BadRequestException("新密码不能与旧密码相同");
        }
        userService.updatePass(userDetails.getUsername(),EncryptUtils.encryptPassword(pass));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 修改头像
     * @param file
     * @return
     */
    @PostMapping(value = "/users/updateAvatar")
    public ResponseEntity<Void> updateAvatar(@RequestParam MultipartFile file){
        UserDetails userDetails = SecurityContextHolder.getUserDetails();
        Picture picture = pictureService.upload(file,userDetails.getUsername());
        userService.updateAvatar(userDetails.getUsername(),picture.getUrl());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 修改邮箱
     * @param user
     * @param user
     * @return
     */
    @Log("修改邮箱")
    @PostMapping(value = "/users/updateEmail/{code}")
    public ResponseEntity<Void> updateEmail(@PathVariable String code,@RequestBody User user){
        UserDetails userDetails = SecurityContextHolder.getUserDetails();
        if(!userDetails.getPassword().equals(EncryptUtils.encryptPassword(user.getPassword()))){
            throw new BadRequestException("密码错误");
        }
        VerificationCode verificationCode = new VerificationCode(code, ElAdminConstant.RESET_MAIL,"email",user.getEmail());
        verificationCodeService.validated(verificationCode);
        userService.updateEmail(userDetails.getUsername(),user.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
