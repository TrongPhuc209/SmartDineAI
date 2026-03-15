package com.SmartDineAI.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SmartDineAI.dto.diningTable.DiningTableResponse;
import com.SmartDineAI.dto.reservation.CreateReservationRequest;
import com.SmartDineAI.dto.reservation.ReservationResponse;
import com.SmartDineAI.dto.reservation.UpdateReservationRequest;
import com.SmartDineAI.dto.reservation.UpdateReservationStatusRequest;
import com.SmartDineAI.entity.Customer;
import com.SmartDineAI.entity.DiningTable;
import com.SmartDineAI.entity.Reservation;
import com.SmartDineAI.entity.ReservationStatus;
import com.SmartDineAI.entity.Restaurant;
import com.SmartDineAI.entity.User;
import com.SmartDineAI.exception.AppException;
import com.SmartDineAI.exception.ErrorCode;
import com.SmartDineAI.mapper.DiningTableMapper;
import com.SmartDineAI.mapper.ReservationMapper;
import com.SmartDineAI.repository.*;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final DiningTableRepository diningTableRepository;
    private final ReservationStatusRepository reservationStatusRepository;
    private final DiningTableMapper diningTableMapper;
    private final CustomerRepository customerRepository;
    private final UserRepository userrepository;
    private final RestaurantRepository restaurantRepository;

    // ========================= CREATE =========================

    public ReservationResponse createReservation(CreateReservationRequest request) {

        DiningTable diningTable = diningTableRepository.findById(request.getDiningTableId())
                .orElseThrow(() -> new AppException(ErrorCode.DINING_TABLE_NOT_FOUND));

        validateTimeRange(request.getStartTime(), request.getEndTime());
        validGuestCount(request.getGuestCount(), diningTable.getCapacity());
        checkOverlapping(
                null,
                request.getDiningTableId(),
                request.getStartTime(),
                request.getEndTime()
        );

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String userName = jwt.getSubject();

        if(request.getPhoneNumber().isBlank()){
            throw new AppException(ErrorCode.INVALID_FORMAT_PHONE_NUMBER);
        }

        Customer customer = resolveCustomer(request.getPhoneNumber(), null);

        Reservation reservation = reservationMapper.toReservation(request);

        reservation.setCustomer(customer);
        reservation.setDiningTable(diningTable);
        reservation.setReservationStatus(
                reservationStatusRepository.findById(1L).orElseThrow()
        );
        reservation.setUser(userrepository.findByUsername(userName).orElseThrow());
        reservation.setRestaurant(diningTable.getRestaurant());

        reservationRepository.save(reservation);

        return reservationMapper.toResponse(reservation);
    }


    public ReservationResponse createReservationById(CreateReservationRequest request, Long restaurantId) {

        DiningTable diningTable = diningTableRepository.findById(request.getDiningTableId())
                .orElseThrow(() -> new AppException(ErrorCode.DINING_TABLE_NOT_FOUND));

        validateTimeRange(request.getStartTime(), request.getEndTime());

        validGuestCount(request.getGuestCount(), diningTable.getCapacity());

        checkOverlapping(
                null,
                request.getDiningTableId(),
                request.getStartTime(),
                request.getEndTime()
        );

        // Lấy restaurant trước để kiểm tra giờ mở cửa
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new AppException(ErrorCode.RESTAURANT_NOT_FOUND));

        // Kiểm tra thời gian đặt có nằm trong giờ mở cửa
        validateRestaurantOpenTime(
                request.getStartTime(),
                request.getEndTime(),
                restaurant.getOpenTime(),
                restaurant.getCloseTime()
        );

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String userName = jwt.getSubject();

        // Lấy user từ database
        User user = userrepository.findByUsername(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Kiểm tra trạng thái tài khoản
        if (!user.getActive()) {
            throw new AppException(ErrorCode.ACCOUNT_DISABLED);
        }

        if (request.getPhoneNumber().isBlank()) {
            throw new AppException(ErrorCode.INVALID_FORMAT_PHONE_NUMBER);
        }

        Customer customer = resolveCustomer(request.getPhoneNumber(), null);

        Reservation reservation = reservationMapper.toReservation(request);

        reservation.setCustomer(customer);
        reservation.setDiningTable(diningTable);

        reservation.setReservationStatus(
                reservationStatusRepository.findById(1L).orElseThrow()
        );

        reservation.setUser(user);

        reservation.setRestaurant(restaurant);

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
    public Page<ReservationResponse> getAllReservation(Pageable pageable) {
        return reservationRepository.findAll(pageable).map(reservationMapper::toResponse);
    }

    public Page<ReservationResponse> myReservation(Pageable pageable){
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userrepository.findByUsername(currentUser).orElseThrow();
        return reservationRepository.findByCustomerPhoneNumber(user.getPhoneNumber(), 
                                                                pageable).map(reservationMapper::toResponse);
    }

    public ReservationResponse myReservation(Long reservationId){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userrepository.findByUsername(userName).orElseThrow();
        Reservation reservation = reservationRepository.findById(reservationId)
                                                        .orElseThrow(() -> new AppException(ErrorCode.RESERVATION_NOT_FOUND));
        String phoneNumber = reservation.getCustomer().getPhoneNumber();
        if(!phoneNumber.equals(user.getPhoneNumber())){
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }
        return reservationMapper.toResponse(reservation);
    }

    // ========================= UPDATE =========================

    public ReservationResponse updateReservation(Long id, UpdateReservationRequest request) {

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND));

        LocalDateTime start = request.getStartTime() != null
                ? request.getStartTime()
                : reservation.getStartTime();

        LocalDateTime end = request.getEndTime() != null
                ? request.getEndTime()
                : reservation.getEndTime();

        Long tableId = request.getDiningTableId() != null
                ? request.getDiningTableId()
                : reservation.getDiningTable().getId();

        validateTimeRange(start, end);

        checkOverlapping(
                id,
                tableId,
                start,
                end
        );

        reservationMapper.updateReservation(reservation, request);

        if (request.getDiningTableId() != null) {
            reservation.setDiningTable(
                    diningTableRepository.findById(request.getDiningTableId())
                            .orElseThrow(() -> new AppException(ErrorCode.DINING_TABLE_NOT_FOUND))
            );
        }

        if (request.getReservationStatusId() != null) {
            reservation.setReservationStatus(
                    reservationStatusRepository.findById(request.getReservationStatusId())
                            .orElseThrow(() -> new AppException(ErrorCode.RESERVATION_STATUS_NOT_FOUND))
            );
        }

        reservationRepository.save(reservation);

        return reservationMapper.toResponse(reservation);
    }

    // ========================= DELETE =========================

    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND));

        reservationRepository.delete(reservation);
    }

    public void myDelete(Long reservationId){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userrepository.findByUsername(userName).orElseThrow();
        Reservation reservation = reservationRepository.findById(reservationId)
                                                        .orElseThrow(() -> new AppException(ErrorCode.RESERVATION_NOT_FOUND));
        String phoneNumber = reservation.getCustomer().getPhoneNumber();
        if(!phoneNumber.equals(user.getPhoneNumber())){
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }

        validateCancelReservation(reservation);

        ReservationStatus reservationStatus = reservationStatusRepository.findByStatusName("CANCELLED_BY_CUSTOMER");
        reservation.setReservationStatus(reservationStatus);
        reservationRepository.save(reservation);

    }

    // ========================= SEARCH =========================

    @Transactional(readOnly = true)
    public Page<ReservationResponse> searchReservation(
            Long restaurantId,
            Long statusId,
            LocalDateTime from,
            LocalDateTime to,
            String keyword,
            Pageable pageable) {

        return reservationRepository
                .searchReservation(restaurantId, statusId, from, to, keyword, pageable)
                .map(reservationMapper::toResponse);
    }
    // ========================= BUSINESS METHOD =========================

    public Page<DiningTableResponse> findAvailableTablesByTime(LocalDateTime start, LocalDateTime end, Pageable pageable){
        return diningTableRepository.findAvailableTablesByTime(start, end, pageable).map(diningTableMapper::toResponse);
    }


    // ========================= PRIVATE METHODS =========================

    private void validateTimeRange(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null || !start.isBefore(end) || start.isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.INVALID_TIME_RANGE);
        }
    }

    private void validGuestCount(Integer guestCount, Integer capacity){
        if(guestCount > capacity){
               throw new AppException(ErrorCode.EXCEED_TABLE_CAPACITY);
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

    public Customer resolveCustomer(String phoneNumber, String userName){
        if(phoneNumber == null && userName != null && !userName.isEmpty()){
            User currentUser = userrepository.findByUsername(userName).orElseThrow();
            phoneNumber = currentUser.getPhoneNumber();    
        }

        Customer customer = customerRepository.findByPhoneNumber(phoneNumber);

        if(customer != null){
            return customer;
        } else {
            customer = Customer.builder()
                                .fullName("Customer")
                                .phoneNumber(phoneNumber)
                                .build();
            return customerRepository.save(customer);            
        }
    }

    private void validateRestaurantOpenTime(
            LocalDateTime startTime,
            LocalDateTime endTime,
            LocalTime openTime,
            LocalTime closeTime
    ) {

        LocalTime start = startTime.toLocalTime();
        LocalTime end = endTime.toLocalTime();

        if (start.isBefore(openTime) || end.isAfter(closeTime)) {
            throw new AppException(ErrorCode.RESTAURANT_CLOSED);
        }
    }

    private void validateCancelReservation(Reservation reservation){
        String myStatusName = reservation.getReservationStatus().getStatusName();
        LocalDateTime myStarTime = reservation.getStartTime();
        if(!myStatusName.equals("PENDING") && !myStatusName.equals("CONFIRMED")){
            throw new AppException(ErrorCode.INVALID_RESERVATION_STATUS);
        } if(LocalDateTime.now().isAfter(myStarTime.minusHours(1))){
            throw new AppException(ErrorCode.INVALID_CANCEL_TIME);
        } 
    }

    @Transactional
    public void updateReservationStatus(UpdateReservationStatusRequest request) {

        Reservation reservation = reservationRepository.findById(request.getReservationId())
                .orElseThrow(() -> new AppException(ErrorCode.RESERVATION_NOT_FOUND));

        ReservationStatus newStatus = reservationStatusRepository
                .findByStatusName(request.getStatus());

        LocalDateTime now = LocalDateTime.now();

        String status = request.getStatus();

        switch (status) {

            case "COMPLETED":
                if (now.isBefore(reservation.getEndTime())) {
                    throw new AppException(ErrorCode.CANNOT_COMPLETE);
                }
                break;

            case "CANCELLED_BY_CUSTOMER":
                if (now.isAfter(reservation.getStartTime())) {
                    throw new AppException(ErrorCode.CANNOT_CANCEL);
                }
                break;

            case "NO_SHOW":
                if (now.isBefore(reservation.getStartTime())) {
                    throw new AppException(ErrorCode.CANNOT_MARK_NO_SHOW);
                }
                break;

            case "CONFIRMED":
            case "REJECTED":
            case "CANCELLED_BY_ADMIN":
            case "PENDING":
                break;

            default:
                throw new AppException(ErrorCode.INVALID_RESERVATION_STATUS);
        }

        reservation.setReservationStatus(newStatus);

        reservationRepository.save(reservation);
    }
}