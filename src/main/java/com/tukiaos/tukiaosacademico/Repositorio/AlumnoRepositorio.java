package com.tukiaos.tukiaosacademico.Repositorio;

import com.tukiaos.tukiaosacademico.Modelo.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlumnoRepositorio extends JpaRepository<Alumno, Long> {
 
    Alumno findByNombres(String nombres);

    List<Alumno> findByAula_IdAula(Long idAula);

}
