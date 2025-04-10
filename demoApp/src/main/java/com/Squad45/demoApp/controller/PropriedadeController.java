package com.Squad45.demoApp.controller;

import com.Squad45.demoApp.dto.PropriedadeDTO;
import com.Squad45.demoApp.dto.PropriedadeResponseDTO;
import com.Squad45.demoApp.dto.ValidacaoPropriedadeRequest;
import com.Squad45.demoApp.entities.*;
import com.Squad45.demoApp.service.JwtTokenService;
import com.Squad45.demoApp.service.PropriedadeService;
import com.Squad45.demoApp.service.UsuarioService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/propriedades")
public class PropriedadeController {

    @Autowired
    private PropriedadeService propriedadeService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping
    public ResponseEntity<StandardResponse<?>> cadastrarPropriedade(
            @RequestBody @Valid PropriedadeDTO propriedadeDTO,
            @RequestHeader("Authorization") String token) {

        try {
            String jwt = token.substring(7);
            String email = jwtTokenService.getUsernameFromToken(jwt);
            Usuario usuario = usuarioService.buscarPorEmail(email);


            if (usuario == null || !Role.PROPRIETARIO.equals(usuario.getRole())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(StandardResponse.error("Acesso negado",
                                "Apenas proprietários podem cadastrar propriedades"));
            }

            Proprietario proprietario = (Proprietario) usuario;
            PropriedadeResponseDTO propriedade = propriedadeService.cadastrarPropriedade(propriedadeDTO, proprietario);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(StandardResponse.success("Propriedade cadastrada com sucesso", propriedade));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(StandardResponse.error("Erro ao cadastrar propriedade", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<StandardResponse<?>> listarPropriedades(
            @RequestHeader("Authorization") String token) {

        try {
            String jwt = token.substring(7);
            String email = jwtTokenService.getUsernameFromToken(jwt);
            Usuario usuario = usuarioService.buscarPorEmail(email);

            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(StandardResponse.error("Acesso negado", "Usuário não autenticado"));
            }

            List<PropriedadeResponseDTO> propriedades;
            if (Role.ADMINISTRADOR.equals(usuario.getRole())) {
                // Administrador recebe todas as propriedades
                propriedades = propriedadeService.listarPropriedades();
            } else if (Role.PROPRIETARIO.equals(usuario.getRole())) {
                // Proprietário recebe apenas suas propriedades
                propriedades = propriedadeService.listarPropriedadesPorProprietario((Proprietario) usuario);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(StandardResponse.error("Acesso negado", "Tipo de usuário não permitido"));
            }

            return ResponseEntity.ok(StandardResponse.success("Propriedades listadas com sucesso", propriedades));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(StandardResponse.error("Erro ao listar propriedades", e.getMessage()));
        }
    }

    @PutMapping("/validar")
    public ResponseEntity<StandardResponse<?>> validarPropriedade(
            @RequestBody @Valid ValidacaoPropriedadeRequest request,
            @RequestHeader("Authorization") String token) {

        try {
            String jwt = token.substring(7);
            String email = jwtTokenService.getUsernameFromToken(jwt);
            Usuario usuario = usuarioService.buscarPorEmail(email);

            if (usuario == null || !Role.ADMINISTRADOR.equals(usuario.getRole())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(StandardResponse.error("Acesso negado", "Apenas administradores podem validar propriedades"));
            }

            PropriedadeResponseDTO propriedade = propriedadeService.validarPropriedade(
                    request.getId(), request.getStatus(), request.getMensagem(), request.getProducaoCarbono());

            return ResponseEntity.ok(StandardResponse.success("Propriedade validada com sucesso", propriedade));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(StandardResponse.error("Erro ao validar propriedade", e.getMessage()));
        }
    }
}