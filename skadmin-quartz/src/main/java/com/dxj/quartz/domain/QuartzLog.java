package com.dxj.quartz.domain;

import com.dxj.common.annotation.Query;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author dxj
 * @date 2019-01-07
 */
@Entity
@Data
@Table(name = "quartz_log")
public class QuartzLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 任务名称
    @Column(name = "job_name")
    @Query(type = Query.Type.LIKE)
    private String jobName;

    // Bean名称
    @Column(name = "bean_name")
    private String beanName;

    // 方法名称
    @Column(name = "method_name")
    private String methodName;

    // 参数
    @Column(name = "params")
    private String params;

    // cron表达式
    @Column(name = "cron_expression")
    private String cronExpression;

    // 状态
    @Column(name = "is_success")
    @Query
    private Boolean isSuccess;

    // 异常详细
    @Column(name = "exception_detail", columnDefinition = "text")
    private String exceptionDetail;

    // 耗时（毫秒）
    private Long time;

    // 创建日期
    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;
}
