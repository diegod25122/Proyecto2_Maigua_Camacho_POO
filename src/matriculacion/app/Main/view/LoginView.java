package matriculacion.app.Main.view;

import matriculacion.app.Main.CRUD_DATOS.UsuarioDao;
import matriculacion.app.Main.model.Usuario;
import matriculacion.app.Main.util.Sesion;

import javax.swing.*;

public class LoginView extends JFrame {

    // componentes provenientes del form
    private JPanel panelPrincipal;

    private JTextField txtUser;
    private JPasswordField passField;
    private JButton btnIngreso;


    // control de intentos
    private int intentos = 3;

    public LoginView() {

        // conectamos con el form
        setContentPane(panelPrincipal);
        setTitle("Login");
        setSize(440, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        // usamos enter para ingresar
        getRootPane().setDefaultButton(btnIngreso);

        // login
        btnIngreso.addActionListener(e -> {

            String user = txtUser.getText();
            String pass = new String(passField.getPassword());

            try {
                UsuarioDao dao = new UsuarioDao();
                Usuario u = dao.login(user, pass);

                if (u != null) {
                    Sesion.setUsuario(u);
                    if (u.getRol().equalsIgnoreCase("ADMIN")) {
                        new MenuAdminView(u.getNombre(), u.getRol());
                    } else if (u.getRol().equalsIgnoreCase("ANALISTA")) {
                        new MenuAnalistaView(u.getNombre(), u.getRol());
                    }

                    dispose();

                } else {
                    intentos--;

                    JOptionPane.showMessageDialog(
                            this,
                            "Usuario o contraseña incorrectos\nIntentos Restantes: "+intentos,
                            "Error de autenticación",
                            JOptionPane.WARNING_MESSAGE
                    );

                    if (intentos == 0) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Ha superado el número máximo de intentos.\nLa aplicación se cerrará.",
                                "Acceso bloqueado",
                                JOptionPane.ERROR_MESSAGE
                        );
                        System.exit(0);
                    }
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error de conexión con la base de datos",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        setVisible(true);
    }
}
