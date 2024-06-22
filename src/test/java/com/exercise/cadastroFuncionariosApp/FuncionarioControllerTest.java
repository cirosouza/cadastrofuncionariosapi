package com.exercise.cadastroFuncionariosApp;

import com.exercise.cadastroFuncionariosApp.controller.FuncionarioController;
import com.exercise.cadastroFuncionariosApp.model.Departamento;
import com.exercise.cadastroFuncionariosApp.model.Funcionario;
import com.exercise.cadastroFuncionariosApp.service.DepartamentoService;
import com.exercise.cadastroFuncionariosApp.service.FuncionarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FuncionarioController.class)
class FuncionarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FuncionarioService funcionarioService;

    @MockBean
    private DepartamentoService departamentoService;

    @Test
    @WithMockUser(username="admin",roles="ADMIN")
    void testGetFuncionarioById() throws Exception {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("Funcionario A");
        funcionario.setEndereco("Endereço A");
        funcionario.setTelefone("123456789");
        funcionario.setEmail("funcionario@example.com");
        funcionario.setDataDeNascimento(new Date());
        Departamento departamento = new Departamento();
        departamento.setId(1L);
        funcionario.setDepartamento(departamento);

        given(funcionarioService.getFuncionarioById(1L)).willReturn(Optional.of(funcionario));

        mockMvc.perform(get("/api/public/funcionarios/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(funcionario.getId()))
                .andExpect(jsonPath("$.nome").value(funcionario.getNome()))
                .andExpect(jsonPath("$.endereco").value(funcionario.getEndereco()))
                .andExpect(jsonPath("$.telefone").value(funcionario.getTelefone()))
                .andExpect(jsonPath("$.email").value(funcionario.getEmail()))
                .andExpect(jsonPath("$.dataDeNascimento").isNotEmpty());
    }

    @Test
    @WithMockUser(username="admin",roles="ADMIN")
    void testGetAllFuncionario() throws Exception {
        Funcionario funcionario1 = new Funcionario();
        funcionario1.setId(1L);
        funcionario1.setNome("Funcionario A");
        Funcionario funcionario2 = new Funcionario();
        funcionario2.setId(2L);
        funcionario2.setNome("Funcionario B");
        List<Funcionario> funcionarios = Arrays.asList(funcionario1, funcionario2);

        given(funcionarioService.getAllFuncionarios()).willReturn(funcionarios);

        mockMvc.perform(get("/api/public/funcionarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(funcionario1.getId()))
                .andExpect(jsonPath("$[0].nome").value(funcionario1.getNome()))
                .andExpect(jsonPath("$[1].id").value(funcionario2.getId()))
                .andExpect(jsonPath("$[1].nome").value(funcionario2.getNome()));
    }

    @Test
    @WithMockUser(username="admin",roles="ADMIN")
    void testDeleteFuncionario() throws Exception {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("Funcionario A");

        given(funcionarioService.getFuncionarioById(1L)).willReturn(Optional.of(funcionario));
        doNothing().when(funcionarioService).deleteFuncionario(funcionario);

        mockMvc.perform(delete("/api/public/funcionarios/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin",roles="ADMIN")
    void testGetAllFuncionariosDepartamento() throws Exception {
        Funcionario funcionario1 = new Funcionario();
        funcionario1.setId(1L);
        funcionario1.setNome("Funcionario A");
        Funcionario funcionario2 = new Funcionario();
        funcionario2.setId(2L);
        funcionario2.setNome("Funcionario B");
        List<Funcionario> funcionarios = Arrays.asList(funcionario1, funcionario2);

        given(funcionarioService.findByDepartamentoId(1L)).willReturn(funcionarios);

        mockMvc.perform(get("/api/public/funcionarios/departamento/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(funcionario1.getId()))
                .andExpect(jsonPath("$[0].nome").value(funcionario1.getNome()))
                .andExpect(jsonPath("$[1].id").value(funcionario2.getId()))
                .andExpect(jsonPath("$[1].nome").value(funcionario2.getNome()));
    }

    @Test
    @WithMockUser(username="admin",roles="ADMIN")
    void testCreateFuncionario() throws Exception {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("Funcionario A");
        funcionario.setDepartamento(new Departamento());

        mockMvc.perform(post("/api/public/funcionarios")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Funcionario A\", \"departamento\": {\"id\": 1}}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin",roles="ADMIN")
    void testUpdateFuncionario() throws Exception {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("Funcionario A");
        funcionario.setDepartamento(new Departamento());

        given(funcionarioService.updateFuncionario(anyLong(), any(Funcionario.class))).willReturn(funcionario);

        mockMvc.perform(put("/api/public/funcionarios/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Funcionario A\", \"departamento\": {\"id\": 1}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(funcionario.getId()))
                .andExpect(jsonPath("$.nome").value(funcionario.getNome()));
    }
}
