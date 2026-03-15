package com.SmartDineAI.repository;

import com.SmartDineAI.entity.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {

    // lấy tất cả category theo restaurant
    List<FoodCategory> findByRestaurantId(Long restaurantId);

    // kiểm tra category có tồn tại trong restaurant không
    boolean existsByNameAndRestaurantId(String name, Long restaurantId);

}