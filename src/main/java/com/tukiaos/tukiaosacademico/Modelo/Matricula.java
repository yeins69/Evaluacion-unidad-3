package com.tukiaos.tukiaosacademico.Modelo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tukiaos_matricula")
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMatricula")
    private Long idMatricula;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    @Column(name = "fechaMatricula", nullable = false)
    private LocalDate fechaMatricula;

    @Column(name = "utilidad", nullable = false)
    private String costo;

    @ManyToOne
    @JoinColumn(name = "idCurso", referencedColumnName = "idCurso", nullable = false,
    foreignKey = @ForeignKey(name = "FK_Curso_Matricula"))
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "idApoderado", referencedColumnName = "idApoderado", nullable = false,
    foreignKey = @ForeignKey(name = "FK_Apoderado_Matricula"))
    private Alumno alumno;

    @Column(name = "estado", nullable = false)
    private String estado; // Estado de la matrícula (ej. "activa", "inactiva")
}
