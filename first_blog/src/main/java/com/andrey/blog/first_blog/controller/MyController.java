package com.andrey.blog.first_blog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

    @GetMapping("/")
    public String mainPage (Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("title", "Главная страница");

        boolean isAdmin = user != null && user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);
        return "main-page";
    }

    @GetMapping("/info")
    public String info (Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("title", "About us");
        boolean isAdmin = user != null && user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);
        return "info";
    }




}