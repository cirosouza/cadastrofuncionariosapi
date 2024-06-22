package com.exercise.cadastroFuncionariosApp.controller;

import com.exercise.cadastroFuncionariosApp.model.Departamento;
import com.exercise.cadastroFuncionariosApp.service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;

    public DepartamentoController(DepartamentoService departamentoService) {
        this.departamentoService = departamentoService;
    }

    @GetMapping("/{id}")
    public Departamento getDepartamentoById(@PathVariable long id){
        return departamentoService.getDepartamentoById(id).orElseThrow(() -> new RuntimeException("Departamento não encontrado " + id));
    }

    @GetMapping("")
    public List<Departamento> getAllDepartamentos(){
        return departamentoService.getAllDepartamentos();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartamento(@PathVariable long id){
        Departamento departamento = departamentoService.getDepartamentoById(id).orElseThrow(() -> new RuntimeException("Departamento não encontrado " + id));

        departamentoService.deleteDepartamento(departamento);

        return ResponseEntity.ok().body("Departamento deletado com sucesso: " + id);
    }

    @PostMapping
    public Departamento createDepartamento(@RequestBody Departamento departamento){
        return departamentoService.createDepartamento(departamento);
    }

    @PutMapping("/{id}")
    public Departamento updateDepartamento(@PathVariable Long id,@RequestBody Departamento departamento){
        return departamentoService.updateDepartamento(id,departamento);
    }
}
