package com.dxj.generator.controller;

import cn.hutool.core.util.PageUtil;
import com.dxj.generator.domain.vo.ColumnInfo;
import com.dxj.common.exception.BadRequestException;
import com.dxj.generator.service.GenConfigService;
import com.dxj.generator.service.GeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dxj
 * @date 2019-01-02
 */
@RestController
@RequestMapping("api")
public class GeneratorController {

    private final GeneratorService generatorService;

    private final GenConfigService genConfigService;

    @Value("${generator.enabled}")
    private Boolean generatorEnabled;

    @Autowired
    public GeneratorController(GeneratorService generatorService, GenConfigService genConfigService) {
        this.generatorService = generatorService;
        this.genConfigService = genConfigService;
    }

    /**
     * 查询数据库元数据
     *
     * @param name
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/generator/tables")
    public ResponseEntity<Object> getDataBaseList(@RequestParam(defaultValue = "") String name,
                                    @RequestParam(defaultValue = "0") Integer page,
                                    @RequestParam(defaultValue = "10") Integer size) {
        int[] startEnd = PageUtil.transToStartEnd(page + 1, size);
        return new ResponseEntity<>(generatorService.getTables(name, startEnd), HttpStatus.OK);
    }

    /**
     * 查询表内元数据
     *
     * @param tableName
     * @return
     */
    @GetMapping(value = "/generator/columns")
    public ResponseEntity<Object> getTableList(@RequestParam String tableName) {
        return new ResponseEntity<>(generatorService.getColumns(tableName), HttpStatus.OK);
    }

    /**
     * 生成代码
     *
     * @param columnInfos
     * @return
     */
    @PostMapping(value = "/generator")
    public ResponseEntity<Void> generator(@RequestBody List<ColumnInfo> columnInfos, @RequestParam String tableName) {
        if (!generatorEnabled) {
            throw new BadRequestException("此环境不允许生成代码！");
        }
        generatorService.generator(columnInfos, genConfigService.find(), tableName);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
