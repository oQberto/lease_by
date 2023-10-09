package com.example.lease_by.model.entity;


import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "user")
@EqualsAndHashCode(exclude = "user")
@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "avatar_img")
    private String avatar;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void setUser(User user) {
        user.setProfile(this);
        this.user = user;
    }
}
