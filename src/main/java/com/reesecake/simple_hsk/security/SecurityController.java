package com.reesecake.simple_hsk.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class SecurityController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
