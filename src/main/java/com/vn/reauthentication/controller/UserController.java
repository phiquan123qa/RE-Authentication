package com.vn.reauthentication.controller;

import com.vn.reauthentication.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService iUserService;

    @GetMapping
    public String getUser(Model model){
        model.addAttribute("users", iUserService.getAllUsers());
        return "users";
    }

}
