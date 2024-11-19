package com.backbyte.service;

import com.backbyte.repository.UsuarioRepository;
import com.backbyte.models.Usuario;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Método para cargar el usuario por nombre de usuario
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNombreUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Compara la contraseña proporcionada (en texto claro) con la contraseña encriptada de la base de datos
        // Aquí se hace una validación implícita cuando Spring Security la usa
        return new org.springframework.security.core.userdetails.User(
                usuario.getNombreUsuario(),
                usuario.getPasswordEncriptada(),  // Debe ser la contraseña encriptada
                usuario.getAuthorities()
        );
    }
}