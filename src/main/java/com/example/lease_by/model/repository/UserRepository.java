package com.example.lease_by.model.repository;

import com.example.lease_by.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserById(Long id);

    @Query("""
            select u
            from User u
            join u.passwordToken t
            where t.token = :token
            """)
    Optional<User> findUserBy(String token);
}
