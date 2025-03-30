package com.Squad45.demoApp.entities;



import jakarta.persistence.*;
//import java.util.ArrayList;
//import java.util.List;

@Entity
public class Proprietario extends Usuario {
    
    
    private String carteiraBlockchain;

    //@OneToMany(mappedBy = "proprietario", cascade = CascadeType.ALL)
    //private List<Propriedade> propriedades = new ArrayList<>();
    
    // Getters e setters
    

    public String getCarteiraBlockchain() {
        return carteiraBlockchain;
    }

    public void setCarteiraBlockchain(String carteiraBlockchain) {
        this.carteiraBlockchain = carteiraBlockchain;
    }
    /* 
    public List<Propriedade> getPropriedades() {
        return propriedades;
    }

    public void setPropriedades(List<Propriedade> propriedades) {
        this.propriedades = propriedades;
    }
    
    public void adicionarPropriedade(Propriedade propriedade) {
        propriedade.setProprietario(this);
        this.propriedades.add(propriedade);
    }
        */
}
