package matriculacion.app.Main.CRUD_DATOS;

import matriculacion.app.Main.Conexion.Conexion_Base;
import matriculacion.app.Main.model.Usuario;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class UsuarioDao {

    public Usuario login(String user, String pass) throws Exception {

        Connection conexion = Conexion_Base.conectar();

        String sql =
                "SELECT nombre, rol FROM usuario " +
                        "WHERE username = '" + user + "' " +
                        "AND password = '" + pass + "' " +
                        "AND estado = 'ACTIVO'";

        Statement st = conexion.createStatement();
        
        ResultSet rs = st.executeQuery(sql);

        Usuario u = null;
        if (rs.next()) {
            u = new Usuario();
            u.setNombre(rs.getString("nombre"));
            u.setRol(rs.getString("rol"));
        }

        conexion.close();
        return u;
    }
}
