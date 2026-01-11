package matriculacion.app.Main.view;

import matriculacion.app.Main.CRUD_DATOS.LicenciaDao;
import matriculacion.app.Main.CRUD_DATOS.TramiteDao;
import matriculacion.app.Main.model.Licencia;
import matriculacion.app.Main.model.Tramite;
import javax.swing.*;

public class GeneracionLicenciaView extends JFrame {
    private JButton generarButton;
    private JButton exportarPDFButton;
    private JButton regresarButton;
    private JLabel lblEstado;
    private JLabel titulo;
    private JTextField txtNumeroLicencia;
    private JTextField txtFechaEmision;
    private JTextField txtFechaVencimiento;
    private  JPanel panelLicencia;
    private LicenciaDao licenciaDao;
    private TramiteDao tramiteDao;
    private int tramiteId;

    // Constructor con tramiteId
    public GeneracionLicenciaView(int tramiteId) {
        this.tramiteId = tramiteId;
        this.licenciaDao = new LicenciaDao();
        this.tramiteDao = new TramiteDao();

        setContentPane(panelLicencia);
        setTitle("Generación de Licencia");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // hacer los campos de texto solo lectura
        txtNumeroLicencia.setEditable(false);
        txtFechaEmision.setEditable(false);
        txtFechaVencimiento.setEditable(false);

        // eventos de los botones
        generarButton.addActionListener(e -> generarLicencia());
        exportarPDFButton.addActionListener(e -> exportarPDF());
        regresarButton.addActionListener(e -> dispose());

        // deshabilitar exportar hasta generar
        exportarPDFButton.setEnabled(false);

        // cargar datos iniciales
        cargarDatos();

        setVisible(true);
    }

    private void cargarDatos() {
        try {
            // verificar si ya tiene licencia
            if (licenciaDao.tieneLicencia(tramiteId)) {
                Licencia lic = licenciaDao.obtenerPorTramite(tramiteId);
                if (lic != null) {
                    txtNumeroLicencia.setText(lic.getNumeroLicencia());
                    txtFechaEmision.setText(lic.getFechaEmision().toString());
                    txtFechaVencimiento.setText(lic.getFechaVencimiento().toString());
                    lblEstado.setText("Licencia ya generada");
                    generarButton.setEnabled(false);
                    exportarPDFButton.setEnabled(true);
                }
            } else {
                // mostrar numero previo (vista previa)
                String numero = licenciaDao.generarNumeroLicencia();
                txtNumeroLicencia.setText(numero);
                txtFechaEmision.setText("Pendiente");
                txtFechaVencimiento.setText("Pendiente");
                lblEstado.setText("Listo para generar");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar datos: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void generarLicencia() {
        try {
            // verificar que el tramite existe
            Tramite tramite = tramiteDao.obtenerPorId(tramiteId);
            if (tramite == null) {
                JOptionPane.showMessageDialog(this,
                        "El trámite no existe",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // verificar que el tramite este aprobado
            if (!tramite.getEstado().equalsIgnoreCase("aprobado")) {
                JOptionPane.showMessageDialog(this,
                        "Solo se pueden generar licencias para trámites APROBADOS.\n" +
                                "Estado actual: " + tramite.getEstado(),
                        "Trámite no aprobado",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // verificar si ya tiene licencia
            if (licenciaDao.tieneLicencia(tramiteId)) {
                JOptionPane.showMessageDialog(this,
                        "Este trámite ya tiene una licencia generada",
                        "Licencia duplicada",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // confirmar generacion
            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de generar la licencia para el trámite #" + tramiteId + "?",
                    "Confirmar generación",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion != JOptionPane.YES_OPTION) {
                return;
            }

            // generar numero de licencia
            String numeroLic = licenciaDao.generarNumeroLicencia();

            // crear objeto licencia
            Licencia licencia = new Licencia(tramiteId, numeroLic);

            // guardar en base de datos
            if (licenciaDao.generarLicencia(licencia)) {
                // actualizar campos en pantalla
                txtNumeroLicencia.setText(licencia.getNumeroLicencia());
                txtFechaEmision.setText(licencia.getFechaEmision().toString());
                txtFechaVencimiento.setText(licencia.getFechaVencimiento().toString());
                lblEstado.setText("Licencia generada exitosamente");

                // mostrar mensaje de exito
                JOptionPane.showMessageDialog(this,
                        "Licencia generada correctamente\n\n" +
                                "Número: " + licencia.getNumeroLicencia() + "\n" +
                                "Fecha emisión: " + licencia.getFechaEmision() + "\n" +
                                "Válida hasta: " + licencia.getFechaVencimiento(),
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

                // deshabilitar generar y habilitar exportar
                generarButton.setEnabled(false);
                exportarPDFButton.setEnabled(true);
            } else {
                lblEstado.setText("Error al generar licencia");
                JOptionPane.showMessageDialog(this,
                        "Error al generar la licencia. Intente nuevamente.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            lblEstado.setText("Error");
            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void exportarPDF() {
        if (txtNumeroLicencia.getText().isEmpty() ||
                txtNumeroLicencia.getText().equals("Pendiente")) {
            JOptionPane.showMessageDialog(this,
                    "Primero debe generar la licencia",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // funcionalidad pendiente de implementar
        JOptionPane.showMessageDialog(this,
                "Funcionalidad de exportar PDF en desarrollo\n" +
                        "Puede implementarse con librerías como:\n" +
                        "- iText\n" +
                        "- Apache PDFBox\n" +
                        "- JasperReports",
                "Exportar PDF",
                JOptionPane.INFORMATION_MESSAGE);
    }
}