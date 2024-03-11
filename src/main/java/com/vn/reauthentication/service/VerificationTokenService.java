package com.vn.reauthentication.service;

import com.vn.reauthentication.entity.User;
import com.vn.reauthentication.entity.VerificationToken;
import com.vn.reauthentication.repository.UserRepository;
import com.vn.reauthentication.repository.VerificationTokenRepository;
import com.vn.reauthentication.service.interfaces.IVerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class VerificationTokenService implements IVerificationTokenService {
    private final VerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;
    @Override
    public String validateToken(String token) {
        Optional<VerificationToken> tokenFound = tokenRepository.findByToken(token);
        if(tokenFound.isEmpty()){
            return "invalid";
        }
        User user = tokenFound.get().getUser();

        if(tokenFound.get().getExpirationTime().isBefore(LocalDate.now())){
            return "expired";
        }
        user.setIsEnable(true);
        userRepository.save(user);
        return "valid";
    }

    @Override
    public void saveVerificationTokenForUser(User user, String token) {
        var verificationToken = new VerificationToken(token, user);
        tokenRepository.save(verificationToken);
    }

    @Override
    public Optional<VerificationToken> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }
}
