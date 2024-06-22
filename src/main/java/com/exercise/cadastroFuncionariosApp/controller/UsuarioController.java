package com.exercise.cadastroFuncionariosApp.controller;

import com.exercise.cadastroFuncionariosApp.model.Usuario;
import com.exercise.cadastroFuncionariosApp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/usuarios")
    public List<Usuario> getAllUsuario() {
        return usuarioService.findAll();
    }

    @GetMapping("/{id}")
    public Usuario getUsuarioById(@PathVariable String id) {
        return usuarioService.findById(id);
    }

    @PostMapping("/usuarios")
    public Usuario createAluno(@RequestBody Usuario usuario){
        usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
        return usuarioService.createUsuario(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAluno(@PathVariable String id, @RequestBody Usuario usuario){
        usuarioService.updateUsuario(id,usuario);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAluno(@PathVariable String id){
        usuarioService.deleteUsuario(id);

        return ResponseEntity.ok().build();
    }
}
