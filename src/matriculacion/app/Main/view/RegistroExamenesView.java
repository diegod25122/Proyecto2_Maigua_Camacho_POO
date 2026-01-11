package matriculacion.app.Main.view;

import matriculacion.app.Main.CRUD_DATOS.TramiteDao;
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
            if (txtNota.getText().isEmpty() || txtPractica.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos");
                return;
            }

            double notaTeorica = Double.parseDouble(txtNota.getText());
            double notaPractica = Double.parseDouble(txtPractica.getText());

            if (notaTeorica < 0 || notaTeorica > 20 ||
                    notaPractica < 0 || notaPractica > 20) {
                JOptionPane.showMessageDialog(this, "Las notas deben estar entre 0 y 20");
                return;
            }

            if (tramiteId == 0) {
                String input = JOptionPane.showInputDialog(this, "Ingrese el ID del trámite:");
                if (input == null || input.isEmpty()) return;
                tramiteId = Integer.parseInt(input);
            }

            TramiteDao tramiteDao = new TramiteDao();
            ExamenDao examenDAO = new ExamenDao();

            String estado = tramiteDao.obtenerEstado(tramiteId);

            // No permitir si ya está aprobado o con licencia
            if (estado.equalsIgnoreCase("aprobado") ||
                    estado.equalsIgnoreCase("licencia_emitida")) {

                JOptionPane.showMessageDialog(
                        this,
                        "Este trámite ya fue aprobado.\nNo se puede volver a rendir examen.",
                        "Acción no permitida",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Examen examen = new Examen(tramiteId, notaTeorica, notaPractica);

            boolean existe = examenDAO.existeExamen(tramiteId);
            boolean ok;

            if (existe) {
                ok = examenDAO.actualizar(examen); //  reintento
            } else {
                ok = examenDAO.guardar(examen);    // primer intento
            }

            if (ok) {
                tramiteDao.actualizarEstado(
                        tramiteId,
                        examen.getResultado().equals("APROBADO")
                                ? "aprobado"
                                : "reprobado"
                );

                JOptionPane.showMessageDialog(
                        this,
                        existe
                                ? "Examen actualizado correctamente"
                                : "Examen registrado correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );

                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el examen");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese solo números válidos");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error del sistema");
            e.printStackTrace();
        }
    }


    private void limpiar() {
        txtNota.setText("");
        txtPractica.setText("");
        txtResultado.setText("");
    }
}
