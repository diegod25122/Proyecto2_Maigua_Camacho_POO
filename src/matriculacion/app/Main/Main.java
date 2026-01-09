package matriculacion.app.Main;
import matriculacion.app.Main.Conexion.Conexion_Base;
import matriculacion.app.Main.view.DetalleTramiteView;
import matriculacion.app.Main.view.LoginView;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hola");
        new LoginView().setVisible(true);

}
}