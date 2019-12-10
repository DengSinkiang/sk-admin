package com.dxj.generator.service;

import cn.hutool.core.util.ObjectUtil;
import com.dxj.common.util.PageUtil;
import com.dxj.common.util.StringUtil;
import com.dxj.generator.entity.GenConfig;
import com.dxj.generator.entity.vo.ColumnInfo;
import com.dxj.generator.entity.vo.TableInfo;
import com.dxj.common.exception.BadRequestException;
import com.dxj.generator.util.GenUtil;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dxj
 * @date 2019-01-02
 */
@Service
public class GeneratorService {

    @PersistenceContext
    private EntityManager em;

    /**
     * 查询数据库元数据
     *
     * @param name
     * @param startEnd
     * @return
     */
    public Object getTableList(String name, int[] startEnd) {
        // 使用预编译防止sql注入
        String sql = "select table_name ,create_time , engine, table_collation, table_comment from information_schema.tables " +
                "where table_schema = (select database()) " +
                "and table_name like ? order by create_time desc";
        Query query = em.createNativeQuery(sql);
        query.setFirstResult(startEnd[0]);
        query.setMaxResults(startEnd[1] - startEnd[0]);
        query.setParameter(1, StringUtil.isNotBlank(name) ? ("%" + name + "%") : "%%");
        List result = query.getResultList();
        List<TableInfo> tableInfos = new ArrayList<>();
        for (Object obj : result) {
            Object[] arr = (Object[]) obj;
            tableInfos.add(new TableInfo(arr[0], arr[1], arr[2], arr[3], ObjectUtil.isNotEmpty(arr[4]) ? arr[4] : "-"));
        }
        Query query1 = em.createNativeQuery("SELECT COUNT(*) from information_schema.tables where table_schema = (select database())");
        Object totalElements = query1.getSingleResult();
        return PageUtil.toPage(tableInfos, totalElements);
    }

    /**
     * 得到数据表的元数据
     *
     * @param name
     * @return
     */
    public Object getColumnList(String name) {
        // 使用预编译防止sql注入
        String sql = "select column_name, is_nullable, data_type, column_comment, column_key, extra from information_schema.columns " +
                "where table_name = ? and table_schema = (select database()) order by ordinal_position";
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, StringUtil.isNotBlank(name) ? name : null);
        List result = query.getResultList();
        List<ColumnInfo> columnInfos = new ArrayList<>();
        for (Object obj : result) {
            Object[] arr = (Object[]) obj;
            columnInfos.add(new ColumnInfo(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], null, "true"));
        }
        return PageUtil.toPage(columnInfos, columnInfos.size());
    }

    /**
     * 生成代码
     *
     * @param columnInfoList
     * @param genConfig
     * @param tableName
     */
    public void generator(List<ColumnInfo> columnInfoList, GenConfig genConfig, String tableName) {
        if (genConfig.getId() == null) {
            throw new BadRequestException("请先配置生成器");
        }
        try {
            GenUtil.generatorCode(columnInfoList, genConfig, tableName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
