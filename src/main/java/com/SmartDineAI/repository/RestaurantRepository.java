package com.SmartDineAI.repository;

import java.util.List;

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
        AND (:isActive IS NULL OR r.isActive = :isActive)
    """)
    List<Restaurant> searchRestaurant(
            @Param("name") String name,
            @Param("isActive") Boolean isActive
    );
}
