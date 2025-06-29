package com.tukiaos.tukiaosacademico.Modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tukiaos_curso")
public class Curso {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idCurso")
  private Long idCurso;
  @Column(name = "nombreCurso")
  private String nombreCurso;
  @Column(name = "horasSemana")
  private String horasSemana;


  @ManyToOne
  @JoinColumn(name = "idProfesor", referencedColumnName = "idProfesor",
          nullable = false,
          foreignKey = @ForeignKey(name = "FK_profesor_curso"))
  private Profesor profesor;

  @ManyToOne
    @JoinColumn(name = "idAula", referencedColumnName = "idAula",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_aula_curso"))
  private Aula aula;
}
