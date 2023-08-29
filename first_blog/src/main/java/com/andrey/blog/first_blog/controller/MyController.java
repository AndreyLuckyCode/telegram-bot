package com.andrey.blog.first_blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

    @GetMapping("/")
    public String mainPage (Model model) {
        model.addAttribute("title", "Главная страница");
        return "main-page";
    }

    @GetMapping("/info")
    public String info (Model model) {
        model.addAttribute("title", "About us");
        return "info";
    }




}