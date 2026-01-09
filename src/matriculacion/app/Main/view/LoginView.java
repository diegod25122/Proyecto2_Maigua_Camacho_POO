package matriculacion.app.Main.view;

import matriculacion.app.Main.model.Usuario;
import matriculacion.app.Main.services.UsuarioService;

import javax.swing.*;

public class LoginView extends JFrame {

    private JTextField txtUser;
    private JPasswordField passField;
    private JButton btnIngreso;

    public LoginView() {

        setTitle("Login");
        setSize(300, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblUser = new JLabel("Usuario:");
        JLabel lblPass = new JLabel("Contraseña:");

        txtUser = new JTextField();
        passField = new JPasswordField();
        btnIngreso = new JButton("Ingresar");

        lblUser.setBounds(20, 20, 80, 25);
        txtUser.setBounds(110, 20, 150, 25);

        lblPass.setBounds(20, 60, 80, 25);
        passField.setBounds(110, 60, 150, 25);

        btnIngreso.setBounds(90, 110, 100, 30);

        add(lblUser);
        add(txtUser);
        add(lblPass);
        add(passField);
        add(btnIngreso);

        btnIngreso.addActionListener(e -> {

            String user = txtUser.getText();
            String pass = new String(passField.getPassword());

            UsuarioService service = new UsuarioService();

            try {
                Usuario usuario = service.autenticar(user, pass);
                System.out.println("Nombre: " + usuario.getNombre());
                System.out.println("Rol: " + usuario.getRol());
                if (usuario != null) {
                    if (usuario.getRol().equals("ADMIN")) {
                        new MenuAdminView(
                                usuario.getNombre(),
                                usuario.getRol()
                        ).setVisible(true);
                    } else {
                        new MenuAnalistaView(
                                usuario.getNombre(),
                                usuario.getRol()
                        ).setVisible(true);
                    }

                    dispose(); // cerrar login

                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Usuario o contraseña incorrectos"
                    );
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(
                        null,
                        "Error interno del sistema"
                );
            }
        });
    }
}
