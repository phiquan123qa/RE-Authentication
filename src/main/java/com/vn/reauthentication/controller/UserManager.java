package com.vn.reauthentication.controller;

import com.vn.reauthentication.utility.SetAuthToHeader;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mgtre")
@RequiredArgsConstructor
public class UserManager {
    @GetMapping("/add")
    public String add(Model model, HttpServletRequest request){
        SetAuthToHeader.setUserDetailsToModel(model);
        model.addAttribute("requestURI", request.getRequestURI());
        return "mgtre/add";
    }
    @GetMapping("/list")
    public String list(Model model, HttpServletRequest request){
        SetAuthToHeader.setUserDetailsToModel(model);
        model.addAttribute("title", request.getParameter("title"));
        model.addAttribute("city", request.getParameter("city"));
        model.addAttribute("district", request.getParameter("district"));
        model.addAttribute("ward", request.getParameter("ward"));
        model.addAttribute("type", request.getParameter("type"));
        model.addAttribute("sort", request.getParameter("sort"));
        model.addAttribute("requestURI", request.getRequestURI());
        return "mgtre/list";
    }
    @GetMapping("/likelist")
    public String his(Model model, HttpServletRequest request){
        SetAuthToHeader.setUserDetailsToModel(model);
        model.addAttribute("requestURI", request.getRequestURI());
        return "mgtre/likelist";
    }
}
