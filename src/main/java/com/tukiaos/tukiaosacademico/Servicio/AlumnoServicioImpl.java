package com.tukiaos.tukiaosacademico.Servicio;

import com.tukiaos.tukiaosacademico.Modelo.Alumno;
import com.tukiaos.tukiaosacademico.Repositorio.AlumnoRepositorio;
import com.tukiaos.tukiaosacademico.Servicio.AlumnoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlumnoServicioImpl implements AlumnoServicio {

    @Autowired
    private AlumnoRepositorio alumnoRepositorio;

    @Override
    public List<Alumno> listarEntidad() {
        return alumnoRepositorio.findAll();
    }

    @Override
    public Alumno guardarEntidad(Alumno alumno) {
        return alumnoRepositorio.save(alumno);
    }

    @Override
    public Alumno buscarEntidad(Long id) {
        return alumnoRepositorio.findById(id).orElse(null);
    }

    @Override
    public void eliminarRegEntidad(Long id) {
        alumnoRepositorio.deleteById(id);
    }
    @Override
    public Alumno buscarPorNombre(String nombre) {
        return alumnoRepositorio.findByNombres(nombre); // asumimos que Alumno tiene un campo 'nombres'
    }


    @Override
    public Alumno actualizarEntidad(Alumno alumno) {
        return alumnoRepositorio.save(alumno); // UPDATE
    }

    @Override
    public List<Alumno> listarAlumnosPorAula(Long idAula) {
        return alumnoRepositorio.findByAula_IdAula(idAula); // delegamos al repositorio
    }
}
