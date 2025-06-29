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
@Table(name = "tukiaos_alumno")
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAlumno")
    private Long idAlumno;
    @Column(name = "nombres")
    private String nombres;
    @Column(name = "apellidoPaterno")
    private String apellidoPaterno;
    @Column(name = "apellidoMaterno")
    private String apellidoMaterno;
    @Column(name = "dni")
    private String dni;
    @ManyToOne
    @JoinColumn(name = "aula_id") // o el nombre que tengas en tu DB
    private Aula aula;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    @Column(name = "fechaCumpleaños", nullable = false, length = 100)
    private LocalDate fechaCumpleaños;

    @ManyToOne
    @JoinColumn(name = "idApoderado", referencedColumnName = "idApoderado",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_Apoderado_Alumno"))
    private Apoderado apoderado;
}
