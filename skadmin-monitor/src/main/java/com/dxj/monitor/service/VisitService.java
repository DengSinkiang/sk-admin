package com.dxj.monitor.service;

import com.dxj.monitor.domain.entity.Visit;
import com.dxj.monitor.repository.VisitRepository;
import com.dxj.log.repository.LogRepository;
import com.dxj.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
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
 * @author dxj
 * @date 2019-04-13
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class VisitService {

    private final VisitRepository visitRepository;

    private final LogRepository logRepository;

    @Autowired
    public VisitService(VisitRepository visitRepository, LogRepository logRepository) {
        this.visitRepository = visitRepository;
        this.logRepository = logRepository;
    }

    public void save() {
        LocalDate localDate = LocalDate.now();
        Visit visit = visitRepository.findByDate(localDate.toString());
        if (visit == null) {
            visit = new Visit();
            visit.setWeekDay(StringUtil.getWeekDay());
            visit.setPvCounts(1L);
            visit.setIpCounts(1L);
            visit.setDate(localDate.toString());
            visitRepository.save(visit);
        }
    }

    public void count(HttpServletRequest request) {
        LocalDate localDate = LocalDate.now();
        Visit visit = visitRepository.findByDate(localDate.toString());
        visit.setPvCounts(visit.getPvCounts() + 1);
        long ipCounts = logRepository.findIp(localDate.toString(), localDate.plusDays(1).toString());
        visit.setIpCounts(ipCounts);
        visitRepository.save(visit);
    }

    public Object get() {
        Map<String, Object> map = new HashMap<>();
        LocalDate localDate = LocalDate.now();
        Visit visit = visitRepository.findByDate(localDate.toString());
        List<Visit> list = visitRepository.findAllVisits(localDate.minusDays(6).toString(), localDate.plusDays(1).toString());

        long recentVisits = 0, recentIp = 0;
        for (Visit data : list) {
            recentVisits += data.getPvCounts();
            recentIp += data.getIpCounts();
        }
        map.put("newVisits", visit.getPvCounts());
        map.put("newIp", visit.getIpCounts());
        map.put("recentVisits", recentVisits);
        map.put("recentIp", recentIp);
        return map;
    }

    public Object getChartData() {
        Map<String, Object> map = new HashMap<>();
        LocalDate localDate = LocalDate.now();
        List<Visit> list = visitRepository.findAllVisits(localDate.minusDays(6).toString(), localDate.plusDays(1).toString());
        map.put("weekDays", list.stream().map(Visit::getWeekDay).collect(Collectors.toList()));
        map.put("visitsData", list.stream().map(Visit::getPvCounts).collect(Collectors.toList()));
        map.put("ipData", list.stream().map(Visit::getIpCounts).collect(Collectors.toList()));
        return map;
    }
}
