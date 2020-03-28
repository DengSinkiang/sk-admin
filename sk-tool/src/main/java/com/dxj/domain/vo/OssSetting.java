package com.dxj.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Sinkiang
 */
@Data
public class OssSetting implements Serializable {

    @ApiModelProperty(value = "服务商")
    private String serviceName;

    @ApiModelProperty(value = "ak")
    private String accessKey;

    @ApiModelProperty(value = "sk")
    private String secretKey;

    @ApiModelProperty(value = "endpoint域名")
    private String endpoint;

    @ApiModelProperty(value = "bucket空间")
    private String bucket;

    @ApiModelProperty(value = "http")
    private String http;

    @ApiModelProperty(value = "zone存储区域")
    private int zone;

    @ApiModelProperty(value = "bucket存储区域")
    private String bucketRegion;

    @ApiModelProperty(value = "本地存储路径")
    private String filePath;

    public boolean getChanged() {
        return changed;
    }


    @ApiModelProperty(value = "是否改变secretKey")
    private boolean changed;
}
