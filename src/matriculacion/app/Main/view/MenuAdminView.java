package matriculacion.app.Main.view;

import javax.swing.*;

public class MenuAdminView extends JFrame{
    private JButton REGISTRARSOLICITANTEButton;
    private JButton VERIFICARREQUISITOSButton;
    private JButton REGISTRAREXÁMENESButton;
    private JButton GESTIÓNDETRÁMITESButton;
    private JButton GENERARLICENCIAButton;
    private JButton CERRARSESIÓNButton;
    private JLabel nombreUser;
    private JLabel rolUser;

   public MenuAdminView(String nombre, String rol){
       initComponents();
       nombreUser.setText(nombre);
       rolUser.setText(rol);

       //Configuracion de la ventana
       setTitle("Menu Admin");
       setSize(300,300);
       setVisible(true);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setLayout(null);
       setLocationRelativeTo(null);
    }

    private void initComponents() {

        nombreUser = new JLabel();
        rolUser = new JLabel();
    }

}
