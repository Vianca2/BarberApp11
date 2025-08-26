package com.barberapp.repository;

import com.barberapp.entity.Cita;
import com.barberapp.entity.Usuario;
import com.barberapp.entity.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByClienteOrderByFechaHoraDesc(Usuario cliente);
    List<Cita> findByEstadoOrderByFechaHora(EstadoCita estado);
}

//falta EstadoCita en entidad