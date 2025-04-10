package com.Squad45.demoApp.dto;

import com.Squad45.demoApp.entities.StatusPropriedade;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ValidacaoPropriedadeRequest {

    @NotNull(message = "O ID da propriedade é obrigatório")
    private Long id;

    @NotNull(message = "O status da propriedade é obrigatório")
    private StatusPropriedade status;

    private String mensagem;

    @Positive(message = "Produção de carbono deve ser positiva")
    private Double producaoCarbono;

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

    public Double getProducaoCarbono() {
        return producaoCarbono;
    }

    public void setProducaoCarbono(Double producaoCarbono) {
        this.producaoCarbono = producaoCarbono;
    }
}