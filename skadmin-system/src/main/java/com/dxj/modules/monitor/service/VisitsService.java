package com.dxj.modules.monitor.service;

import lombok.extern.slf4j.Slf4j;
import com.dxj.modules.monitor.domain.Visits;
import com.dxj.modules.monitor.repository.VisitsRepository;
import com.dxj.repository.LogRepository;
import com.dxj.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @author jie
 * @date 2018-12-13
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class VisitsService {

    private final VisitsRepository visitsRepository;

    private final LogRepository logRepository;

    @Autowired
    public VisitsService(VisitsRepository visitsRepository, LogRepository logRepository) {
        this.visitsRepository = visitsRepository;
        this.logRepository = logRepository;
    }

    public void save() {
        LocalDate localDate = LocalDate.now();
        Visits visits = visitsRepository.findByDate(localDate.toString());
        if (visits == null) {
            visits = new Visits();
            visits.setWeekDay(StringUtils.getWeekDay());
            visits.setPvCounts(1L);
            visits.setIpCounts(1L);
            visits.setDate(localDate.toString());
            visitsRepository.save(visits);
        }
    }

    public void count(HttpServletRequest request) {
        LocalDate localDate = LocalDate.now();
        Visits visits = visitsRepository.findByDate(localDate.toString());
        visits.setPvCounts(visits.getPvCounts() + 1);
        long ipCounts = logRepository.findIp(localDate.toString(), localDate.plusDays(1).toString());
        visits.setIpCounts(ipCounts);
        visitsRepository.save(visits);
    }

    public Object get() {
        Map<String, Object> map = new HashMap<>();
        LocalDate localDate = LocalDate.now();
        Visits visits = visitsRepository.findByDate(localDate.toString());
        List<Visits> list = visitsRepository.findAllVisits(localDate.minusDays(6).toString(), localDate.plusDays(1).toString());

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

    public Object getChartData() {
        Map<String, Object> map = new HashMap<>();
        LocalDate localDate = LocalDate.now();
        List<Visits> list = visitsRepository.findAllVisits(localDate.minusDays(6).toString(), localDate.plusDays(1).toString());
        map.put("weekDays", list.stream().map(Visits::getWeekDay).collect(Collectors.toList()));
        map.put("visitsData", list.stream().map(Visits::getPvCounts).collect(Collectors.toList()));
        map.put("ipData", list.stream().map(Visits::getIpCounts).collect(Collectors.toList()));
        return map;
    }
}
