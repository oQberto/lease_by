package com.example.lease_by.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"street", "city", "rentals"})
@EqualsAndHashCode(exclude = {"street", "city", "rentals"})
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

    @Builder.Default
    @OneToMany(mappedBy = "address")
    private List<Rental> rentals = new ArrayList<>();
}
