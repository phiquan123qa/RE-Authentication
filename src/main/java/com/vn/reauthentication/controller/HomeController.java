package com.vn.reauthentication.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping
public class HomeController {
    @GetMapping("/")
    public String home(Model model, HttpServletRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName(); // Get the username from the authentication object
            model.addAttribute("username", username);
        }
        model.addAttribute("requestURI", request.getRequestURI());
        return "home";
    }
    @GetMapping("/properties")
    public String properties(@RequestParam(value = "title", required = false) String title,
                             @RequestParam(value = "type", required = false) String type,
                             @RequestParam(value = "city", required = false) String city,
                             @RequestParam(value = "district", required = false) String district,
                             @RequestParam(value = "ward", required = false) String ward,
                             Model model,
                             HttpServletRequest request) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName(); // Get the username from the authentication object
            model.addAttribute("username", username);
        }
        if (title != null && !title.isEmpty()) {
            model.addAttribute("searchTitle", title);
        }
        if (type != null && !type.isEmpty()) {
            model.addAttribute("searchType", type);
        }
        if (city != null && !city.isEmpty()) {
            model.addAttribute("selectedCity", city);
        }
        if (district != null && !district.isEmpty()) {
            model.addAttribute("selectedDistrict", district);
        }
        if (ward != null && !ward.isEmpty()) {
            model.addAttribute("selectedWard", ward);
        }

        model.addAttribute("requestURI", request.getRequestURI());
        return "properties";
    }
    @PostMapping("/properties")
    public String propertiesPost(@RequestParam("title") String title,
                                 @RequestParam("type") String type,
                                 @RequestParam("city") String city,
                                 @RequestParam("district") String district,
                                 @RequestParam("ward") String ward,
                                 Model model,
                                 HttpServletRequest request) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            model.addAttribute("username", username);
        }
        model.addAttribute("searchTitle", title);
        model.addAttribute("searchType", type);
        model.addAttribute("searchCity", city);
        model.addAttribute("searchDistrict", district);
        model.addAttribute("searchWard", ward);
        model.addAttribute("requestURI", request.getRequestURI());
        return "redirect:/properties";
    }
    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request){
        model.addAttribute("requestURI", request.getRequestURI());
        return "login";
    }
    @GetMapping("/services")
    public String services(Model model, HttpServletRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName(); // Get the username from the authentication object
            model.addAttribute("username", username);
        }
        model.addAttribute("requestURI", request.getRequestURI());
        return "services";
    }
    @GetMapping("/wiki")
    public String wiki(Model model, HttpServletRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName(); // Get the username from the authentication object
            model.addAttribute("username", username);
        }
        model.addAttribute("requestURI", request.getRequestURI());
        return "wiki";
    }
    @GetMapping("/about")
    public String about(Model model, HttpServletRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName(); // Get the username from the authentication object
            model.addAttribute("username", username);
        }
        model.addAttribute("requestURI", request.getRequestURI());
        return "about";
    }
    @GetMapping("/property-single")
    public String property_single(Model model, HttpServletRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName(); // Get the username from the authentication object
            model.addAttribute("username", username);
        }
        model.addAttribute("requestURI", request.getRequestURI());
        return "property-single";
    }
    @GetMapping("/error")
    public String error(Model model, HttpServletRequest request){
        model.addAttribute("requestURI", request.getRequestURI());
        return "error";
    }
}
