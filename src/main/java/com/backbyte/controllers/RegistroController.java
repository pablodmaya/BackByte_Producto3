package com.backbyte.controllers;

import com.backbyte.models.Usuario;
import com.backbyte.models.Usuario.TipoUsuario;
import com.backbyte.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistroController {

    private final UsuarioService usuarioService;

    public RegistroController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Muestra el formulario de registro
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    // Maneja el envío del formulario
    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute("usuario") Usuario usuario, Model model) {
        try {
            // Asignar el rol "user" por defecto
            usuario.setTipo_Usuario(TipoUsuario.user);

            // Registrar el usuario
            usuarioService.registrarUsuario(usuario);

            // Redirigir al login con mensaje opcional
            return "redirect:/login?registroExitoso";
        } catch (RuntimeException e) {
            // Manejo de errores (por ejemplo, usuario ya existe)
            model.addAttribute("error", e.getMessage());
            return "registro";
        }
    }
}

