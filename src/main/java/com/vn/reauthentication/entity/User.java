package com.vn.reauthentication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Collection;
import com.vn.reauthentication.entity.Role;
import org.hibernate.annotations.NaturalId;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email
    @NaturalId(mutable = true)
    private String email;
    private String name;
    private String avatar;
    private String password;
    private LocalDate dob;
    @Size(min = 10, max = 13)
    private Long phoneNumber;
    private String city;
    private String district;
    private String ward;
    private Integer point;
    private Boolean status;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
            ,inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;
    private Boolean isEnable = false;

    public User(String email, String name, String password, LocalDate dob
            , Long phoneNumber, String city, String district, String ward
            , Collection<Role> roles) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.district = district;
        this.ward = ward;
        this.roles = roles;
    }
}
