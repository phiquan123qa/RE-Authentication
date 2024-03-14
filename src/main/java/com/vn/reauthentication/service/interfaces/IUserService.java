package com.vn.reauthentication.service.interfaces;

import com.vn.reauthentication.entity.User;
import com.vn.reauthentication.entityDTO.RegisterRequest;
import com.vn.reauthentication.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IUserService{
    List<User> getAllUsers();
    User registerUser(RegisterRequest registerRequest);
    Optional<User> findUserByEmail(String email);
    Optional<User> findById(Long id);
    void updateUser(String name, String email,
                    String avatar, LocalDate dob, Long phoneNumber,
                    String city, String district, String ward, Long id);
}
