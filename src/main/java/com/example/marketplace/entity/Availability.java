package com.example.marketplace.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;


@Entity
@Table(name = "availability")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "dayOfWeek")
    private int dayOfWeek;

    @Column(name = "startTime", columnDefinition = "TIME")
    private LocalTime startTime;

    @Column(name = "endTime", columnDefinition = "TIME")
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "providerId", referencedColumnName = "id")
    private Provider provider;
}
