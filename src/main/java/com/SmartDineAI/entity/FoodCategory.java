package com.SmartDineAI.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "FOOD_CATEGORY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CAT")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_RTR", nullable = false)
    private Restaurant restaurant;

    @Column(name = "NAME_CAT", nullable = false, length = 100)
    private String name;

    @Column(name = "DESCRIPTION_CAT", length = 255)
    private String description;

    @OneToMany(mappedBy = "category")
    private List<Food> foods;
}