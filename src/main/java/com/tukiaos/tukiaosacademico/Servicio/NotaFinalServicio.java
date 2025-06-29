package com.tukiaos.tukiaosacademico.Servicio;

import com.tukiaos.tukiaosacademico.Modelo.NotaFinal;
import com.tukiaos.tukiaosacademico.Repositorio.NotaFinalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotaFinalServicio {

    @Autowired
    private NotaFinalRepository notaFinalRepository;

    public List<NotaFinal> listarEntidad() {
        return notaFinalRepository.findAll();
    }

    public NotaFinal guardarEntidad(NotaFinal nota) {
        return notaFinalRepository.save(nota);
    }

    public NotaFinal actualizarEntidad(NotaFinal nota) {
        return notaFinalRepository.save(nota);
    }

    public void eliminarRegEntidad(Long id) {
        notaFinalRepository.deleteById(id);
    }

    public NotaFinal buscarEntidad(Long id) {
        return notaFinalRepository.findById(id).orElse(null);
    }
}
