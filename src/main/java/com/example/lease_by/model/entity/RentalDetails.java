package com.example.lease_by.model.entity;

import com.example.lease_by.model.entity.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "rental")
@EqualsAndHashCode(exclude = "rental")
@Entity
@Table(name = "rental_details")
public class RentalDetails {

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
    @Enumerated(STRING)
    private LeaseTerm leaseTerm;

    @Column(name = "short_term")
    private Boolean shortTerm;

    @Column(name = "pet_friendly")
    private Boolean petFriendly;

    @Column(name = "year_built")
    private LocalDate yearBuilt;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "rental_id")
    private Rental rental;

    @Builder.Default
    @ElementCollection
    @CollectionTable(
            name = "category",
            joinColumns = @JoinColumn(name = "rental_details_id")
    )
    @Column(name = "name")
    @Enumerated(STRING)
    private Set<Category> categories = new HashSet<>();

    @Builder.Default
    @ElementCollection
    @CollectionTable(
            name = "utility",
            joinColumns = @JoinColumn(name = "rental_details_id")
    )
    @Column(name = "name")
    @Enumerated(STRING)
    private Set<Utility> utilities = new HashSet<>();

    public void setRental(Rental rental) {
        rental.setRentalDetails(this);
        this.rental = rental;
    }
}
