package matriculacion.app.Main.view;

import matriculacion.app.Main.util.Sesion;

import javax.swing.*;

public class MenuAnalistaView extends JFrame {

    private JPanel panelPrincipal;
    private JLabel lblTitulo;
    private JLabel nombreUser;
    private JLabel rolUser;

    private JButton btnRegistrar;
    private JButton btnVerificar;
    private JButton btnRegistrarExamen;
    private JButton btnGestTramites;
    private JButton btnGenerarLice;
    private JButton btnCerrarSesion;

    public MenuAnalistaView(String nombre, String rol) {

        // conectamos el form con la pantalla principal
        setContentPane(panelPrincipal);
        setTitle("Menu Analista");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // usamos lo del form
        nombreUser.setText("👤 " + nombre);
        rolUser.setText("Rol: " + rol);

        // Acciones
        btnRegistrar.addActionListener(e -> {
            int idUsuario = Sesion.getUsuario().getId();
            new RegistroSolicitanteView(idUsuario);
        });


        btnVerificar.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Verificar Requisitos"));

        btnRegistrarExamen.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Registrar Exámenes"));

        btnGestTramites.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Gestión de Trámites"));

        btnGenerarLice.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Generar Licencia"));

        btnCerrarSesion.addActionListener(e -> {
            int op = JOptionPane.showConfirmDialog(
                    this,
                    "¿Desea cerrar sesión?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION
            );

            if (op == JOptionPane.YES_OPTION) {
                Sesion.cerrarSesion();
                dispose();
                new LoginView();
            }
        });

        setVisible(true);
    }
}
