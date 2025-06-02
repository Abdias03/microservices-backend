package com.marketplace.services.repository;

import com.marketplace.services.entity.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    List<Servicio> findByUsuarioId(Long usuarioId);
    List<Servicio> findByCategoria(String categoria);
}
