package com.dxj.module.monitor.service.impl;

import com.dxj.dao.LogDao;
import com.dxj.module.monitor.dao.VisitsDao;
import com.dxj.module.monitor.entity.Visits;
import com.dxj.module.monitor.service.VisitsService;
import com.dxj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Sinkiang
 * @date 2018-12-13
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class VisitsServiceImpl implements VisitsService {

    private final VisitsDao visitsDao;

    private final LogDao logDao;

    public VisitsServiceImpl(VisitsDao visitsDao, LogDao logDao) {
        this.visitsDao = visitsDao;
        this.logDao = logDao;
    }

    @Override
    public void save() {
        LocalDate localDate = LocalDate.now();
        Visits visits = visitsDao.findByDate(localDate.toString());
        if (visits == null) {
            visits = new Visits();
            visits.setWeekDay(StringUtils.getWeekDay());
            visits.setPvCounts(1L);
            visits.setIpCounts(1L);
            visits.setDate(localDate.toString());
            visitsDao.save(visits);
        }
    }

    @Override
    public void count(HttpServletRequest request) {
        LocalDate localDate = LocalDate.now();
        Visits visits = visitsDao.findByDate(localDate.toString());
        visits.setPvCounts(visits.getPvCounts() + 1);
        long ipCounts = logDao.findIp(localDate.toString(), localDate.plusDays(1).toString());
        visits.setIpCounts(ipCounts);
        visitsDao.save(visits);
    }

    @Override
    public Map<String, Object> get() {
        Map<String, Object> map = new HashMap<>(4);
        LocalDate localDate = LocalDate.now();
        Visits visits = visitsDao.findByDate(localDate.toString());
        List<Visits> list = visitsDao.findAllVisits(localDate.minusDays(6).toString(), localDate.plusDays(1).toString());

        long recentVisits = 0, recentIp = 0;
        for (Visits data : list) {
            recentVisits += data.getPvCounts();
            recentIp += data.getIpCounts();
        }
        map.put("newVisits", visits.getPvCounts());
        map.put("newIp", visits.getIpCounts());
        map.put("recentVisits", recentVisits);
        map.put("recentIp", recentIp);
        return map;
    }

    @Override
    public Map<String, Object> getChartData() {
        Map<String, Object> map = new HashMap<>(3);
        LocalDate localDate = LocalDate.now();
        List<Visits> list = visitsDao.findAllVisits(localDate.minusDays(6).toString(), localDate.plusDays(1).toString());
        map.put("weekDays", list.stream().map(Visits::getWeekDay).collect(Collectors.toList()));
        map.put("visitsData", list.stream().map(Visits::getPvCounts).collect(Collectors.toList()));
        map.put("ipData", list.stream().map(Visits::getIpCounts).collect(Collectors.toList()));
        return map;
    }
}
