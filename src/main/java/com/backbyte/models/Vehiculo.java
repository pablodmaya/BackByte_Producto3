package com.backbyte.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_Vehiculo;

    @Column(name = "marca")
    private String marca;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "matricula")
    private String matricula;

    @Column(name = "precio_Dia")
    private Double precioDia;

    @Enumerated(EnumType.STRING)  // Para que se almacene como un String en la base de datos
    @Column(name = "localizacion")
    private Ciudad localizacion;

    @Enumerated(EnumType.STRING)  // Para guardar el tipo como un String ("COCHE", "MOTO")
    @Column(name = "tipo_Vehiculo")
    private TipoVehiculo tipoVehiculo;

}
