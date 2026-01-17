package com.example.marketplace.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "serviceProposal")
public class ServiceProposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "service", referencedColumnName = "id")
    private Service service;

    @ManyToOne
    @JoinColumn(name = "provider", referencedColumnName = "id")
    private Provider provider;


}
