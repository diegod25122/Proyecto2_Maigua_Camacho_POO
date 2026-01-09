package matriculacion.app.Main.Conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion_Base {

    private static final String URL =
            "jdbc:postgresql://db.lvwcvgxiwnwxoopgogmi.supabase.co:5432/postgres?sslmode=require";

    private static final String USER = "postgres";
    private static final String PASS = "megustapoo123";

    public static Connection conectar() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace(); // 👈 MUESTRA ERROR REAL
            return null;
        }
    }
}
