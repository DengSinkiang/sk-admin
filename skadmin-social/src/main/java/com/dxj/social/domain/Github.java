package com.dxj.social.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @AUTHOR: sinkiang
 * @DATE: 2019-08-02 19:02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "t_github")
@ApiModel(value = "Github用户")
public class Github extends BaseSocialEntity {

    private static final long serialVersionUID = 1L;
}
