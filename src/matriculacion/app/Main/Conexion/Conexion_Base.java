package matriculacion.app.Main.Conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion_Base {

    public static Connection conectar() {
        try {
            String url = "jdbc:postgresql://db.lvwcvgxiwnwxoopgogmi.supabase.co:5432/postgres";
            String user = "postgres";
            String password = "megustapoo123";

            return DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
