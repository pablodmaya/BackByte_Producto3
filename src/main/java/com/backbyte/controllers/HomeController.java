package com.backbyte.controllers;

import com.backbyte.models.Ciudad;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Ruta mapeada a la raíz "/"
    @GetMapping("/")
    public String home(Model model) {
        // Aquí puedes agregar atributos al modelo si es necesario.
        model.addAttribute("ciudades", Ciudad.values()); // Pasamos las opciones del enum al modelo
        return "home";  // Esta es la vista home.html
    }
}