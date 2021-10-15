package com.reesecake.simple_hsk.security;

import com.reesecake.simple_hsk.security.alternative.AppUser;
import com.reesecake.simple_hsk.security.alternative.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    AppUserRepository appUserRepository;

    @GetMapping("/{username}")
    public String userProfile(@PathVariable("username") String username, Model model) {
        AppUser user = appUserRepository.findUserByUsername(username);
        model.addAttribute("user", user);

        return "user-profile";
    }
}
