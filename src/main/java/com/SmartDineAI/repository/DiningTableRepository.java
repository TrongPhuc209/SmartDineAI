package com.SmartDineAI.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        AND (:restaurantId IS NULL OR d.restaurant.id = :restaurantId)
    """)
    Page<DiningTable> searchDiningTable(
            @Param("tableCode") String tableCode,
            @Param("capacity") Integer capacity,
            @Param("active") Boolean active,
            @Param("location") String location,
            @Param("restaurantId") Long restaurantId,
            Pageable pageable
    );

    @Query("""
        SELECT dt FROM DiningTable dt
        WHERE dt.active = true
        AND NOT EXISTS (
            SELECT r FROM Reservation r
            WHERE r.diningTable = dt
            AND r.reservationStatus.statusName IN ('CONFIRMED', 'PENDING')
            AND r.startTime < :endTime
            AND r.endTime > :startTime
        )
    """)
    Page<DiningTable> findAvailableTablesByTime(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            Pageable pageable
    );

    List<DiningTable> findByRestaurantIdAndCapacityGreaterThanEqual(
        Long restaurantId,
        Integer capacity
);
}
