package com.dxj.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * @Author: dxj
 * @Date: 2019-05-10 14:24
 */
@Data
@Entity
@Table(name = "sms_config")
public class SmsConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "access_key")
    private String accessKeyId;

    @NotBlank
    @Column(name = "access_secret")
    private String accessSecret;

    @NotBlank
    @Column(name = "sign_name")
    private String signName;

    @NotBlank
    @Column(name = "template_code")
    private String templateCode;


}
