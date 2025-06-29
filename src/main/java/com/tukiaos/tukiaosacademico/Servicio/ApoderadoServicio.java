package com.tukiaos.tukiaosacademico.Servicio;


import com.tukiaos.tukiaosacademico.Modelo.Apoderado;
import com.tukiaos.tukiaosacademico.Repositorio.ApoderadoRepositorio;
import com.tukiaos.tukiaosacademico.dto.ComboBoxOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApoderadoServicio {
    @Autowired
    private ApoderadoRepositorio repository;
    // Create
    public Apoderado guardarEntidad(Apoderado to) {
        return repository.save(to);
    }
    // Report
    public List<Apoderado> listarEntidad() {
        return repository.findAll();
    }
    // Update
    public Apoderado actualizarEntidad(Apoderado to) {
        return repository.save(to);
    }
    // Delete
    public void eliminarRegEntidad(Long id) {
        repository.deleteById(id);
    }
    // Buscar por ID
    public Apoderado buscarEntidad(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<ComboBoxOption> listarCombobox(){
        List<ComboBoxOption> listar=new ArrayList<>();
        ComboBoxOption cb;
        for(Apoderado cate : repository.findAll()) {
            cb=new ComboBoxOption();
            cb.setKey(String.valueOf(cate.getIdApoderado()));
            cb.setValue(cate.getNombres());
            listar.add(cb);
        }
        return listar;
    }
}
