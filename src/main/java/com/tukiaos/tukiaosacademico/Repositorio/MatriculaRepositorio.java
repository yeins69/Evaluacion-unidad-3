package com.tukiaos.tukiaosacademico.Repositorio;

import com.tukiaos.tukiaosacademico.Modelo.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatriculaRepositorio extends JpaRepository<Matricula, Long> {
    Optional<Matricula> findByAlumnoIdAlumnoAndCursoIdCurso(Long idAlumno, Long idCurso);

}
