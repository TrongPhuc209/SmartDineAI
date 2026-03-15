package com.SmartDineAI.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "REVIEW")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REVIEW")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_RTR", nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "ID_RSVT", nullable = false)
    private Reservation reservation;

    @Column(name = "RATING_REVIEW", nullable = false)
    private Integer rating;

    @Column(name = "COMMENT_REVIEW", length = 500)
    private String comment;

    @Column(name = "CREATED_AT_REVIEW", nullable = false)
    private LocalDateTime createdAt;
}