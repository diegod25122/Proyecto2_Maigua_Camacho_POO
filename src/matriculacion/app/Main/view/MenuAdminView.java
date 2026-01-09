package matriculacion.app.Main.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuAdminView extends JFrame{
    private JButton btnGestUsuarios;
    private JButton btnReportes;
    private JButton btnCerrarSesión;
    private JLabel nombreUserA;
    private JLabel rolA;
    private JLabel lblTitulo;
    private JButton btnGenerarLicencia;
    private JButton btnGestionTramites;
    private JButton btnRegistrarExamenes;
    private JButton btnVerificar;
    private JButton btnRegistrarSolicitante;

    MenuAdminView(String nombre, String rol){
         initcomponents();
         nombreUserA.setText(nombre);
         rolA.setText(rol);

         //Configuracion de la ventana
        setTitle("Menu Admin");
        setSize(400,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);

        //Pantalla
        //=======ENCABEZADO ========
        JLabel lblTitulo= new JLabel("BIENVENIDO/A");
        lblTitulo.setBounds(20,20,200,25);

        JLabel nombreUserA=new JLabel("👤:"+nombre);
        nombreUserA.setBounds(20,55,300,25);
        JLabel rolA=new JLabel("Rol:"+rol);
        rolA.setBounds(20,80,200,25);

        add(lblTitulo);
        add(nombreUserA);
        add(rolA);

        //===== BOTONES =======
        JButton btnRegistrarSolicitante = new JButton("Registrar Solicitante");
        JButton btnVerificar = new JButton("Verificar");
        JButton btnRegistrarExamenes = new JButton("Registrar Examenes");
        JButton btnGestionTramites = new JButton("Gestion Tramites");
       JButton btnGenerarLicencia = new JButton("Generar Licencia");
        JButton btnGestUsuarios= new  JButton("Gestionar Usuarios");
        JButton btnReportes = new JButton("Reportes y Estadísticas");
        JButton btnCerrarSesión= new JButton("Cerrar Sesión");

        int x = 60;
        int y = 120;
        int w = 280;
        int h = 35;
        int gap = 45;

        btnRegistrarSolicitante.setBounds(x,y,w,h);
        btnVerificar.setBounds(x,y+gap*3,w,h);
        btnRegistrarExamenes.setBounds(x,y+gap*4,w,h);
        btnGestionTramites.setBounds(x,y+gap*5,w,h);
        btnGenerarLicencia.setBounds(x,y+gap*6,w,h);
        btnGestUsuarios.setBounds(x,y,w,h);
        btnReportes.setBounds(x,y+gap,w,h);
        btnCerrarSesión.setBounds(x,y+gap*2,w,h);



        add(btnRegistrarSolicitante);
        add(btnVerificar);
        add(btnRegistrarExamenes);
        add(btnGestionTramites);
        add(btnGenerarLicencia);
        add(btnGestUsuarios);
        add(btnReportes);
        add(btnCerrarSesión);

        btnGestUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnReportes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnCerrarSesión.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
    public void initcomponents(){
        nombreUserA=new JLabel();
        rolA=new JLabel();
    }
}
