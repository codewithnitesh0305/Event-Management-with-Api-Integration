package com.springboot.Repository;

import com.springboot.Model.Events;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventsRepository extends JpaRepository<Events, Integer> {
    @Query(value = "SELECT * FROM events WHERE date  BETWEEN :startDate AND DATE_ADD(:startDate, INTERVAL 14 DAY) ORDER BY date  ASC", nativeQuery = true)
    Page<Events> searchEvents(@Param("startDate") LocalDate startDate, Pageable pageable);
}
