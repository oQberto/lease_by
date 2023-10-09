package com.example.lease_by.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"street", "city"})
@EqualsAndHashCode(exclude = {"street", "city"})
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "house_no")
    private Integer houseNo;

    @ManyToOne()
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne()
    @JoinColumn(name = "street_id")
    private Street street;

    @Builder.Default
    @OneToMany(mappedBy = "address")
    private List<Rental> rentals = new ArrayList<>();
}
