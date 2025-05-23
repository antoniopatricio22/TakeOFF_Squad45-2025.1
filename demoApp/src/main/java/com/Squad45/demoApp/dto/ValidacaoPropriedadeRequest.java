package com.Squad45.demoApp.dto;

import com.Squad45.demoApp.entities.StatusPropriedade;
import jakarta.validation.constraints.NotNull;

public class ValidacaoPropriedadeRequest {

    @NotNull(message = "O ID da propriedade é obrigatório")
    private Long id;

    @NotNull(message = "O status da propriedade é obrigatório")
    private StatusPropriedade status;

    private String mensagem;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusPropriedade getStatus() {
        return status;
    }

    public void setStatus(StatusPropriedade status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}