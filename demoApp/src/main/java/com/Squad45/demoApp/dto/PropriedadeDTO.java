package com.Squad45.demoApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class PropriedadeDTO {

    @NotBlank
    private String nome;

    @NotBlank
    private String logradouro;

    @NotBlank
    private String numero;

    @NotBlank
    private String cidade;

    @NotBlank
    private String estado;

    @NotBlank
    private String pais;

    @NotNull
    @Positive
    private Double areaPreservada;

    
    @Positive
    private Double producaoCarbono;

    @NotBlank
    @Pattern(regexp = "[A-Z]{2}-\\d{7}-([A-Za-z0-9]{4}\\.){7}[A-Za-z0-9]{4}",
            message = "Formato inválido para o idCAR")
    private String idCAR;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Double getAreaPreservada() {
        return areaPreservada;
    }

    public void setAreaPreservada(Double areaPreservada) {
        this.areaPreservada = areaPreservada;
    }

    public Double getProducaoCarbono() {
        return producaoCarbono;
    }

    public void setProducaoCarbono(Double producaoCarbono) {
        this.producaoCarbono = producaoCarbono;
    }

    public String getIdCAR() {
        return idCAR;
    }

    public void setIdCAR(String idCAR) {
        this.idCAR = idCAR;
    }
}