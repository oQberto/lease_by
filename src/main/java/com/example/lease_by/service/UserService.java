package com.example.lease_by.service;

import com.example.lease_by.dto.UserCreateDto;
import com.example.lease_by.mapper.UserMapper;
import com.example.lease_by.model.entity.Profile;
import com.example.lease_by.model.entity.User;
import com.example.lease_by.model.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ProfileService profileService;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(userRepository.findUserById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found!")));
    }

    @Transactional
    public void createUser(UserCreateDto dto) {
        User user = userMapper.mapToUser(dto);
        userRepository.saveAndFlush(user);

        Profile profile  = new Profile();
        profile.setUser(user);
        profileService.createProfile(profile);
    }
}
