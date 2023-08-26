/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Matemagicas.controlador;

import com.example.Matemagicas.modelos.Usuario;
import com.example.Matemagicas.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Reyes
 */

@Controller
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private UsuarioRepository usuarioRepository; // Debes tener un repositorio para acceder a la base de datos

    @PostMapping("/nuevo")
    public String registrarUsuario(
        @RequestParam String nombre,
        @RequestParam String apellido,
        @RequestParam String rol,
        @RequestParam String fechadenacimiento,
        @RequestParam String correoelectronico,
        @RequestParam String contrasenia
    ) {
        // Crea una instancia de Usuario con los datos del formulario
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setRol(rol);
        usuario.setFechadenacimiento(fechadenacimiento);
        usuario.setCorreoelectronico(correoelectronico);
        usuario.setContrasenia(contrasenia);
        
        // Guarda el usuario en la base de datos
        usuarioRepository.save(usuario);

        // Redirige a una página de confirmación o a donde desees
        return "confirmacion";
    }
}
