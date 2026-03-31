package com.example.marketplace.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "askingService")
public class Asking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "proposal", referencedColumnName = "id")
    private ServiceProposal proposal;

    @Column(name = "status")
    private String status;

    @Column(name = "createdAt", columnDefinition = "TIME")
    private Date createdAt;

    @Column(name = "scheduledAt", columnDefinition = "TIME")
    private Date scheduledAt;

    @ManyToOne
    @JoinColumn(name = "client", referencedColumnName = "id")
    private Client client;
}
