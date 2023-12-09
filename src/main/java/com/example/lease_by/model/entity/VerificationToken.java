package com.example.lease_by.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "verification_token")
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "token")
    private String token;

    @Column(name = "expire_date")
    private LocalDate expireDate;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
