package com.example.lease_by.service.impl;

import com.example.lease_by.dto.account.UserReadDto;
import com.example.lease_by.mapper.UserMapper;
import com.example.lease_by.model.entity.VerificationToken;
import com.example.lease_by.model.repository.VerificationTokenRepository;
import com.example.lease_by.service.VerificationTokenService;
import com.example.lease_by.service.util.VerifiedTokenUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.lease_by.service.util.VerifiedTokenUtil.createExpireDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VerificationTokenServiceImpl implements VerificationTokenService {
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public void createToken(UserReadDto userReadDto) {
        var verificationToken = VerificationToken.builder()
                .user(userMapper.mapToUser(userReadDto))
                .token(VerifiedTokenUtil.createToken())
                .expireDate(createExpireDate())
                .build();

        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public VerificationToken findVerificationTokenBy(String userEmail) {
        return verificationTokenRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Token with user's email: " + userEmail + " not found!"));
    }

    @Override
    @Transactional
    public UserReadDto removeUsedToken(UserReadDto userReadDto) {
        verificationTokenRepository
                .delete(verificationTokenRepository
                        .findByUserEmail(userReadDto.getEmail())
                        .orElseThrow(() -> new EntityNotFoundException("Token with user's email: " + userReadDto.getEmail() + " not found!")));

        return userReadDto;
    }
}
