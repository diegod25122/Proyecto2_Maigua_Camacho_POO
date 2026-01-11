package matriculacion.app.Main.view;

import matriculacion.app.Main.CRUD_DATOS.*;
import matriculacion.app.Main.model.*;

import javax.swing.*;
import java.awt.*;

public class DetalleTramiteView extends JFrame {

    private JPanel panelPrincipal;
    // Datos del solicitante / trámite
    private JLabel cedulaLabel;
    private JLabel nombreLabel;
    private JLabel tipoLicenciaLabel;

    // Requisitos
    private JCheckBox medicoCheckBox;
    private JCheckBox pagoCheckBox;
    private JCheckBox multasCheckBox;

    // Exámenes
    private JLabel teoricoLabel;
    private JLabel practicoLabel;

    // Licencia
    private JLabel licenciaLabel;


    private int tramiteId;

    public DetalleTramiteView(int tramiteId) {
        this.tramiteId = tramiteId;

        setContentPane(panelPrincipal);
        setTitle("Detalle del Trámite");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        cargarDatos();


        setVisible(true);
    }

    private void cargarDatos() {
        try {
            // 1. Datos del trámite
            TramiteDao TramiteDao = new TramiteDao();
            Tramite tramite = TramiteDao.obtenerPorId(tramiteId);
            SolicitanteDao solicitanteDao = new SolicitanteDao();
            Solicitante s = solicitanteDao.obtenerPorId(tramite.getSolicitanteId());
            cedulaLabel.setText(s.getCedula());
            nombreLabel.setText(s.getNombre());
            tipoLicenciaLabel.setText(tramite.getTipoLicencia());

            // 2. Requisitos
            RequisitosDao reqDao = new RequisitosDao();
            Requisitos req = reqDao.obtenerPorTramite(tramiteId);
            if (req != null) {
                medicoCheckBox.setSelected(req.isCertificadoMedico());
                pagoCheckBox.setSelected(req.isPagoRealizado());
                multasCheckBox.setSelected(req.isMultasCanceladas());
            }

            // 3. Exámenes
            ExamenDao examenDao = new ExamenDao();
            Examen examen = examenDao.obtenerPorTramite(tramiteId); // necesitas este método en tu DAO
            if (examen != null) {
                teoricoLabel.setText(String.valueOf(examen.getNotaTeorica()));
                practicoLabel.setText(String.valueOf(examen.getNotaPractica()));
            } else {
                teoricoLabel.setText("00");
                practicoLabel.setText("00");
            }

            // 4. Licencia
            LicenciaDao licenciaDao = new LicenciaDao();
            Licencia lic = licenciaDao.obtenerPorTramite(tramiteId);
            licenciaLabel.setText(lic != null ? "GENERADA" : "NO GENERADA");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar detalle del trámite",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }
}
