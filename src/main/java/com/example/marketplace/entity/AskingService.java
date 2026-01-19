package com.example.marketplace.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "askingService")
public class AskingService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "proposal", referencedColumnName = "id")
    private ServiceProposal proposal;

    @ManyToOne
    @JoinColumn(name = "client", referencedColumnName = "id")
    private Client client;
}
