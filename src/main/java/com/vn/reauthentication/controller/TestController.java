package com.vn.reauthentication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class TestController {

    @RequestMapping("/test")
    public String test() {
        return "test";
    }
}
