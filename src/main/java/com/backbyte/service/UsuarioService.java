package com.backbyte.service;

import com.backbyte.models.Usuario;
import com.backbyte.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Cargar el usuario por nombre de usuario (Spring Security)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNombreUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return new org.springframework.security.core.userdetails.User(
                usuario.getNombreUsuario(),
                usuario.getPasswordEncriptada(), // Contraseña encriptada
                usuario.getAuthorities()
        );
    }

    // Obtener un usuario por su ID
    public Optional<Usuario> getUsuarioById(Integer id) {
        return usuarioRepository.findById(Integer.valueOf(id));
    }

    // Obtener un usuario por el id del cliente
    public Usuario getUsuarioPorClienteId(Integer clienteId) {
        return usuarioRepository.findByClienteId(clienteId);
    }

    // Agregar un nuevo usuario
    public Usuario addUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Actualizar un usuario existente
    public Usuario updateUsuario(Usuario usuarioDetalles) {
        return usuarioRepository.save(usuarioDetalles);
    }

    // Eliminar un usuario por su ID
    public void deleteUsuario(Integer id) {
        if (usuarioRepository.existsById(Integer.valueOf(id))) {
            usuarioRepository.deleteById(Integer.valueOf(id));
        } else {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
    }
}
