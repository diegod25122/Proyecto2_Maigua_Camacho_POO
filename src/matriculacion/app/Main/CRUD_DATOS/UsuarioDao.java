package matriculacion.app.Main.CRUD_DATOS;

import matriculacion.app.Main.Conexion.Conexion_Base;
import matriculacion.app.Main.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class UsuarioDao {

    public Usuario login(String user, String pass) throws Exception {

        Connection cn = Conexion_Base.conectar();

        String sql ="""
                    SELECT id, nombre, rol
                    FROM usuario
                    WHERE username = ?
                    AND password = ?
                    AND estado = 'ACTIVO' """;

        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, user);
        ps.setString(2, pass);

        
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Usuario u = new Usuario();
            u.setId(rs.getInt("id"));
            u.setNombre(rs.getString("nombre"));
            u.setRol(rs.getString("rol"));
            return u;
        }

        cn.close();
        return null;
    }
}
