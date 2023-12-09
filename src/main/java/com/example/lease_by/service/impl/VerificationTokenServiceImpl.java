package com.example.lease_by.service.impl;

import com.example.lease_by.dto.account.UserReadDto;
import com.example.lease_by.mapper.UserMapper;
import com.example.lease_by.model.entity.VerificationToken;
import com.example.lease_by.model.repository.VerificationTokenRepository;
import com.example.lease_by.service.VerificationTokenService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.lease_by.service.util.PasswordTokenUtil.createExpireDate;
import static com.example.lease_by.service.util.PasswordTokenUtil.createToken;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VerificationTokenServiceImpl implements VerificationTokenService {
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public void createPasswordToken(UserReadDto userReadDto) {
        var passwordToken = VerificationToken.builder()
                .user(userMapper.mapToUser(userReadDto))
                .token(createToken())
                .expireDate(createExpireDate())
                .build();

        verificationTokenRepository.save(passwordToken);
    }

    @Override
    public VerificationToken findPasswordTokenBy(String userEmail) {
        return verificationTokenRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Token with user's email: " + userEmail + " not found!"));
    }

    @Override
    @Transactional
    public void removeUsedToken(String userEmail) {
        VerificationToken passwordToken = verificationTokenRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Token with user's email: " + userEmail + " not found!"));
        verificationTokenRepository.delete(passwordToken);
    }
}
