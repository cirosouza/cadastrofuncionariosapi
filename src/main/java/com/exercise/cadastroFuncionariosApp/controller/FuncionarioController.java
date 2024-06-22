package com.exercise.cadastroFuncionariosApp.controller;

import com.exercise.cadastroFuncionariosApp.model.Departamento;
import com.exercise.cadastroFuncionariosApp.model.Funcionario;
import com.exercise.cadastroFuncionariosApp.service.DepartamentoService;
import com.exercise.cadastroFuncionariosApp.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;
    @Autowired
    private DepartamentoService departamentoService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping("/{id}")
    public Funcionario getFuncionario(@PathVariable Long id) {
        return funcionarioService.getFuncionarioById(id).orElseThrow(() -> new RuntimeException("Funcionario não encontrado " + id));
    }

    @GetMapping("")
    public List<Funcionario> getAllFuncionario(){
        return funcionarioService.getAllFuncionarios();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFuncionario(@PathVariable long id){
        Funcionario funcionario = funcionarioService.getFuncionarioById(id).orElseThrow(() -> new RuntimeException("Funcionario não encontrado " + id));

        funcionarioService.deleteFuncionario(funcionario);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/departamento/{departamentoId}")
    public ResponseEntity<?> getAllFuncionariosDepartamento(@PathVariable Long departamentoId){
        List<Funcionario> funcionarios = funcionarioService.findByDepartamentoId(departamentoId);
        if(funcionarios.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(funcionarios);
    }

    @PostMapping
    public ResponseEntity<?> createFuncionario(@RequestBody Funcionario funcionario){
        funcionarioService.addFuncionarioDepartamento(funcionario.getDepartamento().getId(),funcionario);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public Funcionario updateFuncionario(@PathVariable Long id,@RequestBody Funcionario funcionario){
        return funcionarioService.updateFuncionario(id,funcionario);
    }
}
