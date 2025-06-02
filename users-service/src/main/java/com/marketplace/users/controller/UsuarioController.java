package com.marketplace.users.controller;

import com.marketplace.users.entity.Usuario;
import com.marketplace.users.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

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

            if (usuarioService.correoExiste(correo)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("El correo '" + correo + "' ya está registrado.");
            }

            Usuario nuevo = usuarioService.registrarUsuario(payload);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("El tipo debe ser: cliente, proveedor o admin.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al registrar usuario: " + e.getMessage());
        }
    }
}
