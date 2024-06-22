package com.exercise.cadastroFuncionariosApp.model;

import jakarta.persistence.Id;
import jakarta.persistence.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="usuario")
public class Usuario {
    @Id
    private String id;
    private String nome;
    private String senha;
    private String papel;
    @Version
    Integer version;

    public Usuario(String id, String nome, String senha, String papel, Integer version) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.papel = papel;
        this.version = version;
    }

    public Usuario() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
