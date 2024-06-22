package com.exercise.cadastroFuncionariosApp.service;

import com.exercise.cadastroFuncionariosApp.model.Departamento;
import com.exercise.cadastroFuncionariosApp.model.Funcionario;
import com.exercise.cadastroFuncionariosApp.repository.DepartamentoRepository;
import com.exercise.cadastroFuncionariosApp.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private DepartamentoService departamentoService;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, DepartamentoService departamentoService) {
        this.funcionarioRepository = funcionarioRepository;
        this.departamentoService = departamentoService;
    }

    public Optional<Funcionario> getFuncionarioById(Long id) {
        return funcionarioRepository.findById(id);
    }

    public List<Funcionario> getAllFuncionarios(){
        return funcionarioRepository.findAll();
    }

    public List<Funcionario> findByDepartamentoId(Long id) {
        return funcionarioRepository.findByDepartamentoId(id);
    }

    public void deleteFuncionario(Funcionario funcionario) {
        funcionarioRepository.delete(funcionario);
    }

    public void addFuncionarioDepartamento(Long departamentoId, Funcionario funcionario){
        departamentoService.getDepartamentoById(departamentoId).map(departamento -> {
            funcionario.setDepartamento(departamento);
            return funcionarioRepository.save(funcionario);
        }).orElseThrow(() -> new RuntimeException("Departamento nao encontrado com o id: " + departamentoId));
    }

    public Funcionario updateFuncionario(Long id, Funcionario funcionario) {
        Funcionario funcionarioAtual = funcionarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Funcionario nao encontrado com o id: " + id));
        funcionarioAtual.setNome(funcionario.getNome());
        funcionarioAtual.setEmail(funcionario.getEmail());
        funcionarioAtual.setDepartamento(funcionario.getDepartamento());

        return funcionarioRepository.save(funcionarioAtual);
    }
}
