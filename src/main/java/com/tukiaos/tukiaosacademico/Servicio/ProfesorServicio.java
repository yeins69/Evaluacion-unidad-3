package com.tukiaos.tukiaosacademico.Servicio;


import com.tukiaos.tukiaosacademico.Modelo.Profesor;
import com.tukiaos.tukiaosacademico.Repositorio.ProfesorRepositorio;
import com.tukiaos.tukiaosacademico.dto.ComboBoxOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfesorServicio {
    @Autowired
    private ProfesorRepositorio repository;
    // Create
    public Profesor guardarEntidad(Profesor to) {
        return repository.save(to);
    }
    // Report
    public List<Profesor> listarEntidad() {
        return repository.findAll();
    }
    // Update
    public Profesor actualizarEntidad(Profesor to) {
        return repository.save(to);
    }
    // Delete
    public void eliminarRegEntidad(Long id) {
        repository.deleteById(id);
    }
    // Buscar por ID
    public Profesor buscarEntidad(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<ComboBoxOption> listarCombobox(){
        List<ComboBoxOption> listar=new ArrayList<>();
        ComboBoxOption cb;
        for(Profesor cate : repository.findAll()) {
            cb=new ComboBoxOption();
            cb.setKey(String.valueOf(cate.getIdProfesor()));
            cb.setValue(cate.getNombres());
            listar.add(cb);
        }
        return listar;
    }
}
