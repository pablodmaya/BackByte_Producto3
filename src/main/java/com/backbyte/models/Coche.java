package com.backbyte.models;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Coche extends Vehiculo {

    private Integer plazas;
    private String color;
    private Integer puertas;

}
