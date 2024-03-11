package com.vn.reauthentication.service.interfaces;

import com.vn.reauthentication.entity.User;
import com.vn.reauthentication.entity.VerificationToken;

import java.util.Optional;

public interface IVerificationTokenService {
    String validateToken(String token);
    void saveVerificationTokenForUser(User user,String token);
    Optional<VerificationToken> findByToken(String token);
}
