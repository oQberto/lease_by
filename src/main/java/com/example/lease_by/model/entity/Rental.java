package com.example.lease_by.model.entity;

import com.example.lease_by.model.entity.enums.Amenities;
import com.example.lease_by.model.entity.enums.Feature;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Rental {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "rental", fetch = LAZY)
    private Set<Image> images = new HashSet<>();

    @OneToOne(mappedBy = "rental")
    private About about;

    @ElementCollection
    @CollectionTable(
            name = "amenities",
            joinColumns = @JoinColumn(name = "rental_id")
    )
    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private Set<Amenities> amenities = new HashSet<>();

    @ElementCollection
    @CollectionTable(
            name = "feature",
            joinColumns = @JoinColumn(name = "rental_id")
    )
    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private Set<Feature> features = new HashSet<>();
}
