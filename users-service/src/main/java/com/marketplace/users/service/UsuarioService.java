package com.marketplace.users.service;

import com.marketplace.users.entity.Usuario;
import com.marketplace.users.entity.TipoUsuario;
import com.marketplace.users.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    public Usuario registrarUsuario(Map<String, Object> payload) {
        Usuario usuario = new Usuario();
        usuario.setNombre((String) payload.get("nombre"));
        usuario.setCorreo((String) payload.get("correo"));
        usuario.setTelefono((String) payload.get("telefono"));
        usuario.setContrasena((String) payload.get("contrasena"));
        usuario.setTipo(TipoUsuario.valueOf(((String) payload.get("tipo")).toUpperCase()));
        usuario.setVerificado(Boolean.parseBoolean(payload.get("verificado").toString()));
        return usuarioRepository.save(usuario);
    }

    public boolean correoExiste(String correo) {
        return usuarioRepository.findByCorreo(correo).isPresent();
    }
}
