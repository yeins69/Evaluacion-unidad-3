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
@Table(name = "tukiaos_apoderado")
public class Apoderado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idApoderado")
    private Long  idApoderado;
    @Column(name = "nombres")
    private String nombres;
    @Column(name = "apellidoPaterno")
    private String apellidoPaterno;
    @Column(name = "apellidoMaterno")
    private String apellidoMaterno;
    @Column(name = "celular")
    private String celular;
    @Column(name = "direccion")
    private String direccion;


    @Override
    public String toString() {
        return nombres;
    }
}


