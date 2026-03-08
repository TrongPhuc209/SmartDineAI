package com.SmartDineAI.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SmartDineAI.entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("""
        SELECT r FROM Reservation r
        WHERE (:restaurantId IS NULL OR r.restaurant.id = :restaurantId)
        AND (:statusId IS NULL OR r.reservationStatus.id = :statusId)
        AND (
            (:from IS NULL OR :to IS NULL)
            OR (r.startTime < :to AND r.endTime > :from)
        )
        AND (
            :keyword IS NULL
            OR LOWER(r.customer.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(r.customer.phoneNumber) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(r.diningTable.tableCode) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(r.restaurant.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
        ORDER BY r.startTime DESC
    """)
    Page<Reservation> searchReservation(
            Long restaurantId,
            Long statusId,
            LocalDateTime from,
            LocalDateTime to,
            String keyword,
            Pageable pageable
    );

    @Query("""
    SELECT COUNT(r) > 0 FROM Reservation r
    WHERE r.diningTable.id = :tableId
    AND r.reservationStatus.id <> 3
    AND (
        r.startTime < :endTime
        AND r.endTime > :startTime
    )
    """)
    boolean existsOverlappingReservation(
            @Param("tableId") Long tableId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    @Query("""
        SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
        FROM Reservation r
        WHERE r.id <> :reservationId
        AND r.diningTable.id = :tableId
        AND r.startTime < :end
        AND r.endTime > :start
    """)
    boolean existsOverlappingReservationForUpdate(
        Long reservationId,
        Long tableId,
        LocalDateTime start,
        LocalDateTime end
    );

    Page<Reservation> findByCustomerPhoneNumber(String phoneNumber, Pageable pageable);
    List<Reservation> findByCustomerPhoneNumber(String phoneNumber);

    @Query("""
    SELECT COUNT(r) > 0
    FROM Reservation r
    WHERE r.diningTable.id = :tableId
    AND r.startTime < :endTime
    AND r.endTime > :startTime
    """)
    boolean existsOverlap(
            Long tableId,
            LocalDateTime startTime,
            LocalDateTime endTime
    );
}
