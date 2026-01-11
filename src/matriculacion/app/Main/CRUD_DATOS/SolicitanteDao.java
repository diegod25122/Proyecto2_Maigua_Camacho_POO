package matriculacion.app.Main.CRUD_DATOS;

import matriculacion.app.Main.Conexion.Conexion_Base;
import matriculacion.app.Main.model.Solicitante;

import java.sql.*;

public class SolicitanteDao {
    public void insertarSolicitante(Solicitante s) throws Exception {
        Connection cn = Conexion_Base.conectar();
        String sql = """
            INSERT INTO solicitante (cedula, nombre)
            VALUES (?, ?)
        """;
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, s.getCedula());
        ps.setString(2, s.getNombre());

        ps.executeUpdate();
        cn.close();
    }

    public int obtenerIdPorCedula(String cedula) throws Exception {
        Connection cn = Conexion_Base.conectar();
        String sql = "SELECT id FROM solicitante WHERE cedula = ?";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, cedula);
        ResultSet rs = ps.executeQuery();
        int id = 0;
        if (rs.next()) {
            id = rs.getInt("id");
        }
        cn.close();
        return id;
    }
    public ResultSet listarSolicitantesConTramite()throws Exception {
        Connection cn = Conexion_Base.conectar();
        String sql = """
        SELECT s.cedula,
               s.nombre,
               t.tipo_licencia,
               t.fecha_solicitud,
               t.estado
        FROM solicitante s
        JOIN tramite t ON t.solicitante_id = s.id
        ORDER BY t.fecha_solicitud DESC
    """;
        PreparedStatement ps = cn.prepareStatement(sql);
        return ps.executeQuery();
    }
}

