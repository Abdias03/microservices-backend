package com.marketplace.users.security;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.marketplace.users.entity.TipoUsuario;
import com.marketplace.users.entity.Usuario;
import com.marketplace.users.repository.UsuarioRepository;

@Component
public class AdminInitializer {

    @Value("${admin.user.email}")
    private String adminEmail;

    @Value("${admin.user.password}")
    private String adminPassword;

    @Value("${admin.user.nombre}")
    private String adminNombre;

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initAdminUser() {
        if (usuarioRepository.findByCorreo(adminEmail).isEmpty()) {
            Usuario admin = new Usuario();
            admin.setCorreo(adminEmail);
            admin.setContrasena(passwordEncoder.encode(adminPassword));
            admin.setNombre(adminNombre);
            admin.setTipo(TipoUsuario.ADMIN);
            admin.setVerificado(true);
            usuarioRepository.save(admin);
            System.out.println("✅ Admin inicial creado: " + adminEmail);
        } else {
            System.out.println("ℹ️ Admin ya existe, no se creó uno nuevo.");
        }
    }
}
