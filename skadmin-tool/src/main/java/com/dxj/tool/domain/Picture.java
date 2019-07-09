package com.dxj.tool.domain;

import com.dxj.common.annotation.Query;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * sm.ms图床
 *
 * @author dxj
 * @date 2019-05-27
 */
@Data
@Entity
@Table(name = "picture")
public class Picture implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Query(type = Query.Type.LIKE)
    private String filename;

    private String url;

    private String size;

    private String height;

    private String width;

    /**
     * delete URl
     */
    @Column(name = "delete_url")
    private String delete;

    @Query(type = Query.Type.LIKE)
    private String username;

    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;

    @Override
    public String toString() {
        return "Picture{" +
                "filename='" + filename + '\'' +
                '}';
    }
}
