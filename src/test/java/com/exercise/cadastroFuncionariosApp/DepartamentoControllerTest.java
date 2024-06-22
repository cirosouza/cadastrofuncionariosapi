package com.exercise.cadastroFuncionariosApp;

import com.exercise.cadastroFuncionariosApp.controller.DepartamentoController;
import com.exercise.cadastroFuncionariosApp.model.Departamento;
import com.exercise.cadastroFuncionariosApp.service.DepartamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DepartamentoController.class)
public class DepartamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartamentoService departamentoService;

    @Test
    @WithMockUser(username="admin",roles="ADMIN")
    void testGetDepartamentoById() throws Exception {
        Departamento departamento = new Departamento(1L, "Departamento A", "Local A");
        given(departamentoService.getDepartamentoById(anyLong())).willReturn(Optional.of(departamento));

        mockMvc.perform(get("/api/public/departamentos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(departamento.getId()))
                .andExpect(jsonPath("$.nome").value(departamento.getNome()))
                .andExpect(jsonPath("$.local").value(departamento.getLocal()));
    }

    @Test
    @WithMockUser(username="admin",roles="ADMIN")
    void testGetAllDepartamentos() throws Exception {
        Departamento departamento1 = new Departamento(1L, "Departamento A", "Local A");
        Departamento departamento2 = new Departamento(2L, "Departamento B", "Local B");
        given(departamentoService.getAllDepartamentos()).willReturn(Arrays.asList(departamento1, departamento2));

        mockMvc.perform(get("/api/public/departamentos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(departamento1.getId()))
                .andExpect(jsonPath("$[0].nome").value(departamento1.getNome()))
                .andExpect(jsonPath("$[0].local").value(departamento1.getLocal()))
                .andExpect(jsonPath("$[1].id").value(departamento2.getId()))
                .andExpect(jsonPath("$[1].nome").value(departamento2.getNome()))
                .andExpect(jsonPath("$[1].local").value(departamento2.getLocal()));
    }

    @Test
    @WithMockUser(username="admin",roles="ADMIN")
    void testDeleteDepartamento() throws Exception {
        Departamento departamento = new Departamento(1L, "Departamento A", "Local A");
        given(departamentoService.getDepartamentoById(anyLong())).willReturn(Optional.of(departamento));
        doNothing().when(departamentoService).deleteDepartamento(any(Departamento.class));

        mockMvc.perform(delete("/api/public/departamentos/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Departamento deletado com sucesso: 1"));
    }

    @Test
    @WithMockUser(username="admin",roles="ADMIN")
    void testCreateDepartamento() throws Exception {
        Departamento departamento = new Departamento(1L, "Departamento A", "Local A");
        given(departamentoService.createDepartamento(any(Departamento.class))).willReturn(departamento);

        mockMvc.perform(post("/api/public/departamentos")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Departamento A\", \"local\": \"Local A\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(departamento.getId()))
                .andExpect(jsonPath("$.nome").value(departamento.getNome()))
                .andExpect(jsonPath("$.local").value(departamento.getLocal()));
    }

    @Test
    @WithMockUser(username="admin",roles="ADMIN")
    void testUpdateDepartamento() throws Exception {
        Departamento departamento = new Departamento(1L, "Departamento A", "Local A");
        given(departamentoService.updateDepartamento(anyLong(), any(Departamento.class))).willReturn(departamento);

        mockMvc.perform(put("/api/public/departamentos/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Departamento A\", \"local\": \"Local A\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(departamento.getId()))
                .andExpect(jsonPath("$.nome").value(departamento.getNome()))
                .andExpect(jsonPath("$.local").value(departamento.getLocal()));
    }
}
