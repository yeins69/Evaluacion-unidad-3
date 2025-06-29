package com.tukiaos.tukiaosacademico.Servicio;

import com.tukiaos.tukiaosacademico.Modelo.Aula;
import com.tukiaos.tukiaosacademico.Repositorio.AulaRepositorio;
import com.tukiaos.tukiaosacademico.dto.ComboBoxOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AulaServicioImpl implements AulaServicio {

    @Autowired
    private AulaRepositorio repository;

    @Override
    public Aula guardarEntidad(Aula to) {
        return repository.save(to);
    }

    @Override
    public Aula actualizarEntidad(Aula to) {
        return repository.save(to);
    }

    @Override
    public void eliminarRegEntidad(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Aula> listarEntidad() {
        return repository.findAll();
    }

    @Override
    public Aula buscarEntidad(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Aula buscarPorNombre(String nombreAula) {
        return repository.findByNombreAula(nombreAula)
                .orElseThrow(() -> new RuntimeException(
                        "No se encontró el aula con nombre: " + nombreAula));
    }

    @Override
    public List<ComboBoxOption> listarCombobox() {
        List<ComboBoxOption> listar = new ArrayList<>();
        ComboBoxOption cb;
        for (Aula cate : repository.findAll()) {
            cb = new ComboBoxOption();
            cb.setKey(String.valueOf(cate.getIdAula()));
            cb.setValue(cate.getNombreAula());
            listar.add(cb);
        }
        return listar;
    }
}
