package com.vn.reauthentication.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomeController {
    @GetMapping("/")
    public String home(Model model, HttpServletRequest request){
        model.addAttribute("request", request);
        return "home";
    }
    @GetMapping("/properties")
    public String properties(Model model, HttpServletRequest request){
        model.addAttribute("request", request);
        return "properties";
    }
    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request){
        model.addAttribute("request", request);
        return "login";
    }
    @GetMapping("/services")
    public String services(Model model, HttpServletRequest request){
        model.addAttribute("request", request);
        return "services";
    }
    @GetMapping("/wiki")
    public String wiki(Model model, HttpServletRequest request){
        model.addAttribute("request", request);
        return "wiki";
    }
    @GetMapping("/about")
    public String about(Model model, HttpServletRequest request){
        model.addAttribute("request", request);
        return "about";
    }
    @GetMapping("/property-single")
    public String property_single(Model model, HttpServletRequest request){
        model.addAttribute("request", request);
        return "property-single";
    }
    @GetMapping("/error")
    public String error(Model model, HttpServletRequest request){
        model.addAttribute("request", request);
        return "errorr";
    }
}
