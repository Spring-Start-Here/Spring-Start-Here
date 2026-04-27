package com.example.ssh_ch9_ex3.controllers;

import com.example.ssh_ch9_ex3.processors.LoginProcessor;
import com.example.ssh_ch9_ex3.services.LoginCountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final LoginProcessor loginProcessor;
    private final LoginCountService loginCountService;

    public LoginController(LoginProcessor loginProcessor, LoginCountService loginCountService) {
        this.loginProcessor = loginProcessor;
        this.loginCountService = loginCountService;
    }

    @GetMapping("/")
    public String loginGet() {
        return "login.html";
    }

    @PostMapping("/")
    public String loginPost(
            @RequestParam String username,
            @RequestParam String password,
                    Model model
    ) {
        loginProcessor.setUsername(username);
        loginProcessor.setPassword(password);
        boolean loggedIn = loginProcessor.login();

        if (loggedIn) {
            return "redirect:/main";
        }

        model.addAttribute("message", "Login failed!");
        return "login.html";
    }

}
