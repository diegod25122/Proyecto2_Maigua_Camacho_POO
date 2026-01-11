package matriculacion.app.Main.CRUD_DATOS;

import matriculacion.app.Main.Conexion.Conexion_Base;
import matriculacion.app.Main.model.Examen;
import java.sql.*;

public class ExamenDao {

    // metodo para guardar examen
    public boolean guardar(Examen examen) {
        try {
            Connection conn = Conexion_Base.conectar();
            String sql = "INSERT INTO examen (tramite_id, nota_teorica, nota_practica, resultado) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, examen.getTramiteId());
            ps.setDouble(2, examen.getNotaTeorica());
            ps.setDouble(3, examen.getNotaPractica());
            ps.setString(4, examen.getResultado());

            int filas = ps.executeUpdate();
            ps.close();
            conn.close();

            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // metodo para verificar si ya existe
    public boolean existeExamen(int tramiteId) {
        try {
            Connection conn = Conexion_Base.conectar();
            String sql = "SELECT COUNT(*) FROM examen WHERE tramite_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, tramiteId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                rs.close();
                ps.close();
                conn.close();
                return count > 0;
            }

            rs.close();
            ps.close();
            conn.close();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public Examen obtenerPorTramite(int tramiteId) throws Exception {
        Connection conn = Conexion_Base.conectar();
        String sql = "SELECT * FROM examen WHERE tramite_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, tramiteId);
        ResultSet rs = ps.executeQuery();

        Examen ex = null;
        if (rs.next()) {
            ex = new Examen();
            ex.setId(rs.getInt("id"));
            ex.setTramiteId(rs.getInt("tramite_id"));
            ex.setNotaTeorica(rs.getDouble("nota_teorica"));
            ex.setNotaPractica(rs.getDouble("nota_practica"));
            ex.setResultado(rs.getString("resultado"));
        }

        rs.close();
        ps.close();
        conn.close();
        return ex;
    }
    public boolean actualizar(Examen examen) {
        try {
            Connection conn = Conexion_Base.conectar();
            String sql = """
            UPDATE examen
            SET nota_teorica = ?, nota_practica = ?, resultado = ?
            WHERE tramite_id = ?
        """;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, examen.getNotaTeorica());
            ps.setDouble(2, examen.getNotaPractica());
            ps.setString(3, examen.getResultado());
            ps.setInt(4, examen.getTramiteId());

            int filas = ps.executeUpdate();
            ps.close();
            conn.close();
            return filas > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}