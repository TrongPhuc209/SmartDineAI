package com.SmartDineAI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SmartDineAI.entity.DiningTable;

@Repository
public interface DiningTableRepository extends JpaRepository<DiningTable, Long>{
    @Query("""
        SELECT d FROM DiningTable d 
        WHERE (:tableCode IS NULL OR LOWER(d.tableCode) LIKE LOWER(CONCAT('%', :tableCode, '%')))
        AND (:capacity IS NULL OR d.capacity = :capacity)
        AND (:active IS NULL OR d.active = :active)
        AND (:location IS NULL OR LOWER(d.location) LIKE LOWER(CONCAT('%', :location, '%')))
    """)
    List<DiningTable> searchDiningTable(
        @Param("tableCode") String tableCode,
        @Param("capacity") Integer capacity,
        @Param("active") Boolean active,
        @Param("location") String location
    );
}
