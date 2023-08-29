package com.example.Matemagicas.modelos;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Estudiante")
@Data
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private String fechadenacimiento;

    @Column
    private String correoelectronico;

    @Column
    private String contrasenia;

    @ManyToOne // Muchos estudiantes pueden tener un representante
    @JoinColumn(name = "representante_id") // Esto establece la clave foránea
    private Representate representante;
}
