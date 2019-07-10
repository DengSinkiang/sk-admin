package com.dxj.log.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author: dxj
 * @Date: 2019-05-08 12:44
 */
@Entity
@Data
@Table(name = "login_log")
@NoArgsConstructor
public class LoginLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 操作用户
    private String username;

    // 日志类型
    @Column(name = "log_type")
    private String logType;

    // 请求ip
    @Column(name = "request_ip")
    private String requestIp;

    //请求耗时
    private Long time;

    // 创建日期
    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;

    private String operation;

    @Column(name = "user_agent")
    private String userAgent;


    public LoginLog(String logType, Long time) {
        this.logType = logType;
        this.time = time;
    }
}
