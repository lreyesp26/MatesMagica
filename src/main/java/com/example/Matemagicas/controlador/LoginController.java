/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Matemagicas.controlador;

import com.example.Matemagicas.modelos.Usuario;
import com.example.Matemagicas.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author Reyes
 */
@Controller
@SessionAttributes({"nombre", "userRole"})
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository; // Debes inyectar tu repositorio de usuarios

    @GetMapping("/index")
    public String loginPage() {
        return "index"; // Esto carga la página de inicio de sesión (login.html)
    }

    @PostMapping("/login")
    public String login(@RequestParam String nombre, @RequestParam String contrasenia, Model model) {
        Usuario usuario = usuarioRepository.findByNombre(nombre);
        if (usuario != null && usuario.getContrasenia().equals(contrasenia)) {
            // Obtenemos el rol del usuario desde la base de datos
            String userRole = usuario.getRol();

            // Agregamos el rol al modelo
            model.addAttribute("userRole", userRole);

            // Redirigir al usuario a la página principal o al panel de control adecuado según su rol
            switch (userRole) {
                case "administrador" -> {
                    return "redirect:/admin";
                }
                case "representante" -> {
                    return "redirect:/representante";
                }
                case "estudiante" -> {
                    return "redirect:/estudiante";
                }
                default -> {
                }
            }
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "index"; // Vuelve a cargar la página de inicio de sesión con un mensaje de error
        }
        return null;
    }
}
