package com.dxj.admin.module.domain;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

/**
* @author sinkiang
* @date 2019-09-01
*/
@Entity
@Data
@Table(name="tb_user")
public class TbUser implements Serializable {

    @Column(name = "name")
    private String name;

    @Column(name = "sex")
    private String sex;

    @Id
    @Column(name = "id")
    private Integer id;
}