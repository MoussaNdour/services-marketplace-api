package com.example.marketplace.entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "service")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name= "createdAt")
    private Date createdAt=new Date();

    @Column(name = "mark", precision = 3, scale = 2)
    private BigDecimal mark;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name= "category",referencedColumnName = "id")
    private Category category;

    @OneToOne
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

}
