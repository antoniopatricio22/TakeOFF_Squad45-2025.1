package com.Squad45.demoApp.dto;

public class ProprietarioDTO extends UsuarioDTO {
    private String carteiraBlockchain;
    
    
    public String getCarteiraBlockchain() {
        return carteiraBlockchain;
    }
    
    public void setCarteiraBlockchain(String carteiraBlockchain) {
        this.carteiraBlockchain = carteiraBlockchain;
    }
}