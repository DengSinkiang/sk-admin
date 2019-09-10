package com.dxj.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Sinkiang
 */
@Data
public class IpLocate implements Serializable {

    private String retCode;

    private City result;
}

