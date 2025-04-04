package com.Squad45.demoApp.dto;

import com.Squad45.demoApp.entities.StatusPropriedade;
import java.time.LocalDateTime;

public class PropriedadeResponseDTO {
    private Long id;
    private String nome;
    private String logradouro;
    private String numero;
    private String cidade;
    private String estado;
    private String pais;
    private Double areaPreservada;
    private Double producaoCarbono;
    private StatusPropriedade status;
    private String mensagemStatus;
    private LocalDateTime dataCadastro;
    private ProprietarioResumoDTO proprietario;

    
    public static class ProprietarioResumoDTO {
        private Long id;
        private String carteiraBlockchain;

        
        public ProprietarioResumoDTO(Long id, String carteiraBlockchain) {
            this.id = id;
            this.carteiraBlockchain = carteiraBlockchain;
        }

        
        public Long getId() {
            return id;
        }

        public String getCarteiraBlockchain() {
            return carteiraBlockchain;
        }
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


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


    public StatusPropriedade getStatus() {
        return status;
    }


    public void setStatus(StatusPropriedade status) {
        this.status = status;
    }


    public String getMensagemStatus() {
        return mensagemStatus;
    }


    public void setMensagemStatus(String mensagemStatus) {
        this.mensagemStatus = mensagemStatus;
    }


    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }


    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    

    public void setProprietario(ProprietarioResumoDTO proprietarioResumo) {
        this.proprietario = proprietarioResumo;
    }


    public ProprietarioResumoDTO getProprietario() {
        return proprietario;
    }

    
}