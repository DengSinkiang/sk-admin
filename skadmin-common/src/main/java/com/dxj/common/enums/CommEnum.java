package com.dxj.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author: dxj
 * @Date: 2019-04-30 15:54
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum CommEnum {

    // Entity
    USER_ENTITY("user"),
    DEPT_ENTITY("dept"),
    DICT_ENTITY("dict"),
    JOB_ENTITY("job"),
    MENU_ENTITY("menu"),
    DICT_DETAIL_ENTITY("dictDetail"),
    ROLE_ENTITY("role"),
    PERMISSION_ENTITY("permission"),
    QUARTZ_ENTITY("quartzJob"),
    //
    RESET_MAIL("重置邮箱"),
    SM_MS_URL("https://sm.ms/api/upload")
    ;

    private String entityName;

}
