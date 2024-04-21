package com.vn.reauthentication.controller;

import com.vn.reauthentication.utility.SetAuthToHeader;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    @GetMapping("/home")
    public String home(Model model, HttpServletRequest request) {
        SetAuthToHeader.setUserDetailsToModel(model);
        model.addAttribute("requestURI", request.getRequestURI());
        return "admin/home";
    }

    @GetMapping("/all_re")
    public String all_re(Model model, HttpServletRequest request) {
        SetAuthToHeader.setUserDetailsToModel(model);
        model.addAttribute("title", request.getParameter("title"));
        model.addAttribute("city", request.getParameter("city"));
        model.addAttribute("district", request.getParameter("district"));
        model.addAttribute("ward", request.getParameter("ward"));
        model.addAttribute("type", request.getParameter("type"));
        model.addAttribute("sort", request.getParameter("sort"));
        model.addAttribute("status", request.getParameter("status"));
        model.addAttribute("minArea", request.getParameter("minArea"));
        model.addAttribute("maxArea", request.getParameter("maxArea"));
        model.addAttribute("minPrice", request.getParameter("minPrice"));
        model.addAttribute("maxPrice", request.getParameter("maxPrice"));
        model.addAttribute("requestURI", request.getRequestURI());
        return "admin/all_properties";
    }
    @GetMapping("/accept_post_re")
    public String accept_post_re(Model model, HttpServletRequest request) {
        SetAuthToHeader.setUserDetailsToModel(model);
        model.addAttribute("requestURI", request.getRequestURI());
        return "admin/accept_post_re";
    }
    @GetMapping("/list_recommend_re")
    public String list_recommend_re(Model model, HttpServletRequest request) {
        SetAuthToHeader.setUserDetailsToModel(model);
        model.addAttribute("requestURI", request.getRequestURI());
        return "admin/list_recom_re";
    }
    @GetMapping("/all_acc")
    public String all_acc(Model model, HttpServletRequest request) {
        SetAuthToHeader.setUserDetailsToModel(model);
        model.addAttribute("email", request.getParameter("email"));
        model.addAttribute("role", request.getParameter("role"));
        model.addAttribute("isEnable", request.getParameter("isEnable"));
        model.addAttribute("sort", request.getParameter("sort"));
        model.addAttribute("requestURI", request.getRequestURI());
        return "admin/manager_user";
    }
    @GetMapping("/report_re")
    public String report_re(Model model, HttpServletRequest request) {
        model.addAttribute("status", request.getParameter("status"));
        model.addAttribute("sort", request.getParameter("sort"));
        SetAuthToHeader.setUserDetailsToModel(model);
        model.addAttribute("requestURI", request.getRequestURI());
        return "admin/report_re";
    }
    @GetMapping("/report_acc")
    public String report_acc(Model model, HttpServletRequest request) {
        SetAuthToHeader.setUserDetailsToModel(model);
        model.addAttribute("status", request.getParameter("status"));
        model.addAttribute("sort", request.getParameter("sort"));
        model.addAttribute("requestURI", request.getRequestURI());
        return "admin/report_user";
    }
    @GetMapping("/wiki")
    public String wiki(Model model, HttpServletRequest request) {
        SetAuthToHeader.setUserDetailsToModel(model);
        model.addAttribute("title", request.getParameter("title"));
        model.addAttribute("tag", request.getParameter("tag"));
        model.addAttribute("sort", request.getParameter("sort"));
        model.addAttribute("isPublished", request.getParameter("isPublished"));
        model.addAttribute("requestURI", request.getRequestURI());
        return "admin/manager_wiki";
    }


}
