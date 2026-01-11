package matriculacion.app.Main.CRUD_DATOS;

import matriculacion.app.Main.Conexion.Conexion_Base;
import matriculacion.app.Main.model.Tramite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TramiteDao {

    // Crear un nuevo trámite
    public void crearTramite(Tramite t) throws Exception {
        Connection cn = Conexion_Base.conectar();
        String sql = "INSERT INTO tramite (solicitante_id, tipo_licencia, estado, created_by) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setInt(1, t.getSolicitanteId());
        ps.setString(2, t.getTipoLicencia());
        ps.setString(3, t.getEstado());
        ps.setInt(4, t.getCreatedBy());
        ps.executeUpdate();
        ps.close();
        cn.close();
    }

    // Actualizar estado de un trámite
    public boolean actualizarEstado(int tramiteId, String estado) throws Exception {
        Connection cn = Conexion_Base.conectar();
        String sql = "UPDATE tramite SET estado = ? WHERE id = ?";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, estado);
        ps.setInt(2, tramiteId);
        int filas = ps.executeUpdate();
        ps.close();
        cn.close();
        return filas > 0; // true si se actualizó
    }

    // Listar trámites por estado
    public ResultSet listarTramitesPorEstado(String estado) throws Exception {
        Connection cn = Conexion_Base.conectar();
        String sql = """
            SELECT t.id, s.cedula, s.nombre, t.tipo_licencia, t.estado, t.fecha_solicitud
            FROM tramite t
            JOIN solicitante s ON s.id = t.solicitante_id
            WHERE (? IS NULL OR t.estado = ?)
            ORDER BY t.fecha_solicitud DESC
        """;
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, estado);
        ps.setString(2, estado);
        return ps.executeQuery();
    }

    // Obtener solo el estado de un trámite
    public String obtenerEstado(int tramiteId) throws Exception {
        Connection cn = Conexion_Base.conectar();
        String sql = "SELECT estado FROM tramite WHERE id = ?";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setInt(1, tramiteId);
        ResultSet rs = ps.executeQuery();
        String estado = null;
        if (rs.next()) {
            estado = rs.getString("estado");
        }
        rs.close();
        ps.close();
        cn.close();
        return estado;
    }

    // Obtener un trámite completo por ID
    public Tramite obtenerPorId(int id) throws Exception {
        Connection cn = Conexion_Base.conectar();
        String sql = "SELECT * FROM tramite WHERE id = ?";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        Tramite t = null;
        if (rs.next()) {
            t = new Tramite();
            t.setId(rs.getInt("id"));
            t.setSolicitanteId(rs.getInt("solicitante_id"));
            t.setTipoLicencia(rs.getString("tipo_licencia"));
            t.setFechaSolicitud(rs.getDate("fecha_solicitud").toLocalDate()); // ✅ nuevo
            t.setEstado(rs.getString("estado"));
            t.setCreatedBy(rs.getInt("created_by"));
            t.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime()); // ✅ nuevo
        }

        rs.close();
        ps.close();
        cn.close();
        return t;
    }

}
