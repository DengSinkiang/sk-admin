package com.dxj.social.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.MappedSuperclass;

/**
 * @AUTHOR: sinkiang
 * @DATE: 2019-08-02 19:02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public class BaseSocialEntity extends BaseEntity {

    @ApiModelProperty(value = "唯一id")
    private String openId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "是否绑定账号 默认false")
    private Boolean isRelated = false;

    @ApiModelProperty(value = "绑定用户账号")
    private String relateUsername;
}
