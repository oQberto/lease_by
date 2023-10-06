package com.example.lease_by.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "house_no")
    private Integer houseNo;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "street_id")
    private Street street;

    @OneToMany(mappedBy = "address", fetch = LAZY)
    private List<Rental> rentals = new ArrayList<>();
}
