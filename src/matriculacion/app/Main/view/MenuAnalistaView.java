package matriculacion.app.Main.view;

import matriculacion.app.Main.util.ImageUtil;
import matriculacion.app.Main.util.Sesion;

import javax.swing.*;
import java.awt.*;

public class MenuAnalistaView extends JFrame {

    private JPanel panelPrincipal;
    private JLabel lblTitulo;
    private JLabel nombreUser;
    private JLabel rolUser;

    private JButton btnRegistrarSolicitante;
    private JButton btnGestionTramites;

    private JButton btnCerrarSesion;
    private JLabel lblImagen;

    @Override
    public void addNotify() {
        super.addNotify();

        ImageUtil.setImage(
                lblImagen,

                "/matriculacion/app/Main/util/Imagenes/analista.png",
                120,
                120
        );
    }
    public MenuAnalistaView(String nombre, String rol) {

        // conectamos el form con la pantalla principal
        setContentPane(panelPrincipal);
        setTitle("Menu Analista");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //Estetica de botones e interfaz del admin

        estilizarBoton(btnRegistrarSolicitante);
        estilizarBoton(btnGestionTramites);
        estilizarBoton(btnCerrarSesion);
        // usamos lo del form
        nombreUser.setText("👤 " + nombre);
        rolUser.setText("Rol: " + rol);

        // Acciones
        btnRegistrarSolicitante.addActionListener(e -> {
            int idUsuario = Sesion.getUsuario().getId();
            new RegistroSolicitanteView(idUsuario);
        });

        btnGestionTramites.addActionListener(e ->
                new GestionTramitesView()
        );


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
    //Metodo para la estetica de los botones
    private void estilizarBoton(JButton boton) {
        boton.setBackground(new Color(25, 25, 25));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
    }

}
