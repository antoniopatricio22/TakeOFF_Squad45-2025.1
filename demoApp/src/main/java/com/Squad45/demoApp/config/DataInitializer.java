

package com.Squad45.demoApp.config;

import com.Squad45.demoApp.entities.Administrador;
import com.Squad45.demoApp.entities.Role;
import com.Squad45.demoApp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Remover
        if (usuarioRepository.count() == 0) {
            
            Administrador admin = new Administrador();
            admin.setEmail("admin@exemplo.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setCpf("016.827.070-67");
            admin.setNome("Pedro Paulo");
            admin.setRole(Role.ADMINISTRADOR);
            
            usuarioRepository.save(admin);
            
            System.out.println("===================================================");
            System.out.println("ADMINISTRADOR PADRÃO CRIADO COM SUCESSO!");
            System.out.println("Email: admin@exemplo.com");
            System.out.println("Senha: admin123");
            System.out.println("CPF: 016.827.070-67");
            System.out.println("Nome: Pedro Paulo");
            System.out.println("===================================================");
        }
    }
}