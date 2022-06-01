package com.example.springsecurityintro;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/general")
    public String general() {
        return "general";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}
