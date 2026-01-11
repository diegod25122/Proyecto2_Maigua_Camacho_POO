package matriculacion.app.Main.CRUD_DATOS;

import matriculacion.app.Main.Conexion.Conexion_Base;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReporteDao {

    public ResultSet buscar(
            Date desde,
            Date hasta,
            String estado,
            String tipoLicencia,
            String cedula
    ) throws Exception {

        Connection cn = Conexion_Base.conectar();

        StringBuilder sql = new StringBuilder("""
            SELECT s.cedula,
                   s.nombre,
                   t.tipo_licencia,
                   t.estado,
                   t.fecha_solicitud
            FROM solicitante s
            JOIN tramite t ON t.solicitante_id = s.id
            WHERE 1 = 1
        """);

        List<Object> params = new ArrayList<>();

        if (desde != null) {
            sql.append(" AND t.fecha_solicitud >= ?");
            params.add(desde);
        }

        if (hasta != null) {
            sql.append(" AND t.fecha_solicitud <= ?");
            params.add(hasta);
        }

        if (estado != null && !estado.equalsIgnoreCase("TODOS")) {
            sql.append(" AND t.estado = ?");
            params.add(estado);
        }

        if (tipoLicencia != null && !tipoLicencia.equalsIgnoreCase("TODOS")) {
            sql.append(" AND t.tipo_licencia = ?");
            params.add(tipoLicencia);
        }

        if (cedula != null && !cedula.isEmpty()) {
            sql.append(" AND s.cedula LIKE ?");
            params.add("%" + cedula + "%");
        }

        sql.append(" ORDER BY t.fecha_solicitud DESC");

        PreparedStatement ps = cn.prepareStatement(sql.toString());

        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }

        return ps.executeQuery();
    }
}
