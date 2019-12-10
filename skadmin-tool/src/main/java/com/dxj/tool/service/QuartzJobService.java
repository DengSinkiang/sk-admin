package com.dxj.tool.service;

import com.dxj.common.exception.BadRequestException;
import com.dxj.common.util.BaseQuery;
import com.dxj.common.util.PageUtil;
import com.dxj.common.util.ValidationUtil;
import com.dxj.tool.entity.QuartzJob;
import com.dxj.tool.entity.QuartzLog;
import com.dxj.tool.repository.QuartzJobRepository;
import com.dxj.tool.repository.QuartzLogRepository;
import com.dxj.tool.util.QuartzManage;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author dxj
 * @date 2019-01-07
 */
@Service(value = "quartzJobService")
@CacheConfig(cacheNames = "quartzJob")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class QuartzJobService {

    private final QuartzJobRepository quartzJobRepository;

    private final QuartzManage quartzManage;

    private final QuartzLogRepository quartzLogRepository;
    @Autowired
    public QuartzJobService(QuartzJobRepository quartzJobRepository, QuartzLogRepository quartzLogRepository, QuartzManage quartzManage) {
        this.quartzJobRepository = quartzJobRepository;
        this.quartzLogRepository = quartzLogRepository;
        this.quartzManage = quartzManage;
    }

    @Cacheable(key = "#p0")
    public QuartzJob findById(Long id) {
        Optional<QuartzJob> quartzJob = quartzJobRepository.findById(id);
        ValidationUtil.isNull(quartzJob, "QuartzJob", "id", id);
        return quartzJob.orElse(null);
    }

    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public QuartzJob create(QuartzJob resources) {
        if (!CronExpression.isValidExpression(resources.getCronExpression())) {
            throw new BadRequestException("cron表达式格式错误");
        }
        resources = quartzJobRepository.save(resources);
        quartzManage.addJob(resources);
        return resources;
    }

    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(QuartzJob resources) {
        if (resources.getId().equals(1L)) {
            throw new BadRequestException("该任务不可操作");
        }
        if (!CronExpression.isValidExpression(resources.getCronExpression())) {
            throw new BadRequestException("cron表达式格式错误");
        }
        resources = quartzJobRepository.save(resources);
        quartzManage.updateJobCron(resources);
    }

    @CacheEvict(allEntries = true)
    public void updateIsPause(QuartzJob quartzJob) {
        if (quartzJob.getId().equals(1L)) {
            throw new BadRequestException("该任务不可操作");
        }
        if (quartzJob.getIsPause()) {
            quartzManage.resumeJob(quartzJob);
            quartzJob.setIsPause(false);
        } else {
            quartzManage.pauseJob(quartzJob);
            quartzJob.setIsPause(true);
        }
        quartzJobRepository.save(quartzJob);
    }

    public void execution(QuartzJob quartzJob) {
        if (quartzJob.getId().equals(1L)) {
            throw new BadRequestException("该任务不可操作");
        }
        quartzManage.runAJobNow(quartzJob);
    }

    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(QuartzJob quartzJob) {
        if (quartzJob.getId().equals(1L)) {
            throw new BadRequestException("该任务不可操作");
        }
        quartzManage.deleteJob(quartzJob);
        quartzJobRepository.delete(quartzJob);
    }

    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(QuartzJob query, Pageable pageable) {
        return PageUtil.toPage(quartzJobRepository.findAll((root, criteriaQuery, criteriaBuilder) -> BaseQuery.getPredicate(root, query, criteriaBuilder), pageable));
    }

    public Object queryAllLog(QuartzLog query, Pageable pageable) {
        return PageUtil.toPage(quartzLogRepository.findAll((root, criteriaQuery, criteriaBuilder) -> BaseQuery.getPredicate(root, query, criteriaBuilder), pageable));
    }
}
