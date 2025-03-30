package com.Squad45.demoApp.controller;

import com.Squad45.demoApp.dto.UsuarioDTO;
import com.Squad45.demoApp.entities.StandardResponse;
import com.Squad45.demoApp.entities.Usuario;
import com.Squad45.demoApp.service.JwtTokenService;
import com.Squad45.demoApp.service.UsuarioService;
import com.Squad45.demoApp.util.UsuarioMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<StandardResponse<?>> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        
        Usuario usuario = usuarioService.buscarPorEmail(email);
        if (usuario != null && usuarioService.validatePassword(password, usuario.getPassword())) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));

            String token = jwtTokenService.generateToken(authentication);
            
            UsuarioDTO usuarioDTO = UsuarioMapper.toDTO(usuario);
            
            return ResponseEntity.ok(StandardResponse.success("Login realizado com sucesso", Map.of(
                "token", token,
                "usuario", usuarioDTO
            )));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(StandardResponse.error("Falha no login", "Email ou senha inválidos"));
        }
    }
}
