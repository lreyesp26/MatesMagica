package com.example.Matemagicas.modelos;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Usuarios")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private String rol;

    @Column
    private String fechadenacimiento;

    @Column
    private String correoelectronico;

    @Column
    private String contrasenia;
}
