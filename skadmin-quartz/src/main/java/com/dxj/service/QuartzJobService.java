package com.dxj.service;

import com.dxj.domain.QuartzLog;
import com.dxj.exception.BadRequestException;
import com.dxj.domain.QuartzJob;
import com.dxj.repository.QuartzJobRepository;
import com.dxj.repository.QuartzLogRepository;
import com.dxj.service.query.QuartzLogQueryService;
import com.dxj.spec.QuartzJobSpec;
import com.dxj.spec.QuartzLogSpec;
import com.dxj.utils.PageUtil;
import com.dxj.utils.QuartzManage;
import com.dxj.utils.ValidationUtil;
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
    public Object queryAll(QuartzJob quartzJob, Pageable pageable) {
        return PageUtil.toPage(quartzJobRepository.findAll(QuartzJobSpec.getSpec(quartzJob), pageable));
    }

    public Object queryAll(QuartzLog quartzLog, Pageable pageable) {
        return PageUtil.toPage(quartzLogRepository.findAll(QuartzLogSpec.getSpec(quartzLog), pageable));
    }
}
