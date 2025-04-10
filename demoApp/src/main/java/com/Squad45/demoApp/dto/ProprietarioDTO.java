package com.Squad45.demoApp.dto;

import jakarta.validation.constraints.Pattern;

public class ProprietarioDTO extends UsuarioDTO {
    
    @Pattern(regexp = "^0x[a-fA-F0-9]{40}$", message = "A carteira blockchain deve ser um endereço MetaMask válido")
    private String carteiraBlockchain;
    
    
    public String getCarteiraBlockchain() {
        return carteiraBlockchain;
    }
    
    public void setCarteiraBlockchain(String carteiraBlockchain) {
        this.carteiraBlockchain = carteiraBlockchain;
    }
}