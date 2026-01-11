package matriculacion.app.Main.view;

import matriculacion.app.Main.CRUD_DATOS.TramiteDao;
import matriculacion.app.Main.CRUD_DATOS.RequisitosDao;
import matriculacion.app.Main.model.Requisitos;

import javax.swing.*;

public class RequisitosView extends JFrame {

    private JCheckBox certificadoMedicoCheckBox;
    private JCheckBox pagoRealizadoCheckBox;
    private JCheckBox multasCanceladasCheckBox;
    private JTextArea txtObservaciones;
    private JButton aprobarButton;
    private JButton rechazarButton;
    private JButton regresarButton;
    private JPanel panelPrincipal;

    private int tramiteId;

    public RequisitosView(int tramiteId) {

        this.tramiteId = tramiteId;

        // ✅ VALIDAR ESTADO ANTES DE ABRIR
        try {
            TramiteDao tramiteDao = new TramiteDao();
            String estado = tramiteDao.obtenerEstado(tramiteId);

            if (!"pendiente".equalsIgnoreCase(estado)) {
                JOptionPane.showMessageDialog(
                        null,
                        "No se puede verificar requisitos.\n" +
                                "El trámite se encuentra en estado: " + estado,
                        "Acción no permitida",
                        JOptionPane.WARNING_MESSAGE
                );
                dispose();
                return;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al validar estado del trámite",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            dispose();
            return;
        }

        // ✅ SOLO SI PASA LA VALIDACIÓN
        setContentPane(panelPrincipal);
        setTitle("Verificación de Requisitos");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        aprobarButton.addActionListener(e -> procesar(true));
        rechazarButton.addActionListener(e -> procesar(false));
        regresarButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void procesar(boolean aprobado) {

        boolean certificado = certificadoMedicoCheckBox.isSelected();
        boolean pago = pagoRealizadoCheckBox.isSelected();
        boolean multas = multasCanceladasCheckBox.isSelected();

        if (aprobado && !(certificado && pago && multas)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Para aprobar, todos los requisitos deben cumplirse",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            Requisitos r = new Requisitos();
            r.setTramiteId(tramiteId);
            r.setCertificadoMedico(certificado);
            r.setPagoRealizado(pago);
            r.setMultasCanceladas(multas);
            r.setObservaciones(txtObservaciones.getText());

            RequisitosDao dao = new RequisitosDao();
            dao.guardar(r);

            TramiteDao tramiteDao = new TramiteDao();
            tramiteDao.actualizarEstado(
                    tramiteId,
                    aprobado ? "en_examenes" : "reprobado"
            );

            JOptionPane.showMessageDialog(
                    this,
                    aprobado
                            ? "Requisitos aprobados. Trámite pasa a exámenes"
                            : "Requisitos rechazados. Trámite reprobado",
                    "Proceso completado",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al procesar requisitos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            ex.printStackTrace();
        }
    }
}
