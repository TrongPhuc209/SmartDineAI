package com.SmartDineAI.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SmartDineAI.entity.Role;
import com.SmartDineAI.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    boolean existsByUsername(String username);
    boolean existsByPhoneNumber(String phoneNumber);
    String findPasswordByUsername(String username);
    Optional<User> findByUsername(String username);
    boolean existsByUsernameAndIdNot(String username, Long id);

    @Query("""
        SELECT u FROM User u
        WHERE 
            (:keyword IS NULL OR 
                LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                LOWER(u.phoneNumber) LIKE LOWER(CONCAT('%', :keyword, '%'))
            )
        AND (:role IS NULL OR u.role = :role)
        AND (:active IS NULL OR u.active = :active)
        AND (:fromDate IS NULL OR u.createdAt >= :fromDate)
        AND (:toDate IS NULL OR u.createdAt <= :toDate)
    """)
    Page<User> searchUser(
            @Param("keyword") String keyword,
            @Param("role") Role role,
            @Param("active") Boolean isActive,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            Pageable pageable
    );
}
