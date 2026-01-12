package matriculacion.app.Main.view;

import matriculacion.app.Main.CRUD_DATOS.UsuarioDao;
import matriculacion.app.Main.model.Usuario;
import matriculacion.app.Main.util.ImageUtil;
import matriculacion.app.Main.util.Sesion;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    // componentes provenientes del form
    private JPanel panelPrincipal;

    private JTextField txtUser;
    private JPasswordField passField;
    private JButton btnIngreso;
    private JLabel lblTitulo;
    private JLabel lblUsuario;
    private JLabel lblPassword;
    private JLabel lblImagen;


    // control de intentos
    private int intentos = 3;
    @Override
    public void addNotify() {
        super.addNotify();

        ImageUtil.setImage(
                lblImagen,

                "/matriculacion/app/Main/util/Imagenes/icono.png",
                80,
                80
        );
    }
    public LoginView() {
       //Decoracion del Login
        esticaLogin();
        // conectamos con el form
        setContentPane(panelPrincipal);
        setTitle("Login");
        setSize(500, 300);
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
                ex.printStackTrace();
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
    private void esticaLogin() {

        // Fuente base
        Font fontTexto = new Font("Segoe UI", Font.PLAIN, 14);
        Font fontTitulo = new Font("Segoe UI", Font.BOLD, 16);
        Font fontBoton  = new Font("Segoe UI", Font.BOLD, 14);

        // ===== TÍTULO =====
        lblTitulo.setFont(fontTitulo);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        // ===== LABELS =====
        lblUsuario.setFont(fontTexto);
        lblPassword.setFont(fontTexto);

        // ===== CAMPOS DE TEXTO =====
        estilizarCampo(txtUser, fontTexto);
        estilizarCampo(passField, fontTexto);

        // ===== BOTÓN =====
        btnIngreso.setFont(fontBoton);
        btnIngreso.setBackground(Color.BLACK);
        btnIngreso.setForeground(Color.WHITE);
        btnIngreso.setFocusPainted(false);
        btnIngreso.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        btnIngreso.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    //Metodo para estilizar los campos de ingreso
    private void estilizarCampo(JTextField campo, Font fuente) {
        campo.setFont(fuente);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
    }


}

