package com.dxj.service;

import com.dxj.domain.GenConfig;
import com.dxj.domain.vo.ColumnInfo;
import com.dxj.domain.vo.TableInfo;
import com.dxj.exception.BadRequestException;
import com.dxj.utils.GenUtil;
import com.dxj.utils.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
    public Object getTables(String name, int[] startEnd) {
        StringBuilder sql = new StringBuilder("select table_name tableName,create_time createTime from information_schema.tables where table_schema = (select database()) ");
        if (!ObjectUtils.isEmpty(name)) {
            sql.append("and table_name like '%" + name + "%' ");
        }
        sql.append("order by table_name");
        Query query = em.createNativeQuery(sql.toString());
        query.setFirstResult(startEnd[0]);
        query.setMaxResults(startEnd[1]);

        System.out.println(sql.toString());
        List<Object[]> result = query.getResultList();
        List<TableInfo> tableInfos = new ArrayList<>();
        for (Object[] obj : result) {
            tableInfos.add(new TableInfo(obj[0], obj[1]));
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
    public Object getColumns(String name) {
        StringBuilder sql = new StringBuilder("select column_name, is_nullable, data_type, column_comment, column_key from information_schema.columns where ");
        if (!ObjectUtils.isEmpty(name)) {
            sql.append("table_name = '" + name + "' ");
        }
        sql.append("and table_schema = (select database()) order by ordinal_position");
        Query query = em.createNativeQuery(sql.toString());
        List<Object[]> result = query.getResultList();
        List<ColumnInfo> columnInfos = new ArrayList<>();
        for (Object[] obj : result) {
            columnInfos.add(new ColumnInfo(obj[0], obj[1], obj[2], obj[3], obj[4], null, "true"));
        }
        return PageUtil.toPage(columnInfos, columnInfos.size());
    }

    /**
     * 生成代码
     *
     * @param columnInfos
     * @param genConfig
     * @param tableName
     */
    public void generator(List<ColumnInfo> columnInfos, GenConfig genConfig, String tableName) {
        if (genConfig.getId() == null) {
            throw new BadRequestException("请先配置生成器");
        }
        try {
            GenUtil.generatorCode(columnInfos, genConfig, tableName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
