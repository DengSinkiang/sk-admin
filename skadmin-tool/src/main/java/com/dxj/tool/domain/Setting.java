package com.dxj.tool.domain;

import com.dxj.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Sinkiang
 */
@Data
@Entity
@Table(name = "t_setting")
@ApiModel(value = "配置")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Setting extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "配置值value")
    private String value;

    public Setting(String id) {

        super.setId(id);
    }
}
