package com.backbyte.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_Cliente;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "dni")
    private String dni;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono")
    private String telefono;

    // Relación con Usuario
    @OneToOne // Relación uno a uno (puede ser ManyToOne si varios clientes comparten usuario)
    @JoinColumn(name = "id_Usuario", referencedColumnName = "id_Usuario", nullable = false)
    @JsonIgnore
    private Usuario usuario; // Representa la relación con la tabla Usuario


}
