package com.exercise.cadastroFuncionariosApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/securitytest")
public class SecurityTestController {

    @GetMapping("")
    public String testSecurity(){
        return "Teste realizado com sucesso!";
    }
}
