package com.marketplace.users.controller;

import com.marketplace.users.entity.Usuario;
import com.marketplace.users.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<?> obtenerUsuarioPorCorreo(@PathVariable String correo) {
    	Optional<Usuario> usuario = usuarioService.buscarPorCorreo(correo);

    	if (usuario.isPresent()) {
    	    return ResponseEntity.ok(usuario.get());
    	} else {
    	    return ResponseEntity.status(HttpStatus.NOT_FOUND)
    	            .body("Usuario no encontrado con el correo: " + correo);
    	}
    }

    @PostMapping
    public ResponseEntity<?> registrarUsuario(@RequestBody Map<String, Object> payload) {	
    	
        try {
            String correo = (String) payload.get("correo");
            logger.info("Iniciando creación de usuario con email: {}", correo);

            if (usuarioService.correoExiste(correo)) {
            	logger.info("Conflicto Usuario ya registrado: {}", correo);
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("El correo '" + correo + "' ya está registrado.");
            }

            Usuario nuevo = usuarioService.registrarUsuario(payload);
            logger.info("Usuario creado exitosamente ID: {}", nuevo.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);

        } catch (IllegalArgumentException e) {
        	logger.warn("El tipo debe ser: cliente, proveedor o admin: {}", (String) payload.get("tipo"));
            return ResponseEntity.badRequest().body("El tipo debe ser: cliente, proveedor o admin.");
        } catch (Exception e) {
        	logger.error("Error al crear usuario con email {}: {}", (String) payload.get("correo") , e.getMessage());
            return ResponseEntity.badRequest().body("Error al registrar usuario: " + e.getMessage());
        }
    }
    
}
