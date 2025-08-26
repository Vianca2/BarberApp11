package com.barberapp.controller;

import com.barberapp.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/barbero")
public class BarberoController {

    @Autowired
    private CitaService citaService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("citasPendientes", citaService.obtenerCitasPendientes());
        return "barbero/dashboard";
    }

    @PostMapping("/confirmar/{id}")
    public String confirmarCita(@PathVariable Long id) {
        citaService.confirmarCita(id);
        return "redirect:/barbero/dashboard";
    }

    @PostMapping("/rechazar/{id}")
    public String rechazarCita(@PathVariable Long id) {
        citaService.rechazarCita(id);
        return "redirect:/barbero/dashboard";
    }
}