package com.exercise.cadastroFuncionariosApp.service;

import com.exercise.cadastroFuncionariosApp.model.Usuario;
import com.exercise.cadastroFuncionariosApp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(String id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario não encontrado com o id: " + id));
    }

    public Usuario createUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void updateUsuario(String id, Usuario usuario) {
        usuarioRepository.findById(id)
                .map(usuarioEncontrado ->
                        usuarioRepository.save(usuarioEncontrado))
                .orElseThrow(() -> new RuntimeException("Material Didatico não encontrado com o id: " + id));
    }

    public void deleteUsuario(String id) {
        Usuario usuario = usuarioRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado com o id: " + id));

        usuarioRepository.delete(usuario);
    }
}
