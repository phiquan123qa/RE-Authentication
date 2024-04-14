package com.vn.reauthentication.controller;

import com.vn.reauthentication.entity.User;
import com.vn.reauthentication.entityDTO.APIResponse;
import com.vn.reauthentication.entityDTO.UserIsEnableRequest;
import com.vn.reauthentication.entityDTO.UserUpdateDTO;
import com.vn.reauthentication.service.interfaces.IFileStorageService;
import com.vn.reauthentication.service.interfaces.IUserService;
import com.vn.reauthentication.utility.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final IUserService userService;

    @PostMapping( value = "/update", consumes = {"application/json"})
    public ResponseEntity<?> updateUser(
            @RequestBody UserUpdateDTO updateDTO) {
        User updatedUser = userService.updateUserInfo(
                updateDTO.getAvatar(),
                updateDTO.getName(),
                updateDTO.getEmail(),
                updateDTO.getPhoneNumber(),
                updateDTO.getDob(),
                updateDTO.getDescription(),
                updateDTO.getCity(),
                updateDTO.getDistrict(),
                updateDTO.getWard()
        );
        return ResponseEntity.ok(updatedUser);
    }
    @PostMapping("/upload-avatar")
    public ResponseEntity<?> uploadAvatar(@RequestParam("avatar") MultipartFile file) {
        String avatarUrl = ImageUtil.saveImage(file, "./src/main/resources/static/images/avatars");
        return ResponseEntity.ok(Collections.singletonMap("avatarUrl", avatarUrl));
    }

    @GetMapping("/admin/findalluser")
    public APIResponse<Page<User>> getAllUsers(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "9") int pageSize,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "city", required = false) String city,
            @RequestParam(name = "district", required = false) String district,
            @RequestParam(name = "ward", required = false) String ward,
            @RequestParam(name = "isEnable", required = false) Boolean isEnable,
            @RequestParam(name = "sort", required = false) String sort) {
        Page<User> users = userService.findUsersWithPaginationAndFilterAndSort(
                offset, pageSize, email, city, district, ward, isEnable, sort);
        return new APIResponse<>(users.getSize(), users);
    }

    @PostMapping("/admin/disableuser")
    public ResponseEntity<?> disableUser(@RequestBody UserIsEnableRequest request) {
        Boolean isEnableCheck = userService.disableUser(request.getId(), request.getIsEnable());
        return ResponseEntity.ok(isEnableCheck);
    }
}

