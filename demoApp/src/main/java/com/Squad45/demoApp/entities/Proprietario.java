package com.Squad45.demoApp.entities;



import jakarta.persistence.*;

@Entity
@Table(name = "proprietarios")
public class Proprietario extends Usuario {
    
    
    private String carteiraBlockchain;

    public String getCarteiraBlockchain() {
        return carteiraBlockchain;
    }

    public void setCarteiraBlockchain(String carteiraBlockchain) {
        this.carteiraBlockchain = carteiraBlockchain;
    }
    
}
