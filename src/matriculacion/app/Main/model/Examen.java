package matriculacion.app.Main.model;

public class Examen {
    private int id;
    private int tramiteId;
    private double notaTeorica;
    private double notaPractica;
    private String resultado;

    // Constructor vacio
    public Examen() {
    }

    // Constructor con datos
    public Examen(int tramiteId, double notaTeorica, double notaPractica) {
        this.tramiteId = tramiteId;
        this.notaTeorica = notaTeorica;
        this.notaPractica = notaPractica;
        // calcular el resultado
        double promedio = (notaTeorica + notaPractica) / 2;
        if (promedio >= 14 && notaTeorica >= 14 && notaPractica >= 14) {
            this.resultado = "APROBADO";
        } else {
            this.resultado = "REPROBADO";
        }
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

    public double getNotaTeorica() {
        return notaTeorica;
    }

    public void setNotaTeorica(double notaTeorica) {
        this.notaTeorica = notaTeorica;
    }

    public double getNotaPractica() {
        return notaPractica;
    }

    public void setNotaPractica(double notaPractica) {
        this.notaPractica = notaPractica;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}