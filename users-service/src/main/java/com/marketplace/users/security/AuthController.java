package com.marketplace.users.security;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.marketplace.users.dto.RegisterRequest;
import com.marketplace.users.entity.TipoUsuario;
import com.marketplace.users.entity.Usuario;
import com.marketplace.users.repository.UsuarioRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;
    
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            
            List<String> roles = userDetails.getAuthorities()
                    						.stream()
                    						.map(GrantedAuthority::getAuthority)  // e.g. "ROLE_CLIENTE"
                    						.collect(Collectors.toList());
            
            String token = jwtUtil.generateToken(userDetails.getUsername(), roles);
            
            return ResponseEntity.ok().body("Bearer " + token);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body("Usuario o contraseña incorrectos");
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        // Validar si el tipo de usuario es ADMIN
        if ("ADMIN".equalsIgnoreCase(request.getTipo())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("No está permitido registrarse como ADMIN.");
        }

        // Validar si el correo ya está registrado
        if (usuarioRepository.findByCorreo(request.getCorreo()).isPresent()) {
            return ResponseEntity.badRequest().body("El correo ya está registrado.");
        }

        try {
            Usuario nuevo = new Usuario();
            nuevo.setNombre(request.getNombre());
            nuevo.setCorreo(request.getCorreo());
            nuevo.setTelefono(request.getTelefono());
            nuevo.setTipo(TipoUsuario.valueOf(request.getTipo().toUpperCase())); // Validar enum
            nuevo.setContrasena(passwordEncoder.encode(request.getContrasena()));
            nuevo.setVerificado(false);

            usuarioRepository.save(nuevo);

            String username = nuevo.getCorreo();
            List<String> roles = List.of("ROLE_" + nuevo.getTipo().name());
            String token = jwtUtil.generateToken(username, roles);

            return ResponseEntity.ok(Collections.singletonMap("token", token));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Tipo de usuario inválido.");
        }
    }

}
