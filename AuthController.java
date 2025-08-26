package com.barberapp.controller;

import com.barberapp.entity.Usuario;
import com.barberapp.entity.Rol;
import com.barberapp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public String inicio() {
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registro")
    public String registro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String email,
                                @RequestParam String password,
                                HttpSession session, Model model) {
        try {
            Usuario usuario = usuarioService.autenticar(email, password);
            session.setAttribute("usuarioId", usuario.getId());
            session.setAttribute("usuarioRol", usuario.getRol().name()); // Usar .name() en lugar de .toString()

            // Comparar directamente con el enum en lugar de string
            if (usuario.getRol() == Rol.BARBERO) {
                return "redirect:/barbero/dashboard";
            }
            return "redirect:/cliente/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", "Credenciales incorrectas");
            return "login";
        }
    }

    @PostMapping("/registro")
    public String procesarRegistro(@ModelAttribute Usuario usuario, Model model) {
        try {
            usuarioService.registrar(usuario);
            model.addAttribute("exito", "Registro exitoso. Puedes iniciar sesión.");
            return "login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("usuario", usuario);
            return "registro";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}