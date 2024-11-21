package com.backbyte.service;

import com.backbyte.models.Usuario;
import com.backbyte.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Método para registrar un usuario
    public Usuario registrarUsuario(Usuario usuario) {
        if (usuarioRepository.findByNombreUsuario(usuario.getNombreUsuario()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya está en uso.");
        }

        // Encriptar la contraseña proporcionada
        usuario.setPasswordEncriptada(passwordEncoder.encode(usuario.getPassword()));

        // Guardar el usuario en la base de datos
        return usuarioRepository.save(usuario);
    }

    // Método de carga de usuarios existente para autenticación
    public Usuario cargarUsuarioPorNombre(String username) {
        return usuarioRepository.findByNombreUsuario(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
    }
}
