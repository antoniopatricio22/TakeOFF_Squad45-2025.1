package com.Squad45.demoApp.entities;



import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "proprietarios")
public class Proprietario extends Usuario {
    
    @Pattern(regexp = "^0x[a-fA-F0-9]{40}$", message = "A carteira blockchain deve ser um endereço MetaMask válido")
    private String carteiraBlockchain;

    public String getCarteiraBlockchain() {
        return carteiraBlockchain;
    }

    public void setCarteiraBlockchain(String carteiraBlockchain) {
        this.carteiraBlockchain = carteiraBlockchain;
    }
    
}
