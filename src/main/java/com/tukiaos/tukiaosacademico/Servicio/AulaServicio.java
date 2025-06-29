package com.tukiaos.tukiaosacademico.Servicio;

import com.tukiaos.tukiaosacademico.Modelo.Aula;
import com.tukiaos.tukiaosacademico.dto.ComboBoxOption;

import java.util.List;

public interface AulaServicio {
    Aula guardarEntidad(Aula to);
    Aula actualizarEntidad(Aula to);
    void eliminarRegEntidad(Long id);
    List<Aula> listarEntidad();
    Aula buscarEntidad(Long id);
    Aula buscarPorNombre(String nombreAula);
    // Nuevo
    List<ComboBoxOption> listarCombobox();
}
