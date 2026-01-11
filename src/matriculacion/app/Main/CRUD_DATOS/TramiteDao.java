package matriculacion.app.Main.CRUD_DATOS;

import matriculacion.app.Main.Conexion.Conexion_Base;
import matriculacion.app.Main.model.Tramite;
import java.sql.*;

public class TramiteDao {
    public void crearTramite(Tramite t) throws Exception {

        Connection cn = Conexion_Base.conectar();
        String sql = """
        INSERT INTO tramite (solicitante_id, tipo_licencia, estado, created_by)
        VALUES (?, ?, ?, ?)
    """;

        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setInt(1, t.getSolicitanteId());
        ps.setString(2, t.getTipoLicencia());
        ps.setString(3, t.getEstado());
        ps.setInt(4, t.getCreatedBy());

        ps.executeUpdate();
        cn.close();
    }
    public void actualizarEstado(int tramiteId, String estado) throws Exception {

        Connection cn = Conexion_Base.conectar();

        String sql = "UPDATE tramite SET estado = ? WHERE id = ?";

        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, estado);
        ps.setInt(2, tramiteId);

        ps.executeUpdate();
        cn.close();
    }
    public ResultSet listarTramitesPorEstado(String estado) throws Exception {

        Connection cn = Conexion_Base.conectar();

        String sql = """
        SELECT t.id,
               s.cedula,
               s.nombre,
               t.tipo_licencia,
               t.estado,
               t.fecha_solicitud
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

        cn.close();
        return estado;
    }
}
