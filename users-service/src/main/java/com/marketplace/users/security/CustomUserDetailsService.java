package com.marketplace.users.security;

import com.marketplace.users.entity.Usuario;
import com.marketplace.users.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository
            .findByCorreo(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return User.builder()
            .username(usuario.getCorreo())
            .password(usuario.getContrasena())        // ya está hasheada con BCrypt
            .roles(usuario.getTipo().name().toUpperCase()) // p.ej. "CLIENTE" o "CREADOR"
            .build();
    }
}
