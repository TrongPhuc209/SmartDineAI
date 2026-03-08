package com.SmartDineAI.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SmartDineAI.entity.Restaurant;


@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{
    boolean existsByName(String name);
    List<Restaurant> findAllByName(String name);
    @Query("""
        SELECT r FROM Restaurant r
        WHERE (:name IS NULL OR LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%')))
        AND (:active IS NULL OR r.active = :active)
    """)
   Page<Restaurant> searchRestaurant(
            @Param("name") String name,
            @Param("active") Boolean active,
            Pageable pageable
    );
}
