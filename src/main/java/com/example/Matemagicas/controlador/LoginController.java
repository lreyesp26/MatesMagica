package com.example.Matemagicas.controlador;

import com.example.Matemagicas.dto.EstudianteCalificacionDTO;
import com.example.Matemagicas.modelos.Calificacion;
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
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
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

    @GetMapping("/index")
    public String index(@RequestParam(value = "logout", required = false) String logout, Model model) {
        if (logout != null) {
            // Agrega un mensaje de éxito de cierre de sesión si lo deseas
            model.addAttribute("logoutMessage", "Sesión cerrada correctamente.");
        }
        return "index"; // Esto carga la página de inicio
    }

    @GetMapping("/estudiantes")
    public String estudiantesPage(Model model, HttpSession session) {
        // Obtén el representante actual desde la sesión
        Long representanteId = (Long) session.getAttribute("representanteId");

        if (representanteId != null) {
            // Busca el representante actual
            Representate representante = representanteRepository.findById(representanteId).orElse(null);

            if (representante != null) {
                List<Estudiante> estudiantes = representante.getEstudiantes();

                List<EstudianteCalificacionDTO> estudiantesConCalificaciones = new ArrayList<>();

                for (Estudiante estudiante : estudiantes) {
                    for (Calificacion calificacion : estudiante.getCalificaciones()) {
                        EstudianteCalificacionDTO dto = new EstudianteCalificacionDTO();
                        dto.setNombre(estudiante.getNombre());
                        dto.setApellido(estudiante.getApellido());
                        dto.setMateria(calificacion.getMateria());
                        dto.setNivel(calificacion.getNivel());
                        dto.setCalificacion(calificacion.getCalificacion());
                        estudiantesConCalificaciones.add(dto);
                    }
                }

                model.addAttribute("estudiantes", estudiantesConCalificaciones);
                return "estudiantes";
            }
        }
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
            // Almacenar el nombre y apellido del estudiante en la sesión
            session.setAttribute("nombreEstudiante", estudiante.getNombre());
            session.setAttribute("apellidoEstudiante", estudiante.getApellido());
            model.addAttribute("userRole", "estudiante");
            return "redirect:/estudiante";
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "index"; // Vuelve a cargar la página de inicio de sesión con un mensaje de error
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        // Invalida la sesión actual
        session.removeAttribute("representanteId"); // Cambia "usuario" a "representanteId"

        // Agrega encabezados de respuesta para evitar el almacenamiento en caché
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        // Agrega un parámetro aleatorio a la URL de redirección
        return "redirect:/index?logout=" + Math.random();
    }
}
