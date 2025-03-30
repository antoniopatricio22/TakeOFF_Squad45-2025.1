package com.Squad45.demoApp.util;

import com.Squad45.demoApp.dto.AdministradorDTO;
import com.Squad45.demoApp.dto.ProprietarioDTO;
import com.Squad45.demoApp.dto.UsuarioDTO;
import com.Squad45.demoApp.entities.Administrador;
import com.Squad45.demoApp.entities.Proprietario;
import com.Squad45.demoApp.entities.Usuario;

public class UsuarioMapper {

    public static UsuarioDTO toDTO(Usuario usuario) {
        if (usuario instanceof Proprietario) {
            return toProprietarioDTO((Proprietario) usuario);
        } else if (usuario instanceof Administrador) {
            return toAdministradorDTO((Administrador) usuario);
        } else {
            UsuarioDTO dto = new UsuarioDTO();
            dto.setId(usuario.getId());
            dto.setEmail(usuario.getEmail());
            dto.setNome(usuario.getNome());
            dto.setCpf(usuario.getCpf());
            dto.setRole(usuario.getRole());
            return dto;
        }
    }
    
    public static ProprietarioDTO toProprietarioDTO(Proprietario proprietario) {
        ProprietarioDTO dto = new ProprietarioDTO();
        dto.setId(proprietario.getId());
        dto.setEmail(proprietario.getEmail());
        dto.setNome(proprietario.getNome());
        dto.setCpf(proprietario.getCpf());
        dto.setRole(proprietario.getRole());
        dto.setCarteiraBlockchain(proprietario.getCarteiraBlockchain());
        return dto;
    }
    
    public static AdministradorDTO toAdministradorDTO(Administrador administrador) {
        AdministradorDTO dto = new AdministradorDTO();
        dto.setId(administrador.getId());
        dto.setEmail(administrador.getEmail());
        dto.setNome(administrador.getNome());
        dto.setCpf(administrador.getCpf());
        dto.setRole(administrador.getRole());
        return dto;
    }
}