package com.tukiaos.tukiaosacademico.Modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tukiaos_aula")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAula;

    @Column(name = "nombreAula")
    private String nombreAula;

    @Override
    public String toString() {
        return nombreAula;
    }
}
