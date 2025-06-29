package com.tukiaos.tukiaosacademico.Servicio;

import com.tukiaos.tukiaosacademico.Modelo.Usuario;
import com.tukiaos.tukiaosacademico.Repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepository usuarioRepository;
    // Create
    public Usuario guardarEntidad(Usuario to) {
        return usuarioRepository.save(to);
    }
    // Report
    public List<Usuario> listarEntidad() {
        return usuarioRepository.findAll();
    }
    // Update
    public Usuario actualizarEntidad(Usuario to) {
        return usuarioRepository.save(to);
    }
    // Delete
    public void eliminarRegEntidad(Long id) {
        usuarioRepository.deleteById(id);
    }
    // Buscar por ID
    public Usuario buscarEntidad(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario loginUsuario(String user, String clave) {
        return usuarioRepository.loginUsuario(user, clave);
    }

}
