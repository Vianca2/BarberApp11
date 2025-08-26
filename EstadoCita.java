package com.barberapp.entity;

/**
 * Enumeración que representa los diferentes estados de una cita en la barbería
 */
public enum EstadoCita {

    /**
     * Cita creada pero aún no confirmada por el barbero
     */
    PENDIENTE("Pendiente de confirmación", "La cita está esperando confirmación del barbero"),

    /**
     * Cita confirmada por el barbero
     */
    CONFIRMADA("Confirmada", "La cita ha sido confirmada y está programada"),

    /**
     * Cita rechazada por el barbero o por falta de disponibilidad
     */
    RECHAZADA("Rechazada", "La cita ha sido rechazada"),

    /**
     * Cita realizada exitosamente
     */
    COMPLETADA("Completada", "El servicio de corte ha sido completado"),

    /**
     * Cita cancelada por el cliente o barbero
     */
    CANCELADA("Cancelada", "La cita ha sido cancelada"),

    /**
     * Cliente no se presentó a la cita
     */
    NO_ASISTIO("No asistió", "El cliente no se presentó a la cita programada");

    private final String displayName;
    private final String descripcion;

    /**
     * Constructor del enum
     * @param displayName Nombre a mostrar en la interfaz
     * @param descripcion Descripción detallada del estado
     */
    EstadoCita(String displayName, String descripcion) {
        this.displayName = displayName;
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el nombre para mostrar en la interfaz de usuario
     * @return String con el nombre a mostrar
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Obtiene la descripción detallada del estado
     * @return String con la descripción
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Verifica si el estado permite modificaciones
     * @return true si se puede modificar, false en caso contrario
     */
    public boolean puedeModificar() {
        return this == PENDIENTE || this == CONFIRMADA;
    }

    /**
     * Verifica si el estado permite cancelación
     * @return true si se puede cancelar, false en caso contrario
     */
    public boolean puedeCancelar() {
        return this == PENDIENTE || this == CONFIRMADA;
    }

    /**
     * Verifica si el estado permite confirmación
     * @return true si se puede confirmar, false en caso contrario
     */
    public boolean puedeConfirmar() {
        return this == PENDIENTE;
    }

    /**
     * Verifica si la cita está activa (ni cancelada ni completada)
     * @return true si está activa, false en caso contrario
     */
    public boolean estaActiva() {
        return this != CANCELADA && this != COMPLETADA && this != NO_ASISTIO;
    }

    /**
     * Verifica si el estado es final (no puede cambiar)
     * @return true si es un estado final, false en caso contrario
     */
    public boolean esFinal() {
        return this == COMPLETADA || this == CANCELADA || this == NO_ASISTIO;
    }

    /**
     * Obtiene el color CSS asociado al estado para mostrar en la interfaz
     * @return String con la clase CSS o color
     */
    public String getColorClass() {
        switch (this) {
            case PENDIENTE:
                return "warning"; // Amarillo/Naranja
            case CONFIRMADA:
                return "primary"; // Azul
            case RECHAZADA:
                return "danger"; // Rojo
            case COMPLETADA:
                return "success"; // Verde
            case CANCELADA:
                return "secondary"; // Gris
            case NO_ASISTIO:
                return "dark"; // Negro/Gris oscuro
            default:
                return "secondary";
        }
    }

    /**
     * Convierte string a EstadoCita de forma segura
     * @param estado String del estado
     * @return EstadoCita correspondiente o PENDIENTE por defecto
     */
    public static EstadoCita fromString(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            return PENDIENTE;
        }

        try {
            return EstadoCita.valueOf(estado.toUpperCase());
        } catch (IllegalArgumentException e) {
            return PENDIENTE;
        }
    }
}