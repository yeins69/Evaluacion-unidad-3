package com.tukiaos.tukiaosacademico.Servicio;


import com.tukiaos.tukiaosacademico.Modelo.Curso;
import com.tukiaos.tukiaosacademico.Repositorio.CursoRepositorio;
import com.tukiaos.tukiaosacademico.dto.ComboBoxOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CursoServicio {
    @Autowired
    private CursoRepositorio repository;
    // Create
    public Curso guardarEntidad(Curso to) {
        return repository.save(to);
    }
    // Report
    public List<Curso> listarEntidad() {
        return repository.findAll();
    }
    // Update
    public Curso actualizarEntidad(Curso to) {
        return repository.save(to);
    }
    // Delete
    public void eliminarRegEntidad(Long id) {
        repository.deleteById(id);
    }
    // Buscar por ID
    public Curso buscarEntidad(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Curso buscarPorNombre(String nombreCurso) {
        return repository.findByNombreCurso(nombreCurso).orElse(null);
    }


    public List<ComboBoxOption> listarCombobox(){
        List<ComboBoxOption> listar=new ArrayList<>();
        ComboBoxOption cb;
        for(Curso cate : repository.findAll()) {
            cb=new ComboBoxOption();
            cb.setKey(String.valueOf(cate.getIdCurso()));
            cb.setValue(cate.getNombreCurso());
            listar.add(cb);
        }
        return listar;
    }
}
