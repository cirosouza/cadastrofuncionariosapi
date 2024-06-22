package com.exercise.cadastroFuncionariosApp.repository;

import com.exercise.cadastroFuncionariosApp.model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
}
