package com.tukiaos.tukiaosacademico.Repositorio;

import com.tukiaos.tukiaosacademico.Modelo.Aula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AulaRepositorio extends JpaRepository<Aula, Long> {

    Optional<Aula> findByNombreAula(String nombreAula);  // Método que usa Spring Data automáticamente

}
