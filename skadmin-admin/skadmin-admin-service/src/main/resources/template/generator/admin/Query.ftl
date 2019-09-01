package ${package}.dto;

import lombok.Data;
<#if hasTimestamp>
import java.sql.Timestamp;
</#if>
<#if hasBigDecimal>
import java.math.BigDecimal;
</#if>
<#if queryColumns??>
import com.dxj.common.annotation.Query;
</#if>

/**
* @author ${author}
* @date ${date}
*/
@Data
public class ${className}Query {
<#if queryColumns??>
    <#list queryColumns as column>

    <#if column.columnQuery = '1'>
    // 模糊
    @Query(type = Query.Type.LIKE)
    </#if>
    <#if column.columnQuery = '2'>
    // 精确
    @Query
    </#if>
    private ${column.columnType} ${column.changeColumnName};
    </#list>
</#if>
}
