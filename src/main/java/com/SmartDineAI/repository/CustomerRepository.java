package com.SmartDineAI.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SmartDineAI.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    Customer findByPhoneNumber(String phoneNumber);
    @Query("""
            SELECT c FROM Customer c
            WHERE (:phoneNumber IS NULL OR c.phoneNumber LIKE CONCAT('%', :phoneNumber, '%'))
            """)
    Page<Customer> searchCustomer(Pageable pageable, 
                                    @Param("phoneNumber") String phoneNumber);
}
