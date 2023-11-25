package com.example.lease_by.service.impl;

import com.example.lease_by.dto.account.UserReadDto;
import com.example.lease_by.mapper.UserMapper;
import com.example.lease_by.model.entity.PasswordToken;
import com.example.lease_by.model.repository.PasswordTokenRepository;
import com.example.lease_by.service.PasswordTokenService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.lease_by.service.util.PasswordTokenUtil.createExpireDate;
import static com.example.lease_by.service.util.PasswordTokenUtil.createToken;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PasswordTokenServiceImpl implements PasswordTokenService {
    private final PasswordTokenRepository passwordTokenRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public void createPasswordToken(UserReadDto userReadDto) {
        var passwordToken = PasswordToken.builder()
                .user(userMapper.mapToUser(userReadDto))
                .token(createToken())
                .expireDate(createExpireDate())
                .build();

        passwordTokenRepository.save(passwordToken);
    }

    @Override
    public PasswordToken findPasswordTokenBy(String userEmail) {
        return passwordTokenRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Token with user's email: " + userEmail + " not found!"));
    }
}
