package com.backbyte.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Alquiler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_Alquiler;

    @ManyToOne
    @JoinColumn(name = "id_Vehiculo")  // Establece la relación con Vehiculo
    private Vehiculo vehiculo;  // Relación con la clase Vehiculo

    @ManyToOne
    @JoinColumn(name = "id_Cliente")  // Relación con la clase Cliente
    private Cliente cliente;  // Relación con la clase Cliente

    @Column(name = "fecha_inicio")
    private Date fecha_Inicio;

    @Column(name = "fecha_fin")
    private Date fecha_Fin;

}
