package com.vn.reauthentication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

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
    private String phoneNumber;
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
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<RealEstate> realEstates;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LikedRealEstate> likedRealEstates;
    private String description;
    private Boolean isEnable = false;
    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Wiki> wikis;
    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<News> news;

    public User(String email, String name, String avatar, String password, LocalDate dob
            , String phoneNumber, String city, String district, String ward
            , Collection<Role> roles) {
        this.email = email;
        this.name = name;
        this.avatar = avatar;
        this.password = password;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.district = district;
        this.ward = ward;
        this.roles = roles;
    }
}
