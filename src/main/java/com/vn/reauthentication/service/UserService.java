package com.vn.reauthentication.service;

import com.vn.reauthentication.entity.RealEstate;
import com.vn.reauthentication.entity.Role;
import com.vn.reauthentication.entity.User;
import com.vn.reauthentication.entityDTO.RegisterRequest;
import com.vn.reauthentication.repository.UserRepository;
import com.vn.reauthentication.service.interfaces.IUserService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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
        Optional<User> existingUser = userRepository.findByEmail(registerRequest.getEmail());
        if (existingUser.isPresent()) {
            // User already exists, so you can resend the confirmation email
            return existingUser.get();
        } else {
            var user = new User(registerRequest.getEmail(),
                    registerRequest.getName(),
                    String.valueOf("default_avatar.png"),
                    passwordEncoder.encode(registerRequest.getPassword()),
                    registerRequest.getDob(),
                    registerRequest.getPhoneNumber(),
                    registerRequest.getCity(),
                    registerRequest.getDistrict(),
                    registerRequest.getWard(),
                    List.of(new Role("USER")));
            return userRepository.save(user);
        }
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void updateUser(String name, String email,
                           String avatar, LocalDate dob, Long phoneNumber,
                           String city, String district, String ward, Long id) {
        userRepository.update(name, email,
                 avatar, dob, phoneNumber,
                 city, district, ward, id);
    }

    @Override
    public User updateUserInfo(String avatar, String name, String email,
                               String phoneNumber, LocalDate dob, String description,
                               String city, String district, String ward) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setAvatar(avatar);
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setDob(dob);
        user.setDescription(description);
        user.setCity(city);
        user.setDistrict(district);
        user.setWard(ward);
        return userRepository.save(user);
    }

    @Override
    public Page<User> findUsersWithPaginationAndFilterAndSort(Integer pageNumber, Integer pageSize, String email, String city, String district, String ward, Boolean isEnable, String sort) {
        Specification<User> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (email != null && !email.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + email.toLowerCase() + "%"));
            }
            if (city != null && !city.isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("city")), city.toLowerCase()));
            }
            if (district != null && !district.isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("district")), district.toLowerCase()));
            }
            if (ward != null && !ward.isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("ward")), ward.toLowerCase()));
            }
            if (isEnable != null) {
                predicates.add(cb.equal(root.get("isEnable"), isEnable));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        Pageable pageable;
        if (sort != null && sort.equals("nameDesc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by("email").descending());
        } else if (sort != null && sort.equals("nameAsc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by("email").ascending());
        } else if (sort != null && sort.equals("idDesc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id").descending());
        } else if (sort != null && sort.equals("idAsc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id").ascending());
        } else{
            pageable = PageRequest.of(pageNumber, pageSize);
        }
        return userRepository.findAll(spec, pageable);
    }

    @Override
    public Boolean disableUser(Long id, Boolean isEnable) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setIsEnable(isEnable);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
