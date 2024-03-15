package com.vn.reauthentication.controller;

import com.vn.reauthentication.entity.User;
import com.vn.reauthentication.entity.VerificationToken;
import com.vn.reauthentication.entityDTO.RegisterRequest;
import com.vn.reauthentication.event.RegistrationCompleteEvent;
import com.vn.reauthentication.event.listener.RegistrationCompleteEventListener;
import com.vn.reauthentication.service.VerificationTokenService;
import com.vn.reauthentication.service.interfaces.IPasswordResetTokenService;
import com.vn.reauthentication.service.interfaces.IUserService;
import com.vn.reauthentication.utility.UrlUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class RegistrationController {
    private final IUserService userService;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenService tokenService;
    private final IPasswordResetTokenService passwordResetTokenService;
    private final RegistrationCompleteEventListener eventListener;
    @GetMapping("/register")
    public String showRegistrationForm(Model model, HttpServletRequest request){
        model.addAttribute("request", request);
        model.addAttribute("user", new RegisterRequest());
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") RegisterRequest registerRequest, HttpServletRequest request){
        User user = userService.registerUser(registerRequest);
        publisher.publishEvent(new RegistrationCompleteEvent(user, UrlUtil.getApplicationUrl(request)));
        return "redirect:/register?success";
    }
    @GetMapping("/verify_email")
    public String verifyEmail(@RequestParam("token") String token) {
        Optional<VerificationToken> theToken = tokenService.findByToken(token);
        if (theToken.isPresent() && theToken.get().getUser().getIsEnable()) {
            return "redirect:/login?verified";
        }
        String verificationResult = tokenService.validateToken(token);
        switch (verificationResult.toLowerCase()) {
            case "expired":
                return "redirect:/error?expired";
            case "valid":
                return "redirect:/login?valid";
            default:
                return "redirect:/error?invalid";
        }
    }
    @GetMapping("/forgot_password")
    public String forgotPasswordForm(Model model, HttpServletRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName(); // Get the username from the authentication object
            model.addAttribute("username", username);
        }
        model.addAttribute("request", request);
        return "forgot-password-form";
    }
    @PostMapping("/forgot_password")
    public String resetPasswordRequest(HttpServletRequest request, Model model){
        String email = request.getParameter("email");
        Optional<User> user= userService.findUserByEmail(email);
        if (user.isEmpty()){
            return  "redirect:/forgot_password?not_found";
        }
        String passwordResetToken = UUID.randomUUID().toString();
        passwordResetTokenService.createPasswordResetTokenForUser(user.get(), passwordResetToken);
        String url = UrlUtil.getApplicationUrl(request)+"/reset_password?token="+passwordResetToken;
        try {
            eventListener.sendPasswordResetVerificationEmail(url, user.get());
        } catch (MessagingException | UnsupportedEncodingException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/forgot_password?success";
    }
    @GetMapping("/reset_password")
    public String passwordResetForm(@RequestParam("token") String token, Model model, HttpServletRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName(); // Get the username from the authentication object
            model.addAttribute("username", username);
        }
        model.addAttribute("request", request);
        model.addAttribute("token", token);
        return "password-reset-form";
    }
    @PostMapping("/reset_password")
    public String resetPassword(HttpServletRequest request){
        String theToken = request.getParameter("token");
        String password = request.getParameter("password");
        String tokenVerificationResult = passwordResetTokenService.validatePasswordResetToken(theToken);
        if (!tokenVerificationResult.equalsIgnoreCase("valid")){
            return "redirect:/error?invalid_token";
        }
        Optional<User> theUser = passwordResetTokenService.findUserByPasswordResetToken(theToken);
        if (theUser.isPresent()){
            passwordResetTokenService.resetPassword(theUser.get(), password);
            return "redirect:/login?reset_success";
        }
        return "redirect:/error?not_found";
    }

}
