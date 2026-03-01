package com.SmartDineAI.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SmartDineAI.dto.reservation.CreateReservationRequest;
import com.SmartDineAI.dto.reservation.ReservationResponse;
import com.SmartDineAI.dto.reservation.UpdateReservationRequest;
import com.SmartDineAI.entity.Reservation;
import com.SmartDineAI.exception.AppException;
import com.SmartDineAI.exception.ErrorCode;
import com.SmartDineAI.mapper.ReservationMapper;
import com.SmartDineAI.repository.*;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final CustomerRepository customerRepository;
    private final DiningTableRepository diningTableRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReservationStatusRepository reservationStatusRepository;
    private final UserRepository userRepository;

    // ========================= CREATE =========================

    public ReservationResponse createReservation(CreateReservationRequest request) {

        validateTimeRange(request.getStartTime(), request.getEndTime());

        var diningTable = diningTableRepository.findById(request.getDiningTableId())
                .orElseThrow(() -> new AppException(ErrorCode.DINING_TABLE_NOT_FOUND));

        checkOverlapping(
                null,
                request.getDiningTableId(),
                request.getStartTime(),
                request.getEndTime()
        );

        Reservation reservation = reservationMapper.toReservation(request);

        reservation.setCustomer(customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND)));

        reservation.setDiningTable(diningTable);

        reservation.setRestaurant(restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new AppException(ErrorCode.RESTAURANT_NOT_FOUND)));

        reservation.setReservationStatus(reservationStatusRepository.findById(request.getReservationStatusId())
                .orElseThrow(() -> new AppException(ErrorCode.RESERVATION_STATUS_NOT_FOUND)));

        reservation.setUser(userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));

        reservationRepository.save(reservation);

        return reservationMapper.toResponse(reservation);
    }

    // ========================= GET =========================

    @Transactional(readOnly = true)
    public ReservationResponse getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND));
        return reservationMapper.toResponse(reservation);
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> getAllReservation() {
        return reservationRepository.findAll()
                .stream()
                .map(reservationMapper::toResponse)
                .toList();
    }

    // ========================= UPDATE =========================

    public ReservationResponse updateReservation(Long id, UpdateReservationRequest request) {

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND));

        validateTimeRange(request.getStartTime(), request.getEndTime());

        checkOverlapping(
                id, // exclude itself
                request.getDiningTableId(),
                request.getStartTime(),
                request.getEndTime()
        );

        reservationMapper.updateReservation(reservation, request);

        reservation.setCustomer(customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND)));

        reservation.setDiningTable(diningTableRepository.findById(request.getDiningTableId())
                .orElseThrow(() -> new AppException(ErrorCode.DINING_TABLE_NOT_FOUND)));

        reservation.setRestaurant(restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new AppException(ErrorCode.RESTAURANT_NOT_FOUND)));

        reservation.setReservationStatus(reservationStatusRepository.findById(request.getReservationStatusId())
                .orElseThrow(() -> new AppException(ErrorCode.RESERVATION_STATUS_NOT_FOUND)));

        reservation.setUser(userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));

        reservationRepository.save(reservation);

        return reservationMapper.toResponse(reservation);
    }

    // ========================= DELETE =========================

    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND));

        reservationRepository.delete(reservation);
    }

    // ========================= SEARCH =========================

    @Transactional(readOnly = true)
    public List<ReservationResponse> searchReservation(
            Long restaurantId,
            Long statusId,
            LocalDateTime from,
            LocalDateTime to,
            String keyword) {

        return reservationRepository
                .searchReservation(restaurantId, statusId, from, to, keyword)
                .stream()
                .map(reservationMapper::toResponse)
                .toList();
    }

    // ========================= PRIVATE METHODS =========================

    private void validateTimeRange(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null || !start.isBefore(end)) {
            throw new AppException(ErrorCode.INVALID_TIME_RANGE);
        }
    }

    private void checkOverlapping(
            Long reservationId,
            Long tableId,
            LocalDateTime start,
            LocalDateTime end) {

        boolean exists;

        if (reservationId == null) {
            exists = reservationRepository.existsOverlappingReservation(
                    tableId, start, end);
        } else {
            exists = reservationRepository.existsOverlappingReservationForUpdate(
                    reservationId, tableId, start, end);
        }

        if (exists) {
            throw new AppException(ErrorCode.TABLE_ALREADY_BOOKED);
        }
    }
}