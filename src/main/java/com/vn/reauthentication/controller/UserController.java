package com.vn.reauthentication.controller;

import com.vn.reauthentication.entity.User;
import com.vn.reauthentication.entityDTO.UserUpdateDTO;
import com.vn.reauthentication.service.interfaces.IFileStorageService;
import com.vn.reauthentication.service.interfaces.IUserService;
import com.vn.reauthentication.utility.ImageUtil;
import lombok.RequiredArgsConstructor;
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



}

