package com.marketplace.services.controller;

import com.marketplace.services.entity.Servicio;
import com.marketplace.services.repository.ServicioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private ServicioRepository servicioRepository;

    @GetMapping
    public ResponseEntity<List<Servicio>> obtenerTodos() {
        return ResponseEntity.ok(servicioRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Optional<Servicio> servicio = servicioRepository.findById(id);
        return servicio.isPresent() ?
                ResponseEntity.ok(servicio.get()) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Servicio no encontrado con id: " + id);
    }

    @PostMapping
    public ResponseEntity<?> crearServicio(@Valid @RequestBody Servicio servicio) {
        Servicio nuevo = servicioRepository.save(servicio);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Servicio>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(servicioRepository.findByUsuarioId(usuarioId));
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Servicio>> obtenerPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(servicioRepository.findByCategoria(categoria));
    }
}
