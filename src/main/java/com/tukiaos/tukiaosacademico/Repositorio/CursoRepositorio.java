package com.tukiaos.tukiaosacademico.Repositorio;

import com.tukiaos.tukiaosacademico.Modelo.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CursoRepositorio extends JpaRepository<Curso, Long> {
    Optional<Curso> findByNombreCurso(String nombreCurso);

}
