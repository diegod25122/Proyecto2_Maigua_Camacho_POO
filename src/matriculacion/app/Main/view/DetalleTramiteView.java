package matriculacion.app.Main.view;

import javax.swing.*;
import java.awt.*;

public class DetalleTramiteView {
    private JPanel panelPrincipal;
    private JCheckBox médicoCheckBox;
    private JCheckBox pagoCheckBox;
    private JCheckBox multasCheckBox;
    private JButton guardarRequisitosButton;
    private JButton guardarNotasButton;
    private JButton generarLicenciaButton;

    // colores a la ventana
    public DetalleTramiteView() {
        // color de fondo
        panelPrincipal.setBackground(new Color(240, 80, 34, 255));
        // checkboxes
        médicoCheckBox.setOpaque(false);
        pagoCheckBox.setOpaque(false);
        multasCheckBox.setOpaque(false);

        médicoCheckBox.setForeground(Color.white);
        pagoCheckBox.setForeground(Color.white);
        multasCheckBox.setForeground(Color.white);

        // botones
        estilizarBoton(guardarRequisitosButton, new Color (5, 5, 5));
        estilizarBoton(guardarNotasButton, new Color (5, 5, 5));
        estilizarBoton(generarLicenciaButton, new Color (5, 5, 5));
    }
    private void  estilizarBoton(JButton boton, Color color) {
        boton.setBackground(color);
        boton.setForeground(color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 13));
    }
    public void mostrar(){
        JFrame frame = new JFrame("Detalle del Trámite");
        frame.setContentPane(panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
