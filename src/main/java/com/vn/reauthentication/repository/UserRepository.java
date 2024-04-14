package com.vn.reauthentication.repository;

import com.vn.reauthentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);
    @Modifying
    @Query("UPDATE User u SET u.name = :name, u.email = :email, u.avatar = :avatar, u.dob = :dob, u.phoneNumber = :phoneNumber, u.city = :city, u.district = :district, u.ward = :ward WHERE u.id = :id")
    void update(String name, String email, String avatar, LocalDate dob, Long phoneNumber, String city, String district, String ward, Long id);

}
