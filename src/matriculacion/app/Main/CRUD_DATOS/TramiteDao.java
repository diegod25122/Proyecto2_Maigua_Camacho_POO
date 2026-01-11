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
}
