package com.example.lease_by.model.entity;

import com.example.lease_by.model.entity.enums.Amenities;
import com.example.lease_by.model.entity.enums.Feature;
import com.example.lease_by.model.entity.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"rentalDetails", "user", "address"})
@EqualsAndHashCode(exclude = {"rentalDetails", "user", "address"})
@Entity
public class Rental {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @Enumerated(STRING)
    private Status status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @Builder.Default
    @OneToMany(mappedBy = "rental")
    private Set<Image> images = new HashSet<>();

    @OneToOne(mappedBy = "rental", fetch = LAZY)
    private RentalDetails rentalDetails;

    @Builder.Default
    @ElementCollection
    @CollectionTable(
            name = "amenities",
            joinColumns = @JoinColumn(name = "rental_id")
    )
    @Column(name = "name")
    @Enumerated(STRING)
    private Set<Amenities> amenities = new HashSet<>();

    @Builder.Default
    @ElementCollection
    @CollectionTable(
            name = "feature",
            joinColumns = @JoinColumn(name = "rental_id")
    )
    @Column(name = "name")
    @Enumerated(STRING)
    private Set<Feature> features = new HashSet<>();
}
