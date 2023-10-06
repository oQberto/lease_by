package com.example.lease_by.model.entity;

import com.example.lease_by.model.entity.enums.Furnished;
import com.example.lease_by.model.entity.enums.ParkingType;
import com.example.lease_by.model.entity.enums.PropertyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @OneToOne
    @JoinColumn(name = "rental_id")
    private Rental rental;
}
