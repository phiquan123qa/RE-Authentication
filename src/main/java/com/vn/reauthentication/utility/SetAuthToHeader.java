package com.vn.reauthentication.utility;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SetAuthToHeader {
    public static void setUserDetailsToModel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            model.addAttribute("username", username);
            model.addAttribute("authorities", authorities);

        }
    }
}
