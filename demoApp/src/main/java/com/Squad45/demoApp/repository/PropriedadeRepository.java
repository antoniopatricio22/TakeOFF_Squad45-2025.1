package com.Squad45.demoApp.repository;

import com.Squad45.demoApp.entities.Propriedade;
import com.Squad45.demoApp.entities.Proprietario;
//import com.Squad45.demoApp.entities.StatusPropriedade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropriedadeRepository extends JpaRepository<Propriedade, Long> {
    List<Propriedade> findByProprietario(Proprietario proprietario);
    //List<Propriedade> findByStatus(StatusPropriedade status);
}