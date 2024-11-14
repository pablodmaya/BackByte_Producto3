package com.backbyte.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Alquiler {
    @Id
    private Integer id_Alquiler;
    private Integer id_Vehiculo;
    private Integer id_Cliente;
    private Date fecha_Inicio;
    private Date fecha_Fin;

}
