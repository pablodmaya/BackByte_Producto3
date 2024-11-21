package com.backbyte.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_Usuario;

    @Column(name = "nombre_Usuario")
    private String nombreUsuario;

    @Column(name = "password")
    private String contraseña;

    @Column(name = "password_encriptada")
    private String passwordEncriptada;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING) // Indica que se almacenará como texto en la base de datos
    private TipoUsuario tipo_Usuario;

    @Column(name = "es_cliente")
    private Boolean es_Cliente;

    public Boolean getEs_Cliente() {
        return es_Cliente;
    }

    public void setEs_Cliente(Boolean es_Cliente) {
        this.es_Cliente = es_Cliente;
    }
}
