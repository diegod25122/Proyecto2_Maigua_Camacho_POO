package matriculacion.app.Main.util;

import matriculacion.app.Main.model.Usuario;
public class Sesion {
    private static Usuario usuarioLogueado;
    public static void setUsuario(Usuario u){
        usuarioLogueado = u;
    }
    public static Usuario getUsuario() {
        return usuarioLogueado;
    }
    public static void cerrarSesion() {
        usuarioLogueado = null;
    }
}
