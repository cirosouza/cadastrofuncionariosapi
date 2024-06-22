package com.exercise.cadastroFuncionariosApp.service;

import com.exercise.cadastroFuncionariosApp.model.Departamento;
import com.exercise.cadastroFuncionariosApp.repository.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    public DepartamentoService(DepartamentoRepository departamentoRepository) {
        this.departamentoRepository = departamentoRepository;
    }

    public Optional<Departamento> getDepartamentoById(Long id) {
        return departamentoRepository.findById(id);
    }

    public List<Departamento> getAllDepartamentos() {
        return departamentoRepository.findAll();
    }

    public void deleteDepartamento(Departamento departamento) {
        departamentoRepository.delete(departamento);
    }

    public Departamento createDepartamento(Departamento departamento) {
        return departamentoRepository.save(departamento);
    }

    public Departamento updateDepartamento(Long id, Departamento departamento) {
        Departamento departamentoEncontrado = departamentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Departamento não encontrado com o id: " + id));

        departamentoEncontrado.setNome(departamento.getNome());
        departamentoEncontrado.setLocal(departamento.getLocal());
        departamentoEncontrado.setFuncionarios(departamento.getFuncionarios());

        return departamentoRepository.save(departamentoEncontrado);
    }
}
