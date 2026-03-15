package com.SmartDineAI.repository;

import com.SmartDineAI.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    // lấy món ăn theo restaurant
    List<Food> findByRestaurantId(Long restaurantId);

    // lấy món ăn theo category
    List<Food> findByCategoryId(Long categoryId);

    // lấy món ăn theo restaurant và category
    List<Food> findByRestaurantIdAndCategoryId(Long restaurantId, Long categoryId);

    // chỉ lấy món đang bán
    List<Food> findByRestaurantIdAndIsAvailableTrue(Long restaurantId);

    // tìm kiếm món ăn
    List<Food> findByNameContainingIgnoreCase(String name);

    @Query("""
        SELECT f
        FROM Food f
        JOIN f.category c
        WHERE c.restaurant.id = :restaurantId
        AND LOWER(f.name) LIKE LOWER(CONCAT('%', :name, '%'))
    """)
    List<Food> searchFoodByNameAndRestaurant(String name, Long restaurantId);

}