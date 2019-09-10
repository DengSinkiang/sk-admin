package com.dxj.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Sinkiang
 */
@Data
public class City implements Serializable {

    String country;

    String province;

    String city;
}
