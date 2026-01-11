package matriculacion.app.Main.model;

public class Usuario {
    private int id;
    private String nombre;
    private String rol;

    //Constructor
    public Usuario(){

    }

    //Getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setNombre(String nombre){

        this.nombre=nombre;
    }
    public String getNombre(){
        return nombre;
    }

    public void setRol(String rol){

        this.rol=rol;
    }
    public String getRol(){
        return rol;
    }

}
