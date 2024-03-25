package com.vn.reauthentication.entityDTO;

import com.vn.reauthentication.entity.Role;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RegisterRequest {
    private String email;
    private String name;
    private String password;
    private LocalDate dob;
    private String phoneNumber;
    private String city;
    private String district;
    private String ward;
    private List<Role> roles;
}
