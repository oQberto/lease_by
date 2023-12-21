package com.example.lease_by.model.entity;

import com.example.lease_by.model.entity.enums.Role;
import com.example.lease_by.model.entity.enums.UserNetworkStatus;
import com.example.lease_by.model.entity.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.envers.Audited;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Builder
@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = false)
@Audited
public class User extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "role")
    @Enumerated(STRING)
    private Role role;

    @Column(name = "status")
    @Enumerated(STRING)
    private UserStatus status;

    @Column(name = "network_status")
    @Enumerated(STRING)
    private UserNetworkStatus networkStatus;

    @OneToOne(mappedBy = "user", fetch = LAZY, cascade = ALL)
    private Profile profile;

    @OneToOne(mappedBy = "user", fetch = LAZY)
    private VerificationToken verificationToken;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = ALL)
    private List<Rental> rentals = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "sender", cascade = ALL)
    private List<ChatRoom> senderChats = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "recipient", cascade = ALL)
    private List<ChatRoom> recipientChats = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "sender", cascade = ALL)
    private List<ChatMessage> senderMessages = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "recipient", cascade = ALL)
    private List<ChatMessage> recipientMessages = new ArrayList<>();
}
