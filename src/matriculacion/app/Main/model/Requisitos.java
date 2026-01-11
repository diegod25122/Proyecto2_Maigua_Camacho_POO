package matriculacion.app.Main.model;

public class Requisitos {
    private int tramiteId;
    private boolean certificadoMedico;
    private boolean multasCanceladas;
    private boolean pagoRealizado;
    private String observaciones;

    //getters y setters
    public int getTramiteId() {
        return tramiteId;
    }
    public void setTramiteId(int tramiteId) {
        this.tramiteId = tramiteId;
    }
    public boolean isCertificadoMedico() {
        return certificadoMedico;
    }
    public void setCertificadoMedico(boolean certificadoMedico) {
        this.certificadoMedico = certificadoMedico;
    }
    public boolean isMultasCanceladas() {
        return multasCanceladas;
    }
    public void setMultasCanceladas(boolean multasCanceladas) {
        this.multasCanceladas = multasCanceladas;
    }
    public boolean isPagoRealizado() {
        return pagoRealizado;
    }
    public void setPagoRealizado(boolean pagoRealizado) {
        this.pagoRealizado = pagoRealizado;
    }
    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}

