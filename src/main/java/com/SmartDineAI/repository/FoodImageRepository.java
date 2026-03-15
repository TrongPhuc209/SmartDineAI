package com.SmartDineAI.repository;

import com.SmartDineAI.entity.FoodImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodImageRepository extends JpaRepository<FoodImage, Long> {

    // lấy tất cả ảnh của món ăn
    List<FoodImage> findByFoodId(Long foodId);

    // lấy ảnh chính của món
    FoodImage findByFoodIdAndIsMainTrue(Long foodId);

}