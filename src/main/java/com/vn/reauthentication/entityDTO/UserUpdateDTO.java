package com.vn.reauthentication.entityDTO;

import lombok.Data;

import java.time.LocalDate;
@Data
public class UserUpdateDTO {
    private String avatar;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate dob;
    private String description;
    private String city;
    private String district;
    private String ward;
}
