package com.dxj.common.enums;

/**
 * @Author: dxj
 * @Date: 2019-04-30 15:54
 */
public enum EntityEnum {

    USER_ENTITY("user"),
    DEPT_ENTITY("dept"),
    DICT_ENTITY("dict"),
    JOB_ENTITY("job"),
    MENU_ENTITY("menu"),
    DICT_DETAIL_ENTITY("dictDetail"),
    ROLE_ENTITY("role"),
    PERMISSION_ENTITY("permission"),
    QUARTZ_ENTITY("quartzJob"),
    ;

    private String entityName;

    EntityEnum(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityName() {
        return this.entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
}
