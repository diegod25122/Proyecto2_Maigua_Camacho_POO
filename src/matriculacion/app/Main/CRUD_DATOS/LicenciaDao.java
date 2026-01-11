package matriculacion.app.Main.CRUD_DATOS;

import matriculacion.app.Main.Conexion.Conexion_Base;
import matriculacion.app.Main.model.Licencia;
import java.sql.*;
import java.time.LocalDate;

public class LicenciaDao {

    // metodo para generar y guardar licencia
    public boolean generarLicencia(Licencia licencia) throws Exception {
        Connection conn = null;
        try {
            conn = Conexion_Base.conectar();
            // iniciar transaccion
            conn.setAutoCommit(false);

            // insertar licencia
            String sqlLicencia = "INSERT INTO licencia (tramite_id, numero_licencia, fecha_emision, fecha_vencimiento) VALUES (?, ?, ?, ?)";
            PreparedStatement psLicencia = conn.prepareStatement(sqlLicencia);
            psLicencia.setInt(1, licencia.getTramiteId());
            psLicencia.setString(2, licencia.getNumeroLicencia());
            psLicencia.setDate(3, Date.valueOf(licencia.getFechaEmision()));
            psLicencia.setDate(4, Date.valueOf(licencia.getFechaVencimiento()));
            psLicencia.executeUpdate();

            // actualizar estado del tramite a licencia_emitida
            String sqlTramite = "UPDATE tramite SET estado = 'licencia_emitida' WHERE id = ?";
            PreparedStatement psTramite = conn.prepareStatement(sqlTramite);
            psTramite.setInt(1, licencia.getTramiteId());
            psTramite.executeUpdate();

            // confirmar transaccion
            conn.commit();
            conn.setAutoCommit(true);

            psLicencia.close();
            psTramite.close();
            conn.close();

            return true;
        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback(); // deshacer si hay error
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            throw e;
        }
    }

    // metodo para generar numero de licencia unico
    public String generarNumeroLicencia() throws Exception {
        Connection conn = Conexion_Base.conectar();
        String sql = "SELECT COUNT(*) FROM licencia";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        int total = 0;
        if (rs.next()) {
            total = rs.getInt(1);
        }

        rs.close();
        stmt.close();
        conn.close();

        // formato: LIC-2025-000001
        int año = LocalDate.now().getYear();
        String numero = String.format("LIC-%d-%06d", año, total + 1);
        return numero;
    }

    // verificar si tramite ya tiene licencia
    public boolean tieneLicencia(int tramiteId) throws Exception {
        Connection conn = Conexion_Base.conectar();
        String sql = "SELECT COUNT(*) FROM licencia WHERE tramite_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, tramiteId);
        ResultSet rs = ps.executeQuery();

        boolean tiene = false;
        if (rs.next()) {
            tiene = rs.getInt(1) > 0;
        }

        rs.close();
        ps.close();
        conn.close();
        return tiene;
    }

    // obtener licencia por tramite
    public Licencia obtenerPorTramite(int tramiteId) throws Exception {
        Connection conn = Conexion_Base.conectar();
        String sql = "SELECT * FROM licencia WHERE tramite_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, tramiteId);
        ResultSet rs = ps.executeQuery();

        Licencia lic = null;
        if (rs.next()) {
            lic = new Licencia();
            lic.setId(rs.getInt("id"));
            lic.setTramiteId(rs.getInt("tramite_id"));
            lic.setNumeroLicencia(rs.getString("numero_licencia"));
            lic.setFechaEmision(rs.getDate("fecha_emision").toLocalDate());
            lic.setFechaVencimiento(rs.getDate("fecha_vencimiento").toLocalDate());
        }

        rs.close();
        ps.close();
        conn.close();
        return lic;
    }
}
