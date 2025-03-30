package com.Squad45.demoApp.controller;

import com.Squad45.demoApp.dto.RegisterRequest;
import com.Squad45.demoApp.dto.UsuarioDTO;
import com.Squad45.demoApp.entities.*;
import com.Squad45.demoApp.service.JwtTokenService;
import com.Squad45.demoApp.service.UsuarioService;
import com.Squad45.demoApp.util.UsuarioMapper;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping("/proprietario")
    public ResponseEntity<StandardResponse<?>> registrarProprietario(@Valid @RequestBody RegisterRequest dadosRegistro) {
        try {
            // Validar se as senhas são iguais
            if (!dadosRegistro.getPassword().equals(dadosRegistro.getConfirmPassword())) {
                return ResponseEntity.badRequest()
                        .body(StandardResponse.error("Erro de validação", "As senhas não coincidem"));
            }

            // Verificar se o email já existe
            if (usuarioService.buscarPorEmail(dadosRegistro.getEmail()) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(StandardResponse.error("Erro de validação", "Email já cadastrado"));
            }
            
            // Verificar se o CPF já existe
            if (usuarioService.existsByCpf(dadosRegistro.getCpf())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(StandardResponse.error("Erro de validação", "CPF já cadastrado"));
            }

            // Criar o proprietário
            Proprietario proprietario = new Proprietario();
            proprietario.setEmail(dadosRegistro.getEmail());
            proprietario.setPassword(dadosRegistro.getPassword());
            proprietario.setNome(dadosRegistro.getNome());
            proprietario.setCpf(dadosRegistro.getCpf());
            proprietario.setRole(com.Squad45.demoApp.entities.Role.PROPRIETARIO);
            
            // Dados opcionais
            if (dadosRegistro.getCarteiraBlockchain() != null) {
                proprietario.setCarteiraBlockchain(dadosRegistro.getCarteiraBlockchain());
            }

            Usuario usuarioCriado = usuarioService.createUsuario(proprietario);
            UsuarioDTO dto = UsuarioMapper.toDTO(usuarioCriado);
            
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(StandardResponse.success("Proprietário registrado com sucesso", dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(StandardResponse.error("Erro ao registrar proprietário", e.getMessage()));
        }
    }

    @PostMapping("/administrador")
    public ResponseEntity<StandardResponse<?>> registrarAdmin(@Valid @RequestBody RegisterRequest dadosRegistro, 
                                                            @RequestHeader("Authorization") String token) {
        try {
            // Verificar se o usuário atual é administrador
            String jwt = token.substring(7); // Remove "Bearer " do token
            String email = jwtTokenService.getUsernameFromToken(jwt);
            Usuario usuarioAtual = usuarioService.buscarPorEmail(email);
            
            if (usuarioAtual == null || !com.Squad45.demoApp.entities.Role.ADMINISTRADOR.equals(usuarioAtual.getRole())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(StandardResponse.error("Acesso negado", "Apenas administradores podem registrar outros administradores"));
            }
            
            // Validar se as senhas são iguais
            if (!dadosRegistro.getPassword().equals(dadosRegistro.getConfirmPassword())) {
                return ResponseEntity.badRequest()
                        .body(StandardResponse.error("Erro de validação", "As senhas não coincidem"));
            }

            // Verificar se o email já existe
            if (usuarioService.buscarPorEmail(dadosRegistro.getEmail()) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(StandardResponse.error("Erro de validação", "Email já cadastrado"));
            }
            
            // Verificar se o CPF já existe
            if (usuarioService.existsByCpf(dadosRegistro.getCpf())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(StandardResponse.error("Erro de validação", "CPF já cadastrado"));
            }

            // Criar o administrador
            Administrador administrador = new Administrador();
            administrador.setEmail(dadosRegistro.getEmail());
            administrador.setPassword(dadosRegistro.getPassword());
            administrador.setNome(dadosRegistro.getNome());
            administrador.setCpf(dadosRegistro.getCpf());
            administrador.setRole(com.Squad45.demoApp.entities.Role.ADMINISTRADOR);

            Usuario usuarioCriado = usuarioService.createUsuario(administrador);
            UsuarioDTO dto = UsuarioMapper.toDTO(usuarioCriado);
            
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(StandardResponse.success("Administrador registrado com sucesso", dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(StandardResponse.error("Erro ao registrar administrador", e.getMessage()));
        }
    }
}