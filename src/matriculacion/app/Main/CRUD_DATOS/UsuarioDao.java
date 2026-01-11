package matriculacion.app.Main.CRUD_DATOS;

import matriculacion.app.Main.Conexion.Conexion_Base;
import matriculacion.app.Main.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class UsuarioDao {
    // login
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
    //listar usuarios
    public ResultSet listarUsuarios() throws Exception {

        Connection cn = Conexion_Base.conectar();

        String sql = """
            SELECT id, nombre, username, rol, estado
            FROM usuario
            ORDER BY nombre
        """;

        PreparedStatement ps = cn.prepareStatement(sql);
        return ps.executeQuery();
    }
    // cambiar estado
    public void actualizarEstado(int id, String estado) throws Exception {

        Connection cn = Conexion_Base.conectar();

        String sql = "UPDATE usuario SET estado = ? WHERE id = ?";

        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, estado);
        ps.setInt(2, id);
        ps.executeUpdate();

        cn.close();
    }
    // cambiar rol
    public void actualizarRol(int id, String rol) throws Exception {

        Connection cn = Conexion_Base.conectar();

        String sql = "UPDATE usuario SET rol = ? WHERE id = ?";

        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, rol);
        ps.setInt(2, id);
        ps.executeUpdate();

        cn.close();
    }
    // insertar usuario
    public void insertarUsuario(Usuario u) throws Exception {

        Connection cn = Conexion_Base.conectar();

        String sql = """
            INSERT INTO usuario (nombre, cedula, username, password, rol, estado)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, u.getNombre());
        ps.setString(2, u.getCedula());
        ps.setString(3, u.getUsername());
        ps.setString(4, u.getPassword());
        ps.setString(5, u.getRol());
        ps.setString(6, u.getEstado());

        ps.executeUpdate();
        cn.close();
    }
    //obtener user por id
    public Usuario obtenerUsuarioPorId(int id) throws Exception {

        Connection cn = Conexion_Base.conectar();

        String sql = """
            SELECT id, nombre, cedula, username, rol, estado
            FROM usuario
            WHERE id = ?
        """;

        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        Usuario u = null;
        if (rs.next()) {
            u = new Usuario();
            u.setId(rs.getInt("id"));
            u.setNombre(rs.getString("nombre"));
            u.setCedula(rs.getString("cedula"));
            u.setUsername(rs.getString("username"));
            u.setRol(rs.getString("rol"));
            u.setEstado(rs.getString("estado"));
        }

        cn.close();
        return u;
    }
    //actualizar usuario
    public void actualizarUsuario(Usuario u) throws Exception {

        Connection cn = Conexion_Base.conectar();

        String sql = """
            UPDATE usuario
            SET nombre = ?, cedula = ?, username = ?, rol = ?, estado = ?
            WHERE id = ?
        """;

        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, u.getNombre());
        ps.setString(2, u.getCedula());
        ps.setString(3, u.getUsername());
        ps.setString(4, u.getRol());
        ps.setString(5, u.getEstado());
        ps.setInt(6, u.getId());

        ps.executeUpdate();
        cn.close();
    }
}
