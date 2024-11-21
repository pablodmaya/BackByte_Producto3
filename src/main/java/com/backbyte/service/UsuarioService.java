package com.backbyte.service;

import com.backbyte.models.Usuario;
import com.backbyte.models.Usuario.TipoUsuario;
import com.backbyte.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Metodo necesario para cumplir con UserDetailsService
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByNombreUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }

    // Metodo para registrar el usuario
    public Usuario registrarUsuario(Usuario usuario) {
        // Verificar que el nombre de usuario no esté en uso
        if (usuarioRepository.findByNombreUsuario(usuario.getNombreUsuario()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya está en uso.");
        }

        // Encriptar la contraseña proporcionada
        usuario.setPasswordEncriptada(passwordEncoder.encode(usuario.getPassword()));

        // Asignar el rol "user" por defecto si no tiene un rol asignado
        if (usuario.getTipo_Usuario() == null) {
            usuario.setTipo_Usuario(TipoUsuario.user);
        }

        // Guardar el usuario en la base de datos
        return usuarioRepository.save(usuario);
    }

    // Metodo de carga de usuarios existente para autenticación
    public Usuario cargarUsuarioPorNombre(String username) {
        return usuarioRepository.findByNombreUsuario(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
    }
}
