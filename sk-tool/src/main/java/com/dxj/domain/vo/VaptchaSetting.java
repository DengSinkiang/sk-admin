package com.dxj.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Sinkiang
 */
@Data
public class VaptchaSetting implements Serializable {

    @ApiModelProperty(value = "vid")
    private String vid;

    @ApiModelProperty(value = "secretKey")
    private String secretKey;

    @ApiModelProperty(value = "场景")
    private String scene;

    public boolean getChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    @ApiModelProperty(value = "是否改变secretKey")
    private boolean changed;
}
