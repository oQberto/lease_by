package com.example.lease_by.model.entity;

import com.example.lease_by.model.entity.enums.Amenities;
import com.example.lease_by.model.entity.enums.Feature;
import com.example.lease_by.model.entity.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

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
@ToString(
        exclude = {
                "rentalDetails",
                "user",
                "address"
        })
@EqualsAndHashCode(
        exclude = {
                "rentalDetails",
                "user",
                "address"
        },
        callSuper = false)
@Entity
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Rental extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "description")
    private String description;

    @Column(name = "intro_image")
    private String introImage;

    @Column(name = "status")
    @Enumerated(STRING)
    private Status status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne(mappedBy = "rental", fetch = LAZY)
    private RentalDetails rentalDetails;

    @Builder.Default
    @ElementCollection
    @CollectionTable(
            name = "image",
            joinColumns = @JoinColumn(name = "rental_id")
    )
    @Column(name = "path")
    private Set<String> images = new HashSet<>();

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
