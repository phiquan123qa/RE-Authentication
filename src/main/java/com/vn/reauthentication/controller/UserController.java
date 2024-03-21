package com.vn.reauthentication.controller;

import com.vn.reauthentication.entity.User;
import com.vn.reauthentication.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class UserController {
    private final IUserService iUserService;

    @GetMapping("/users")
    public String getUser(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        // Pass authorities to the model
        model.addAttribute("authorities", authorities);
        List<User> users = iUserService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

}

