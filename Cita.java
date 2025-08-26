package com.barberapp.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "citas")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Usuario cliente;

    private LocalDateTime fechaHora;
    private String tipoCorte;
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private EstadoCita estado = EstadoCita.PENDIENTE;

    private String codigoConfirmacion;

    // Constructores
    public Cita() {}

    public Cita(Usuario cliente, LocalDateTime fechaHora, String tipoCorte, String descripcion) {
        this.cliente = cliente;
        this.fechaHora = fechaHora;
        this.tipoCorte = tipoCorte;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getCliente() { return cliente; }
    public void setCliente(Usuario cliente) { this.cliente = cliente; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public String getTipoCorte() { return tipoCorte; }
    public void setTipoCorte(String tipoCorte) { this.tipoCorte = tipoCorte; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public EstadoCita getEstado() { return estado; }
    public void setEstado(EstadoCita estado) { this.estado = estado; }
    public String getCodigoConfirmacion() { return codigoConfirmacion; }
    public void setCodigoConfirmacion(String codigoConfirmacion) { this.codigoConfirmacion = codigoConfirmacion; }
}
