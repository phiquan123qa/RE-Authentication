package com.vn.reauthentication.service.interfaces;

import com.vn.reauthentication.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface IPasswordResetTokenService {
    String validatePasswordResetToken(String theToken);

    Optional<User> findUserByPasswordResetToken(String theToken);

    void resetPassword(User theUser, String password);

    void createPasswordResetTokenForUser(User user, String passwordResetToken);
}
