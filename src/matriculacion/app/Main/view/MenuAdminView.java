package matriculacion.app.Main.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuAdminView extends JFrame{
    private JButton btnRegistrar;
    private JButton btnVerificar;
    private JButton btnRegistrarExamen;
    private JButton btnGestTramites;
    private JButton btnGenerarLice;
    private JButton btnCerrarSesion;
    private JLabel nombreUser;
    private JLabel rolUser;
    private JLabel lblTitulo;

    public MenuAdminView(String nombre, String rol) {
        initComponents();
        nombreUser.setText(nombre);
        rolUser.setText(rol);

        //Configuracion de la ventana
        setTitle("Menu Admin");
        setSize(400, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        //Pantalla
        //=========== ENCABEZADO ==========
        JLabel lblTitulo = new JLabel("BIENVENIDO/A");
        lblTitulo.setBounds(20, 20, 200, 25);

        nombreUser = new JLabel("👤 " + nombre);
        nombreUser.setBounds(20, 55, 300, 25);

        rolUser = new JLabel("Rol: " + rol);
        rolUser.setBounds(20, 80, 200, 25);

        add(lblTitulo);
        add(nombreUser);
        add(rolUser);

        //=======BOTONES
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnVerificar = new JButton("Verificar");
        JButton btnRegistrarExamen = new JButton("Registrar Exámenes");
        JButton btnGestTramites = new JButton("Gestionar Tramites");
        JButton btnGenerarLice = new JButton("Generar Licencica");
        JButton btnCerrarSesion = new JButton("Cerrar Sesion");

        int x=60;
        int y=120;
        int w=280;
        int h=35;
        int gap=45;

        //Ubicacio de los botones
        btnRegistrar.setBounds(x, y, w, h);
        btnVerificar.setBounds(x, y+gap, w, h);
        btnRegistrarExamen.setBounds(x, y+gap*2, w, h);
        btnGestTramites.setBounds(x, y+gap*3, w, h);
        btnGenerarLice.setBounds(x, y+gap*4, w, h);
        btnCerrarSesion.setBounds(x, y+gap*5, w, h);

        add(btnRegistrar);
        add(btnVerificar);
        add(btnRegistrarExamen);
        add(btnGestTramites);
        add(btnGenerarLice);
        add(btnCerrarSesion);


        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnVerificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnRegistrarExamen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnGestTramites.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnGenerarLice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void initComponents() {

        nombreUser = new JLabel();
        rolUser = new JLabel();
    }

}

