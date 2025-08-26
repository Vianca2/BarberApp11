package com.barberapp.service;

import com.barberapp.entity.Cita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarConfirmacion(Cita cita) {
        if (cita.getCliente() == null || cita.getCliente().getEmail() == null) {
            System.err.println("No se puede enviar email: cliente sin correo");
            return;
        }

        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setTo(cita.getCliente().getEmail());
            mensaje.setSubject("Cita Confirmada - Barbería");
            mensaje.setText(String.format(
                    "Hola %s,\n\nTu cita ha sido confirmada.\n" +
                            "Fecha: %s\nTipo de corte: %s\n" +
                            "Código de confirmación: %s\n\n¡Te esperamos!",
                    cita.getCliente().getNombre(),
                    cita.getFechaHora(),
                    cita.getTipoCorte(),
                    cita.getCodigoConfirmacion()
            ));
            mailSender.send(mensaje);
            System.out.println("Correo de confirmación enviado a " + cita.getCliente().getEmail());

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error enviando correo de confirmación: " + e.getMessage());
        }
    }

    public void enviarRechazo(Cita cita) {
        if (cita.getCliente() == null || cita.getCliente().getEmail() == null) {
            System.err.println("No se puede enviar email: cliente sin correo");
            return;
        }

        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setTo(cita.getCliente().getEmail());
            mensaje.setSubject("Cita No Disponible - Barbería");
            mensaje.setText(String.format(
                    "Hola %s,\n\nLo sentimos, tu cita para el %s no está disponible.\n" +
                            "Por favor agenda una nueva cita en otro horario.\n\nGracias por tu comprensión.",
                    cita.getCliente().getNombre(),
                    cita.getFechaHora()
            ));
            mailSender.send(mensaje);
            System.out.println("Correo de rechazo enviado a " + cita.getCliente().getEmail());

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error enviando correo de rechazo: " + e.getMessage());
        }
    }
}
