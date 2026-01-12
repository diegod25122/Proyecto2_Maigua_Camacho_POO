package matriculacion.app.Main.view;

import matriculacion.app.Main.util.Sesion;
import matriculacion.app.Main.util.ImageUtil;
import javax.swing.*;
import java.awt.*;

public class MenuAdminView extends JFrame {


    private JPanel panelPrincipal;

    private JLabel lbltitulo;
    private JLabel nombreUserA;
    private JLabel rolA;

    private JButton btnRegistrarSolicitante;

    private JButton btnGestionTramites;
    private JButton btnGestUsuarios;
    private JButton btnReportes;
    private JButton btnCerrarSesion;
    private JButton btnVerificarRequisitos;
    private JLabel lblImagen;
    private int tramiteId;
    @Override
    public void addNotify() {
        super.addNotify();

        ImageUtil.setImage(
                lblImagen,

                "/matriculacion/app/Main/util/Imagenes/admin.png",
                200,
                200
        );
    }
    public MenuAdminView(String nombre, String rol) {
        // conexion del form con el jframe
        setContentPane(panelPrincipal);
        setTitle("Menu Administrador");
        setSize(400, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // datos del user
        nombreUserA.setText("👤 " + nombre);
        rolA.setText("Rol: " + rol);

        //Estetica de botones e interfaz del admin

        estilizarBoton(btnRegistrarSolicitante);
        estilizarBoton(btnVerificarRequisitos);
        estilizarBoton(btnGestionTramites);
        estilizarBoton(btnGestUsuarios);
        estilizarBoton(btnReportes);
        estilizarBoton(btnCerrarSesion);

        // accion de los botones

        btnRegistrarSolicitante.addActionListener(e -> {
            int idUsuario = Sesion.getUsuario().getId();
            new RegistroSolicitanteView(idUsuario);
        });

        btnVerificarRequisitos.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Ingrese ID del trámite a verificar:");
            if (input != null && !input.isEmpty()) {
                try {
                    int tramiteId = Integer.parseInt(input);
                    new RequisitosView(tramiteId);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID inválido, debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnGestionTramites.addActionListener(e ->
                new GestionTramitesView()
        );


        btnGestUsuarios.addActionListener(e ->
                new GestionUsuariosView()
        );

        btnReportes.addActionListener(e -> new ReportesView());

        //cerrar sesion
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
        boton.setFocusPainted(false);
        boton.setBackground(new java.awt.Color(30, 30, 30));
        boton.setForeground(java.awt.Color.WHITE);
        boton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        boton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
    }
}
