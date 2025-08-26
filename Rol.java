package com.barberapp.entity;

import java.util.Arrays;
import java.util.List;

/**
 * Enumeración que representa los diferentes roles de usuario en el sistema de barbería
 */
public enum Rol {

    /**
     * Cliente que puede agendar citas
     */
    CLIENTE("Cliente", "Usuario que puede agendar y gestionar sus citas",
            Arrays.asList("AGENDAR_CITA", "VER_CITAS_PROPIAS", "CANCELAR_CITA_PROPIA", "MODIFICAR_PERFIL")),

    /**
     * Barbero que puede gestionar citas y atender clientes
     */
    BARBERO("Barbero", "Profesional que puede gestionar todas las citas y atender clientes",
            Arrays.asList("VER_TODAS_CITAS", "CONFIRMAR_CITA", "RECHAZAR_CITA", "COMPLETAR_CITA",
                    "MARCAR_NO_ASISTIO", "GESTIONAR_HORARIOS", "VER_REPORTES", "MODIFICAR_PERFIL"));

    private final String displayName;
    private final String descripcion;
    private final List<String> permisos;

    /**
     * Constructor del enum
     * @param displayName Nombre a mostrar en la interfaz
     * @param descripcion Descripción del rol
     * @param permisos Lista de permisos asociados al rol
     */
    Rol(String displayName, String descripcion, List<String> permisos) {
        this.displayName = displayName;
        this.descripcion = descripcion;
        this.permisos = permisos;
    }

    /**
     * Obtiene el nombre para mostrar en la interfaz de usuario
     * @return String con el nombre a mostrar
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Obtiene la descripción del rol
     * @return String con la descripción
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Obtiene los permisos asociados al rol
     * @return Lista de permisos
     */
    public List<String> getPermisos() {
        return permisos;
    }

    /**
     * Verifica si el rol tiene un permiso específico
     * @param permiso El permiso a verificar
     * @return true si tiene el permiso, false en caso contrario
     */
    public boolean tienePermiso(String permiso) {
        return permisos.contains(permiso);
    }

    /**
     * Verifica si es rol de cliente
     * @return true si es cliente, false en caso contrario
     */
    public boolean esCliente() {
        return this == CLIENTE;
    }

    /**
     * Verifica si es rol de barbero
     * @return true si es barbero, false en caso contrario
     */
    public boolean esBarbero() {
        return this == BARBERO;
    }

    /**
     * Verifica si puede gestionar citas de otros usuarios
     * @return true si puede gestionar citas ajenas, false en caso contrario
     */
    public boolean puedeGestionarCitas() {
        return this == BARBERO;
    }

    /**
     * Verifica si puede ver reportes del sistema
     * @return true si puede ver reportes, false en caso contrario
     */
    public boolean puedeVerReportes() {
        return this == BARBERO;
    }

    /**
     * Verifica si puede agendar citas
     * @return true si puede agendar, false en caso contrario
     */
    public boolean puedeAgendarCitas() {
        return this == CLIENTE;
    }

    /**
     * Obtiene el color CSS asociado al rol para mostrar en la interfaz
     * @return String con la clase CSS o color
     */
    public String getColorClass() {
        switch (this) {
            case CLIENTE:
                return "primary"; // Azul
            case BARBERO:
                return "success"; // Verde
            default:
                return "secondary";
        }
    }

    /**
     * Obtiene el icono asociado al rol
     * @return String con el nombre del icono
     */
    public String getIcono() {
        switch (this) {
            case CLIENTE:
                return "fas fa-user"; // Icono de usuario
            case BARBERO:
                return "fas fa-cut"; // Icono de tijeras/barbero
            default:
                return "fas fa-question";
        }
    }

    /**
     * Convierte string a Rol de forma segura
     * @param rol String del rol
     * @return Rol correspondiente o CLIENTE por defecto
     */
    public static Rol fromString(String rol) {
        if (rol == null || rol.trim().isEmpty()) {
            return CLIENTE;
        }

        try {
            return Rol.valueOf(rol.toUpperCase());
        } catch (IllegalArgumentException e) {
            return CLIENTE;
        }
    }

    /**
     * Obtiene la URL del dashboard según el rol
     * @return String con la URL del dashboard
     */
    public String getDashboardUrl() {
        switch (this) {
            case CLIENTE:
                return "/cliente/dashboard";
            case BARBERO:
                return "/barbero/dashboard";
            default:
                return "/";
        }
    }
}