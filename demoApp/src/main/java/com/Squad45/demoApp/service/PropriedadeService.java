package com.Squad45.demoApp.service;

import com.Squad45.demoApp.dto.PropriedadeDTO;
import com.Squad45.demoApp.dto.PropriedadeResponseDTO;
import com.Squad45.demoApp.entities.*;
import com.Squad45.demoApp.repository.PropriedadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropriedadeService {

    @Autowired
    private PropriedadeRepository propriedadeRepository;

    public PropriedadeResponseDTO cadastrarPropriedade(PropriedadeDTO propriedadeDTO, Proprietario proprietario) {
        Propriedade propriedade = new Propriedade();
        propriedade.setNome(propriedadeDTO.getNome());
        propriedade.setLogradouro(propriedadeDTO.getLogradouro());
        propriedade.setNumero(propriedadeDTO.getNumero());
        propriedade.setCidade(propriedadeDTO.getCidade());
        propriedade.setEstado(propriedadeDTO.getEstado());
        propriedade.setPais(propriedadeDTO.getPais());
        propriedade.setAreaPreservada(propriedadeDTO.getAreaPreservada());
        propriedade.setIdCAR(propriedadeDTO.getIdCAR());
        propriedade.setProprietario(proprietario);
        propriedade.setStatus(StatusPropriedade.PENDENTE);

        Propriedade savedPropriedade = propriedadeRepository.save(propriedade);
        return convertToResponseDTO(savedPropriedade);
    }

    public List<PropriedadeResponseDTO> listarPropriedades() {
        return propriedadeRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<PropriedadeResponseDTO> listarPropriedadesPorProprietario(Proprietario proprietario) {
        return propriedadeRepository.findByProprietario(proprietario).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public PropriedadeResponseDTO validarPropriedade(Long id, StatusPropriedade status, String mensagem, Double producaoCarbono) {
        Propriedade propriedade = propriedadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Propriedade não encontrada"));

        propriedade.setStatus(status);
        propriedade.setMensagemStatus(mensagem);
        propriedade.setProducaoCarbono(producaoCarbono);

        Propriedade updatedPropriedade = propriedadeRepository.save(propriedade);
        return convertToResponseDTO(updatedPropriedade);
    }

    public PropriedadeResponseDTO convertToResponseDTO(Propriedade propriedade) {
        PropriedadeResponseDTO dto = new PropriedadeResponseDTO();
        dto.setId(propriedade.getId());
        dto.setNome(propriedade.getNome());
        dto.setLogradouro(propriedade.getLogradouro());
        dto.setNumero(propriedade.getNumero());
        dto.setCidade(propriedade.getCidade());
        dto.setEstado(propriedade.getEstado());
        dto.setPais(propriedade.getPais());
        dto.setAreaPreservada(propriedade.getAreaPreservada());
        dto.setProducaoCarbono(propriedade.getProducaoCarbono());
        dto.setStatus(propriedade.getStatus());
        dto.setMensagemStatus(propriedade.getMensagemStatus());
        dto.setDataCadastro(propriedade.getDataCadastro());

        // Resumo do proprietário
        Proprietario proprietario = propriedade.getProprietario();
        PropriedadeResponseDTO.ProprietarioResumoDTO proprietarioResumo = new PropriedadeResponseDTO.ProprietarioResumoDTO(
                proprietario.getId(),
                proprietario.getCarteiraBlockchain());
        dto.setProprietario(proprietarioResumo);

        return dto;
    }
}