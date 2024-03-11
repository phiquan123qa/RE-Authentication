package com.vn.reauthentication.service.interfaces;

import com.vn.reauthentication.entity.User;
import com.vn.reauthentication.entityDTO.RegisterRequest;
import com.vn.reauthentication.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public interface IUserService{
    List<User> getAllUsers();
    User registerUser(RegisterRequest registerRequest);
    User findUserByEmail(String email);

}
