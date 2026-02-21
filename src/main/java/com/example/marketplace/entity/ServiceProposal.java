package com.example.marketplace.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "serviceProposal")
public class ServiceProposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "price")
    private double price;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "service", referencedColumnName = "id")
    private Service service;

    @ManyToOne
    @JoinColumn(name = "provider", referencedColumnName = "id")
    private Provider provider;

}
