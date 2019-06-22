package com.dxj.tool.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @AUTHOR: sinkiang
 * @DATE: 2019-06-21 21:22
 */
@Data
@Entity
@Table(name = "ali_content")
public class AliContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 文件地址
     */
    private String url;
}
