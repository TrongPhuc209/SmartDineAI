package com.SmartDineAI.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "FOOD")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FOOD")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_RTR", nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "ID_CAT", nullable = false)
    private FoodCategory category;

    @Column(name = "NAME_FOOD", nullable = false, length = 150)
    private String name;

    @Column(name = "DESCRIPTION_FOOD", length = 500)
    private String description;

    @Column(name = "PRICE_FOOD", nullable = false)
    private BigDecimal price;

    @Column(name = "IS_AVAILABLE_FOOD", nullable = false)
    private Boolean isAvailable;

    @Column(name = "CREATED_AT_FOOD", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "food")
    private List<FoodImage> images;
}