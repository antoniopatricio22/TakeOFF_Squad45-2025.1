package com.Squad45.demoApp.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "propriedades", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id", "nome", "idCAR"}),
})
public class Propriedade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome da propriedade é obrigatório")
    @Column(unique = true)
    private String nome;

    @NotBlank(message = "Logradouro é obrigatório")
    private String logradouro;

    @NotBlank(message = "Número é obrigatório")
    private String numero;

    @NotBlank(message = "Cidade é obrigatório")
    private String cidade;

    @NotBlank(message = "Estado é obrigatório")
    private String estado;

    @NotBlank(message = "País é obrigatório")
    private String pais;

    @NotNull(message = "Área preservada é obrigatória")
    @Positive(message = "Área preservada deve ser positiva")
    private Double areaPreservada; // em m²

    @Pattern(regexp = "[A-Z]{2}-\\d{7}-([A-Za-z0-9]{4}\\.){7}[A-Za-z0-9]{4}",
            message = "Formato inválido para o idCAR")
    @NotBlank(message = "idCAR é obrigatório")
    @Column(unique = true)
    private String idCAR;

    @Positive(message = "Produção de carbono deve ser positiva")
    private Double producaoCarbono; // em toneladas (inserido pelo administrador)

    @Enumerated(EnumType.STRING)
    private StatusPropriedade status;

    private String mensagemStatus = "Aguardando revisão"; // Valor padrão

    @ManyToOne
    @JoinColumn(name = "proprietario_id", nullable = false)
    private Proprietario proprietario;

    private LocalDateTime dataCadastro;

    @PrePersist
    protected void onCreate() {
        this.dataCadastro = LocalDateTime.now();
    }

    // Getters e Setters
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

    public String getIdCAR() {
        return idCAR;
    }

    public void setIdCAR(String idCAR) {
        this.idCAR = idCAR;
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

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
}