package com.vn.reauthentication.service;

import com.vn.reauthentication.entity.Role;
import com.vn.reauthentication.entity.User;
import com.vn.reauthentication.entityDTO.RegisterRequest;
import com.vn.reauthentication.repository.UserRepository;
import com.vn.reauthentication.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(RegisterRequest registerRequest) {
        var user = new User(registerRequest.getEmail(),
                registerRequest.getName(),
                passwordEncoder.encode(registerRequest.getPassword()),
                registerRequest.getDob(),
                registerRequest.getPhoneNumber(),
                registerRequest.getCity(),
                registerRequest.getDistrict(),
                registerRequest.getWard(),
                Arrays.asList(new Role("USER")));
        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
