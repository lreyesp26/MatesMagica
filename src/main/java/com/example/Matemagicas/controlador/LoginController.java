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

    @GetMapping("/estudiante")
    public String estudiantePage() {
        return "estudiante"; // Esto carga la página de estudiantes (estudiante.html)
    }

    @GetMapping("/representante")
    public String representantePage() {
        return "representante"; // Esto carga la página de representantes (representante.html)
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin"; // Esto carga la página de administradores (admin.html)
    }

    @GetMapping("/index")
    public String loginPage(Model model) {
        // Verifica si hay un mensaje de éxito en el modelo
        if (model.containsAttribute("success")) {
            // Muestra el mensaje de éxito en la página
            model.addAttribute("message", model.asMap().get("success"));
        }

        return "index";
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
