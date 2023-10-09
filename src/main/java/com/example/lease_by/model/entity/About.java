package com.example.lease_by.model.entity;

import com.example.lease_by.model.entity.enums.Furnished;
import com.example.lease_by.model.entity.enums.ParkingType;
import com.example.lease_by.model.entity.enums.PropertyType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "rental")
@EqualsAndHashCode(exclude = "rental")
@Entity
public class About {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "property_type")
    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;

    @Column(name = "parking_type")
    @Enumerated(EnumType.STRING)
    private ParkingType parkingType;

    @Column(name = "furnished")
    @Enumerated(EnumType.STRING)
    private Furnished furnished;

    @Column(name = "lease_term")
    private LocalDate leaseTerm;

    @Column(name = "short_term")
    private LocalDate shortTerm;

    @Column(name = "year_built")
    private LocalDate yearBuilt;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "rental_id")
    private Rental rental;
}
