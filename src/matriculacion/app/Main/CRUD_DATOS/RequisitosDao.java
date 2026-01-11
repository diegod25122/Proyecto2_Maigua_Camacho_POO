package matriculacion.app.Main.CRUD_DATOS;

import matriculacion.app.Main.model.Requisitos;
import matriculacion.app.Main.Conexion.Conexion_Base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RequisitosDao {

    // Guardar requisitos en la base
    public void guardar(Requisitos r) throws Exception {
        Connection cn = Conexion_Base.conectar();

        String sql = "INSERT INTO requisitos " +
                "(tramite_id, certificado_medico, pago_realizado, multas_canceladas, observaciones) " +
                "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setInt(1, r.getTramiteId());
        ps.setBoolean(2, r.isCertificadoMedico());
        ps.setBoolean(3, r.isPagoRealizado());
        ps.setBoolean(4, r.isMultasCanceladas());
        ps.setString(5, r.getObservaciones());

        ps.executeUpdate();

        ps.close();
        cn.close();
    }

    // Obtener requisitos por tramiteId
    public Requisitos obtenerPorTramite(int tramiteId) throws Exception {
        Connection cn = Conexion_Base.conectar();

        String sql = "SELECT certificado_medico, pago_realizado, multas_canceladas, observaciones " +
                "FROM requisitos WHERE tramite_id = ?";

        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setInt(1, tramiteId);

        ResultSet rs = ps.executeQuery();
        Requisitos req = null;

        if (rs.next()) {
            req = new Requisitos();
            req.setTramiteId(tramiteId);
            req.setCertificadoMedico(rs.getBoolean("certificado_medico"));
            req.setPagoRealizado(rs.getBoolean("pago_realizado"));
            req.setMultasCanceladas(rs.getBoolean("multas_canceladas"));
            req.setObservaciones(rs.getString("observaciones"));
        }

        rs.close();
        ps.close();
        cn.close();

        return req;
    }
}
