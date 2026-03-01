package com.SmartDineAI.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SmartDineAI.entity.Role;
import com.SmartDineAI.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    boolean existsByUsername(String username);
    String findPasswordByUsername(String username);
    Optional<User> findByUsername(String username);

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
        AND (:isActive IS NULL OR u.isActive = :isActive)
        AND (:fromDate IS NULL OR u.createdAt >= :fromDate)
        AND (:toDate IS NULL OR u.createdAt <= :toDate)
    """)
    List<User> searchUser(
            @Param("keyword") String keyword,
            @Param("role") Role role,
            @Param("isActive") Boolean isActive,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate
    );
}
