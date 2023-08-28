/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.Matemagicas.repositorio;

import com.example.Matemagicas.modelos.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Luis
 */
public interface EstudianteRepository extends JpaRepository<Estudiante, Long>{
    Estudiante findByCorreoelectronico(String correoelectronico);
}
