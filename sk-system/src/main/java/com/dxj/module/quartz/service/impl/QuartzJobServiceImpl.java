package com.dxj.module.quartz.service.impl;

import com.dxj.exception.SkException;
import com.dxj.module.quartz.dao.QuartzJobDao;
import com.dxj.module.quartz.dao.QuartzLogDao;
import com.dxj.module.quartz.domain.entity.QuartzJob;
import com.dxj.module.quartz.domain.entity.QuartzLog;
import com.dxj.module.quartz.domain.query.QuartzJobQuery;
import com.dxj.module.quartz.service.QuartzJobService;
import com.dxj.module.quartz.util.QuartzManage;
import com.dxj.util.FileUtils;
import com.dxj.util.PageUtil;
import com.dxj.util.QueryHelp;
import com.dxj.util.ValidationUtil;
import org.quartz.CronExpression;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author Sinkiang
 * @date 2019-01-07
 */
@Service(value = "quartzJobService")
@CacheConfig(cacheNames = "quartzJob")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class QuartzJobServiceImpl implements QuartzJobService {

    private final QuartzJobDao quartzJobDao;

    private final QuartzLogDao quartzLogDao;

    private final QuartzManage quartzManage;

    public QuartzJobServiceImpl(QuartzJobDao quartzJobDao, QuartzLogDao quartzLogDao, QuartzManage quartzManage) {
        this.quartzJobDao = quartzJobDao;
        this.quartzLogDao = quartzLogDao;
        this.quartzManage = quartzManage;
    }

    @Override
    @Cacheable
    public Object queryAll(QuartzJobQuery criteria, Pageable pageable) {
        return PageUtil.toPage(quartzJobDao.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable));
    }

    @Override
    public Object queryAllLog(QuartzJobQuery criteria, Pageable pageable) {
        return PageUtil.toPage(quartzLogDao.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable));
    }

    @Override
    public List<QuartzJob> queryAll(QuartzJobQuery criteria) {
        return quartzJobDao.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder));
    }

    @Override
    public List<QuartzLog> queryAllLog(QuartzJobQuery criteria) {
        return quartzLogDao.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder));
    }

    @Override
    @Cacheable(key = "#p0")
    public QuartzJob findById(Long id) {
        QuartzJob quartzJob = quartzJobDao.findById(id).orElseGet(QuartzJob::new);
        ValidationUtil.isNull(quartzJob.getId(), "QuartzJob", "id", id);
        return quartzJob;
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public QuartzJob create(QuartzJob resources) {
        if (!CronExpression.isValidExpression(resources.getCronExpression())) {
            throw new SkException("cron表达式格式错误");
        }
        resources = quartzJobDao.save(resources);
        quartzManage.addJob(resources);
        return resources;
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(QuartzJob resources) {
        if (resources.getId().equals(1L)) {
            throw new SkException("该任务不可操作");
        }
        if (!CronExpression.isValidExpression(resources.getCronExpression())) {
            throw new SkException("cron表达式格式错误");
        }
        resources = quartzJobDao.save(resources);
        quartzManage.updateJobCron(resources);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void updateIsPause(QuartzJob quartzJob) {
        if (quartzJob.getId().equals(1L)) {
            throw new SkException("该任务不可操作");
        }
        if (quartzJob.getIsPause()) {
            quartzManage.resumeJob(quartzJob);
            quartzJob.setIsPause(false);
        } else {
            quartzManage.pauseJob(quartzJob);
            quartzJob.setIsPause(true);
        }
        quartzJobDao.save(quartzJob);
    }

    @Override
    public void execution(QuartzJob quartzJob) {
        if (quartzJob.getId().equals(1L)) {
            throw new SkException("该任务不可操作");
        }
        quartzManage.runJobNow(quartzJob);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            if (id.equals(1L)) {
                throw new SkException("更新访客记录不可删除，你可以在后台代码中取消该限制");
            }
            QuartzJob quartzJob = findById(id);
            quartzManage.deleteJob(quartzJob);
            quartzJobDao.delete(quartzJob);
        }
    }

    @Override
    public void download(List<QuartzJob> quartzJobs, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (QuartzJob quartzJob : quartzJobs) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("任务名称", quartzJob.getJobName());
            map.put("Bean名称", quartzJob.getBeanName());
            map.put("执行方法", quartzJob.getMethodName());
            map.put("参数", quartzJob.getParams());
            map.put("表达式", quartzJob.getCronExpression());
            map.put("状态", quartzJob.getIsPause() ? "暂停中" : "运行中");
            map.put("描述", quartzJob.getRemark());
            map.put("创建日期", quartzJob.getCreateTime());
            list.add(map);
        }
        FileUtils.downloadExcel(list, response);
    }

    @Override
    public void downloadLog(List<QuartzLog> queryAllLog, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (QuartzLog quartzLog : queryAllLog) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("任务名称", quartzLog.getJobName());
            map.put("Bean名称", quartzLog.getBeanName());
            map.put("执行方法", quartzLog.getMethodName());
            map.put("参数", quartzLog.getParams());
            map.put("表达式", quartzLog.getCronExpression());
            map.put("异常详情", quartzLog.getExceptionDetail());
            map.put("耗时/毫秒", quartzLog.getTime());
            map.put("状态", quartzLog.getIsSuccess() ? "成功" : "失败");
            map.put("创建日期", quartzLog.getCreateTime());
            list.add(map);
        }
        FileUtils.downloadExcel(list, response);
    }
}
