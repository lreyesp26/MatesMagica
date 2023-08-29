/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Matemagicas.controlador;

import com.example.Matemagicas.modelos.Estudiante;
import com.example.Matemagicas.modelos.Representate;
import com.example.Matemagicas.repositorio.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import com.example.Matemagicas.repositorio.RepresentateRepository;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author Reyes
 */
@Controller
@SessionAttributes({"nombre", "userRole"})
public class LoginController {

    @Autowired
    private RepresentateRepository representanteRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @GetMapping("/representante")
    public String representantepage() {
        return "representante"; // Esto carga la página de estudiantes (estudiante.html)
    }

    @GetMapping("/estudiantes")
    public String estudiantesPage(Model model, HttpSession session) {
        // Obtén el representante actual desde la sesión
        Long representanteId = (Long) session.getAttribute("representanteId");

        if (representanteId != null) {
            // Busca los estudiantes asociados a este representante
            Representate representante = representanteRepository.findById(representanteId).orElse(null);

            if (representante != null) {
                List<Estudiante> estudiantes = representante.getEstudiantes();
                model.addAttribute("estudiantes", estudiantes);
                return "estudiantes"; // Carga la página "estudiantes.html" con la lista de estudiantes
            }
        }
        // Si no se encuentra el representante o no tiene estudiantes, redirige a alguna otra página o maneja el caso según sea necesario.
        return "redirect:/representante";
    }

    @PostMapping("/login")
    public String login(@RequestParam String correoelectronico, @RequestParam String contrasenia, Model model, HttpSession session) {
        Representate representante = representanteRepository.findByCorreoelectronico(correoelectronico);
        Estudiante estudiante = estudianteRepository.findByCorreoelectronico(correoelectronico);

        if (representante != null && representante.getContrasenia().equals(contrasenia)) {
            // Almacenar el representanteId en la sesión
            session.setAttribute("representanteId", representante.getId());
            model.addAttribute("userRole", "representante");
            return "redirect:/representante";
        } else if (estudiante != null && estudiante.getContrasenia().equals(contrasenia)) {
            model.addAttribute("userRole", "estudiante");
            return "redirect:/estudiante";
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "index"; // Vuelve a cargar la página de inicio de sesión con un mensaje de error
        }
    }
}
