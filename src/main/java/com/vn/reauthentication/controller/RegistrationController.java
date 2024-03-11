package com.vn.reauthentication.controller;

import com.vn.reauthentication.entity.User;
import com.vn.reauthentication.entityDTO.RegisterRequest;
import com.vn.reauthentication.event.RegistrationCompleteEvent;
import com.vn.reauthentication.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class RegistrationController {
    private final IUserService iUserService;
    private final ApplicationEventPublisher publisher;
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new RegisterRequest());
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") RegisterRequest registerRequest){
        User user = iUserService.registerUser(registerRequest);
        publisher.publishEvent(new RegistrationCompleteEvent(user, ""));
        return "redirect:/register?success";
    }
}
