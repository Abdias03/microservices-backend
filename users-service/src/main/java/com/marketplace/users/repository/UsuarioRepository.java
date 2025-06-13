package com.marketplace.users.repository;

import com.marketplace.users.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
     Optional<Usuario> findByCorreo(String correo);

}
