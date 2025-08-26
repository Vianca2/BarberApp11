package com.barberapp.controller;

import com.barberapp.entity.Cita;
import com.barberapp.entity.Usuario;
import com.barberapp.service.CitaService;
import com.barberapp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private UsuarioService usuarioService;

    // Carpeta donde se guardarán las fotos (ajústala a tu proyecto)
    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/perfiles/";

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Long usuarioId = (Long) session.getAttribute("usuarioId");
        if (usuarioId == null) return "redirect:/login";

        Usuario cliente = usuarioService.buscarPorId(usuarioId);
        model.addAttribute("cliente", cliente);
        model.addAttribute("citas", citaService.obtenerCitasCliente(cliente));
        return "cliente/dashboard";
    }

    @GetMapping("/perfil")
    public String perfil(HttpSession session, Model model) {
        Long usuarioId = (Long) session.getAttribute("usuarioId");
        if (usuarioId == null) return "redirect:/login";

        Usuario cliente = usuarioService.buscarPorId(usuarioId);
        model.addAttribute("cliente", cliente);
        return "cliente/perfil";
    }

    // ✅ Subida de foto de perfil
    @PostMapping("/subir-foto")
    public String subirFotoPerfil(@RequestParam("foto") MultipartFile archivo, HttpSession session) {
        Long usuarioId = (Long) session.getAttribute("usuarioId");
        if (usuarioId == null) return "redirect:/login";

        Usuario cliente = usuarioService.buscarPorId(usuarioId);

        if (!archivo.isEmpty()) {
            try {
                // Generamos nombre único
                String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename();
                File directorio = new File(UPLOAD_DIR);
                if (!directorio.exists()) {
                    directorio.mkdirs();
                }

                // Guardamos archivo físico
                File destino = new File(UPLOAD_DIR + nombreArchivo);
                archivo.transferTo(destino);

                // Actualizamos usuario
                cliente.setFotoPerfil(nombreArchivo);
                usuarioService.guardar(cliente);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "redirect:/cliente/perfil";
    }

    @GetMapping("/nueva-cita")
    public String nuevaCita(Model model) {
        model.addAttribute("cita", new Cita());
        return "cliente/nueva-cita";
    }

    @PostMapping("/agendar")
    public String agendarCita(@RequestParam String fechaHora,
                              @RequestParam String tipoCorte,
                              @RequestParam String descripcion,
                              HttpSession session) {

        Long usuarioId = (Long) session.getAttribute("usuarioId");
        if (usuarioId == null) return "redirect:/login";

        Usuario cliente = usuarioService.buscarPorId(usuarioId);

        LocalDateTime fecha = LocalDateTime.parse(fechaHora,
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));

        Cita cita = new Cita(cliente, fecha, tipoCorte, descripcion);
        citaService.agendarCita(cita);

        return "redirect:/cliente/dashboard";
    }

    @PostMapping("/cancelar/{id}")
    public String cancelarCita(@PathVariable Long id) {
        citaService.cancelarCita(id);
        return "redirect:/cliente/dashboard";
    }
}
