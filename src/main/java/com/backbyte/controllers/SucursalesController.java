package com.backbyte.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SucursalesController {

    @GetMapping("/madrid")
    public String madrid() {
        return "city/madrid";  // Retorna la vista "madrid.html"
    }

    // Otros mapeos para diferentes sucursales
    @GetMapping("/barcelona")
    public String barcelona() {
        return "city/barcelona";  // Retorna la vista "barcelona.html"
    }

    // Otros mapeos para diferentes sucursales
    @GetMapping("/bilbao")
    public String bilbao() {
        return "city/bilbao";  // Retorna la vista "barcelona.html"
    }

    // Otros mapeos para diferentes sucursales
    @GetMapping("/sevilla")
    public String sevilla() {
        return "city/sevilla";  // Retorna la vista "barcelona.html"
    }

    // Otros mapeos para diferentes sucursales
    @GetMapping("/valencia")
    public String valencia() {
        return "city/valencia";  // Retorna la vista "barcelona.html"
    }
}