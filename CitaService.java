package com.barberapp.service;

import com.barberapp.entity.Cita;
import com.barberapp.entity.Usuario;
import com.barberapp.entity.EstadoCita;
import com.barberapp.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private EmailService emailService;

    public Cita agendarCita(Cita cita) {
        return citaRepository.save(cita);
    }

    public List<Cita> obtenerCitasCliente(Usuario cliente) {
        return citaRepository.findByClienteOrderByFechaHoraDesc(cliente);
    }

    public List<Cita> obtenerCitasPendientes() {
        return citaRepository.findByEstadoOrderByFechaHora(EstadoCita.PENDIENTE);
    }

    public void confirmarCita(Long citaId) {
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        cita.setEstado(EstadoCita.CONFIRMADA);
        cita.setCodigoConfirmacion(generarCodigo());
        citaRepository.save(cita);

        emailService.enviarConfirmacion(cita);
    }

    public void rechazarCita(Long citaId) {
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        cita.setEstado(EstadoCita.RECHAZADA);
        citaRepository.save(cita);

        emailService.enviarRechazo(cita);
    }

    public void cancelarCita(Long citaId) {
        citaRepository.deleteById(citaId);
    }

    private String generarCodigo() {
        return String.format("%06d", new Random().nextInt(1000000));
    }
}