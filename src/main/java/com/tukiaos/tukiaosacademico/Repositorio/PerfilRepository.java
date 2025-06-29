package com.tukiaos.tukiaosacademico.Repositorio;

import com.tukiaos.tukiaosacademico.Modelo.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {
}
