package com.tukiaos.tukiaosacademico.Servicio;

import com.tukiaos.tukiaosacademico.Modelo.Matricula;
import com.tukiaos.tukiaosacademico.Repositorio.MatriculaRepositorio;
import com.tukiaos.tukiaosacademico.dto.ComboBoxOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MatriculaServicio {
    @Autowired
    private MatriculaRepositorio repository;

    public Matricula guardarEntidad(Matricula to) {
        return repository.save(to);
    }

    public List<Matricula> listarEntidad() {
        return repository.findAll();
    }

    public Matricula actualizarEntidad(Matricula to) {
        return repository.save(to);
    }

    public void eliminarRegEntidad(Long id) {
        repository.deleteById(id);
    }

    public Matricula buscarEntidad(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<ComboBoxOption> listarCombobox() {
        List<ComboBoxOption> listar = new ArrayList<>();
        for (Matricula m : repository.findAll()) {
            listar.add(new ComboBoxOption(String.valueOf(m.getIdMatricula()), m.getAlumno().getNombres()));
        }
        return listar;
    }

    // ✅ MÉTODO NUEVO
    public boolean alumnoEstaMatriculadoEnCurso(Long idAlumno, Long idCurso) {
        Optional<Matricula> matricula = repository.findByAlumnoIdAlumnoAndCursoIdCurso(idAlumno, idCurso);
        return matricula.isPresent();
    }
}
