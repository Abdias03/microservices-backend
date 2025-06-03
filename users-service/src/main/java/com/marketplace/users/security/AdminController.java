package com.marketplace.users.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.users.dto.RegisterRequest;
import com.marketplace.users.entity.TipoUsuario;
import com.marketplace.users.entity.Usuario;
import com.marketplace.users.repository.UsuarioRepository;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterRequest request) {
        if (usuarioRepository.findByCorreo(request.getCorreo()).isPresent()) {
            return ResponseEntity.badRequest().body("El correo ya está registrado.");
        }

        if (!request.getTipo().equalsIgnoreCase("admin")) {
            return ResponseEntity.badRequest().body("Este endpoint solo permite crear usuarios ADMIN.");
        }

        Usuario nuevo = new Usuario();
        nuevo.setNombre(request.getNombre());
        nuevo.setCorreo(request.getCorreo());
        nuevo.setTelefono(request.getTelefono());
        nuevo.setTipo(TipoUsuario.ADMIN);
        nuevo.setContrasena(passwordEncoder.encode(request.getContrasena()));
        nuevo.setVerificado(false);

        usuarioRepository.save(nuevo);

        return ResponseEntity.ok("Usuario ADMIN creado exitosamente.");
    }

}
