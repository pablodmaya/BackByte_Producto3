package com.backbyte.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";  // Muestra la vista del formulario de login
    }

    @GetMapping("/admin/home")
    public String adminHome() {
        return "admin-home";  // Redirige a la página de inicio del admin
    }

    @GetMapping("/user/home")
    public String userHome() {
        return "user-home";  // Redirige a la página de inicio del usuario
    }
}
