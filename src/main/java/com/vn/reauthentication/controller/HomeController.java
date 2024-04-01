package com.vn.reauthentication.controller;

import com.vn.reauthentication.entity.RealEstate;
import com.vn.reauthentication.entity.User;
import com.vn.reauthentication.service.interfaces.IRealEstateService;
import com.vn.reauthentication.service.interfaces.IUserService;
import com.vn.reauthentication.utility.SetAuthToHeader;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class HomeController {
    private final IRealEstateService realEstateService;
    private final IUserService userService;
    @GetMapping("/")
    public String home(Model model, HttpServletRequest request){
        SetAuthToHeader.setUserDetailsToModel(model);
        model.addAttribute("requestURI", request.getRequestURI());
        return "home";
    }
    @GetMapping("/properties")
    public String properties(Model model,
                             HttpServletRequest request) throws IOException {
        SetAuthToHeader.setUserDetailsToModel(model);
        model.addAttribute("requestURI", request.getRequestURI());
        return "properties";
    }
    @PostMapping("/properties")
    public String propertiesPost(String title,
                                 String type,
                                 String city,
                                 String district,
                                 String ward,
                                 Model model,
                                 HttpServletRequest request,
                                 RedirectAttributes redirectAttributes) throws IOException {
        SetAuthToHeader.setUserDetailsToModel(model);
        redirectAttributes.addFlashAttribute("searchTitle", title);
        redirectAttributes.addFlashAttribute("searchType", type);
        redirectAttributes.addFlashAttribute("searchCity", city);
        redirectAttributes.addFlashAttribute("searchDistrict", district);
        redirectAttributes.addFlashAttribute("searchWard", ward);
        return "redirect:/properties";
    }
    @GetMapping("/property/{id}")
    public String properties_single(Model model,
                                    HttpServletRequest request,
                                    @PathVariable Long id) {
        SetAuthToHeader.setUserDetailsToModel(model);
        model.addAttribute("requestURI", request.getRequestURI());
        RealEstate realEstate = realEstateService.findRealEstateById(id).orElseThrow();
        model.addAttribute("realEstate", realEstate);
        return "property-single";
    }
    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request){
        model.addAttribute("requestURI", request.getRequestURI());
        return "login";
    }
    @GetMapping("/services")
    public String services(Model model, HttpServletRequest request){
        SetAuthToHeader.setUserDetailsToModel(model);
        model.addAttribute("requestURI", request.getRequestURI());
        return "services";
    }
    @GetMapping("/wiki")
    public String wiki(Model model, HttpServletRequest request){
        SetAuthToHeader.setUserDetailsToModel(model);
        model.addAttribute("requestURI", request.getRequestURI());
        return "wiki";
    }
    @GetMapping("/about")
    public String about(Model model, HttpServletRequest request){
        SetAuthToHeader.setUserDetailsToModel(model);
        model.addAttribute("requestURI", request.getRequestURI());
        return "about";
    }
    @GetMapping("/property-single")
    public String property_single(Model model, HttpServletRequest request){
        SetAuthToHeader.setUserDetailsToModel(model);
        model.addAttribute("requestURI", request.getRequestURI());
        return "property-single";
    }

    @GetMapping("/user_info")
    public String user_info(Model model, HttpServletRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user;
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            user = userService.findUserByEmail(username).orElseThrow();
            model.addAttribute("user", user);
            model.addAttribute("username", username);
        }
        model.addAttribute("requestURI", request.getRequestURI());
        return "user_info";
    }

//    @GetMapping("/mgtre")
//    public String mgtre(Model model, HttpServletRequest request){
//        SetAuthToHeader.setUserDetailsToModel(model);
//        model.addAttribute("requestURI", request.getRequestURI());
//        return "mgtre";
//    }

    @GetMapping("/error")
    public String error(Model model, HttpServletRequest request){
        model.addAttribute("requestURI", request.getRequestURI());
        return "error";
    }
}
