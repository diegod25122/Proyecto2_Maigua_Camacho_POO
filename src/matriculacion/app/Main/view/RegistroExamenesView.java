package matriculacion.app.Main.view;

import matriculacion.app.Main.CRUD_DATOS.ExamenDao;
import matriculacion.app.Main.model.Examen;
import javax.swing.*;

public class RegistroExamenesView extends JFrame {
    private JTextField txtNota;
    private JTextField txtPractica;
    private JButton guardarResultadosButton;
    private JButton regresarButton;
    private JPanel panelPrincipal;
    private JLabel txtResultado;

    private ExamenDao examenDAO;
    private int tramiteId;

    // Constructor simple para cuando NO tienes el tramiteId todavia
    public RegistroExamenesView() {
        this.examenDAO = new ExamenDao();

        setContentPane(panelPrincipal);
        setTitle("Registro de Exámenes");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // eventos de los botones
        guardarResultadosButton.addActionListener(e -> guardarExamen());
        regresarButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    // Constructor con tramiteId (para cuando SI lo tienes)
    public RegistroExamenesView(int tramiteId) {
        this.tramiteId = tramiteId;
        this.examenDAO = new ExamenDao();

        setContentPane(panelPrincipal);
        setTitle("Registro de Exámenes");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // eventos de los botones
        guardarResultadosButton.addActionListener(e -> guardarExamen());
        regresarButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void guardarExamen() {
        try {
            // validar que no esten vacios
            if (txtNota.getText().isEmpty() || txtPractica.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos");
                return;
            }

            // obtener las notas
            double notaTeorica = Double.parseDouble(txtNota.getText());
            double notaPractica = Double.parseDouble(txtPractica.getText());

            // validar que esten entre 0 y 20
            if (notaTeorica < 0 || notaTeorica > 20 || notaPractica < 0 || notaPractica > 20) {
                JOptionPane.showMessageDialog(this, "Las notas deben estar entre 0 y 20");
                return;
            }

            // Si NO se paso tramiteId, pedirlo al usuario
            if (tramiteId == 0) {
                String input = JOptionPane.showInputDialog(this, "Ingrese el ID del trámite:");
                if (input == null || input.isEmpty()) {
                    return;
                }
                tramiteId = Integer.parseInt(input);
            }

            // Si NO hay conexion, mostrar mensaje
            if (examenDAO == null) {
                JOptionPane.showMessageDialog(this, "Error: No se pudo conectar a la base de datos");
                return;
            }

            // verificar si ya existe un examen
            if (examenDAO.existeExamen(tramiteId)) {
                JOptionPane.showMessageDialog(this, "Ya existe un examen para este tramite");
                return;
            }

            // verificar si ya existe un examen
            if (examenDAO.existeExamen(tramiteId)) {
                JOptionPane.showMessageDialog(this, "Ya existe un examen para este tramite");
                return;
            }

            // crear el examen
            Examen examen = new Examen(tramiteId, notaTeorica, notaPractica);

            // guardar
            if (examenDAO.guardar(examen)) {
                txtResultado.setText("Resultado: " + examen.getResultado());
                JOptionPane.showMessageDialog(this, "Examen guardado correctamente");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese solo numeros validos");
        }
    }

    private void limpiar() {
        txtNota.setText("");
        txtPractica.setText("");
        txtResultado.setText("");
    }
}
