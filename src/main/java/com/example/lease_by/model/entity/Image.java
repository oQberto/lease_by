package com.example.lease_by.model.entity;


import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "rental")
@EqualsAndHashCode(exclude = "rental")
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "image_path", unique = true)
    private String imagePath;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "rental_id", unique = true)
    private Rental rental;
}
