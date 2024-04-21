package com.vn.reauthentication.service;

import com.vn.reauthentication.entity.PasswordResetToken;
import com.vn.reauthentication.entity.User;
import com.vn.reauthentication.repository.PasswordResetTokenRepository;
import com.vn.reauthentication.repository.UserRepository;
import com.vn.reauthentication.service.interfaces.IPasswordResetTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenService implements IPasswordResetTokenService {
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public String validatePasswordResetToken(String theToken) {
        Optional<PasswordResetToken> passwordResetToken = passwordResetTokenRepository.findByToken(theToken);
        if (passwordResetToken.isEmpty()){
            return "invalid";
        }
        if ((passwordResetToken.get().getExpirationTime().isBefore(LocalDate.now()))) {
            return "expired";
        }
        return "valid";
    }

    @Override
    public Optional<User> findUserByPasswordResetToken(String theToken) {
        Optional<PasswordResetToken> tokenEntityOptional  = passwordResetTokenRepository.findByToken(theToken);
        return tokenEntityOptional.map(PasswordResetToken::getUser);
    }

    @Override
    public void resetPassword(User theUser, String newPassword) {
        theUser.setPassword(passwordEncoder.encode(newPassword));
        theUser.setIsEnable(true);
        userRepository.save(theUser);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String passwordResetToken) {
        PasswordResetToken resetToken = new PasswordResetToken(passwordResetToken, user);
        passwordResetTokenRepository.save(resetToken);
    }
}
