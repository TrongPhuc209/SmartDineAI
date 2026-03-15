package com.SmartDineAI.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "FOOD_IMAGE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_IMG")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_FOOD", nullable = false)
    private Food food;

    @Column(name = "IMAGE_URL", nullable = false, length = 500)
    private String imageUrl;

    @Column(name = "IS_MAIN_IMG")
    private Boolean isMain;
}