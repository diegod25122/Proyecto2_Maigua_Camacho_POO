package matriculacion.app.Main.model;

import java.time.LocalDate;

public class Licencia {
    private int id;
    private int tramiteId;
    private String numeroLicencia;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;

    // Constructor vacio
    public Licencia() {
    }

    // Constructor con datos basicos
    public Licencia(int tramiteId, String numeroLicencia) {
        this.tramiteId = tramiteId;
        this.numeroLicencia = numeroLicencia;
        this.fechaEmision = LocalDate.now();
        this.fechaVencimiento = this.fechaEmision.plusYears(5); // 5 años despues
    }

    // getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTramiteId() {
        return tramiteId;
    }

    public void setTramiteId(int tramiteId) {
        this.tramiteId = tramiteId;
    }

    public String getNumeroLicencia() {
        return numeroLicencia;
    }

    public void setNumeroLicencia(String numeroLicencia) {
        this.numeroLicencia = numeroLicencia;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
}
