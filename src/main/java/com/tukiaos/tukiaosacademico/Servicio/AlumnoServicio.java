package com.tukiaos.tukiaosacademico.Servicio;

import com.tukiaos.tukiaosacademico.Modelo.Alumno;
import java.util.List;

public interface AlumnoServicio {
    List<Alumno> listarEntidad();
    Alumno guardarEntidad(Alumno alumno);
    Alumno buscarEntidad(Long id);
    void eliminarRegEntidad(Long id);
    Alumno actualizarEntidad(Alumno alumno);
    Alumno buscarPorNombre(String nombre);
    List<Alumno> listarAlumnosPorAula(Long idAula);
}
