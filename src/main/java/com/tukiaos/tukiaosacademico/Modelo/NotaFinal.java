package com.tukiaos.tukiaosacademico.Modelo;

import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tukiaos_nota_final")
public class NotaFinal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNota;

    @ManyToOne
    @JoinColumn(name = "idAlumno", referencedColumnName = "idAlumno",
            foreignKey = @ForeignKey(name = "FK_nota_alumno"))
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "idCurso", referencedColumnName = "idCurso",
            foreignKey = @ForeignKey(name = "FK_nota_curso"))
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "idAula", referencedColumnName = "idAula",
            foreignKey = @ForeignKey(name = "FK_nota_aula"))
    private Aula aula;  // ✅ AHORA SÍ es una relación ManyToOne

    @Column(name = "calificacion")
    private Double calificacion;

    @Column(name = "nota1")
    private Double nota1;

    @Column(name = "nota2")
    private Double nota2;

    @Column(name = "nota3")
    private Double nota3;

    // setters ya no son necesarios manualmente gracias a @Data de Lombok
}
