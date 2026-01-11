package matriculacion.app.Main.CRUD_DATOS;

import matriculacion.app.Main.model.Requisitos;
import matriculacion.app.Main.Conexion.Conexion_Base;

import java.sql.*;
import java.sql.PreparedStatement;
public class RequisitosDao {
    public void guardar(Requisitos r) throws Exception {

        Connection cn = Conexion_Base.conectar();

        String sql = """
            INSERT INTO requisitos
            (tramite_id, certificado_medico, pago_realizado, multas_canceladas, observaciones)
            VALUES (?, ?, ?, ?, ?)
        """;

        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setInt(1, r.getTramiteId());
        ps.setBoolean(2, r.isCertificadoMedico());
        ps.setBoolean(3, r.isPagoRealizado());
        ps.setBoolean(4, r.isMultasCanceladas());
        ps.setString(5, r.getObservaciones());

        ps.executeUpdate();
        cn.close();
    }
}