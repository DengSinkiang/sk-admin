package com.dxj.monitor.repository;

import com.dxj.monitor.domain.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jie
 * @date 2019-04-13
 */
@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    /**
     * findByDate
     *
     * @param date
     * @return
     */
    Visit findByDate(String date);

    /**
     * 获得一个时间段的记录
     *
     * @param date1
     * @param date2
     * @return
     */
    @Query(value = "select * FROM visits where " +
            "create_time between ?1 and ?2", nativeQuery = true)
    List<Visit> findAllVisits(String date1, String date2);
}
