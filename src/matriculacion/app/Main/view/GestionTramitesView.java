package matriculacion.app.Main.view;

import matriculacion.app.Main.CRUD_DATOS.TramiteDao;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;

public class GestionTramitesView extends JFrame {

    private JPanel panelPrincipal;

    private JComboBox<String> cmbEstado;
    private JTable tblEstado;
    private JButton filtrarButton;
    private JButton verDetalleButton;
    private JButton registrarExámentButton;
    private JButton registrarLicenciaButton;
    private JButton verificarRequisitosButton;

    public GestionTramitesView() {

        setContentPane(panelPrincipal);
        setTitle("Gestión de Trámites");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        cargarEstados();
        cargarTramites(null);

        filtrarButton.addActionListener(e -> filtrar());

        verDetalleButton.addActionListener(e -> {
            int tramiteId = getTramiteSeleccionado();
            if (tramiteId != -1) {
                new DetalleTramiteView(tramiteId);
            }
        });
        verificarRequisitosButton.addActionListener(e -> {
            int tramiteId = getTramiteSeleccionado();
            if (tramiteId == -1) return;

            try {
                TramiteDao dao = new TramiteDao();
                String estado = dao.obtenerEstado(tramiteId);

                if (!estado.equalsIgnoreCase("pendiente")) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Solo se pueden verificar requisitos\n" +
                                    "cuando el trámite está PENDIENTE",
                            "Acción no permitida",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                new RequisitosView(tramiteId);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al verificar requisitos");
                ex.printStackTrace();
            }
        });

        registrarExámentButton.addActionListener(e -> {
            try {
                int tramiteId = getTramiteSeleccionado();
                if (tramiteId == -1) return;

                TramiteDao dao = new TramiteDao();
                String estado = dao.obtenerEstado(tramiteId);

                if (!estado.equalsIgnoreCase("en_examenes") &&
                        !estado.equalsIgnoreCase("reprobado")) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Solo se puede registrar examen cuando el trámite\n" +
                                    "está EN EXÁMENES o REPROBADO.\nEstado actual: " + estado,
                            "Acción no permitida",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                RegistroExamenesView view = new RegistroExamenesView(tramiteId);
                view.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent e) {
                        cargarTramites(null);
                    }
                });

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al validar trámite");
            }
        });



        registrarLicenciaButton.addActionListener(e -> {
            int tramiteId = getTramiteSeleccionado();
            if (tramiteId != -1) {
                new GeneracionLicenciaView(tramiteId);
            }
        });

        setVisible(true);
    }

    // CARGAR COMBO DE ESTADOS
    private void cargarEstados() {
        cmbEstado.removeAllItems();
        cmbEstado.addItem("TODOS");
        cmbEstado.addItem("pendiente");
        cmbEstado.addItem("en_examenes");
        cmbEstado.addItem("aprobado");
        cmbEstado.addItem("reprobado");
        cmbEstado.addItem("licencia_emitida");
    }

    // FILTRAR TRÁMITES
    private void filtrar() {
        String estado = cmbEstado.getSelectedItem().toString();
        cargarTramites(estado.equals("TODOS") ? null : estado);
    }

    // CARGAR TABLA DE TRÁMITES
    private void cargarTramites(String estado) {
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"ID", "Cédula", "Nombre", "Tipo Licencia", "Estado", "Fecha"}, 0
        );

        try {
            TramiteDao dao = new TramiteDao();
            ResultSet rs = dao.listarTramitesPorEstado(estado);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("tipo_licencia"),
                        rs.getString("estado"),
                        rs.getDate("fecha_solicitud")
                });
            }

            tblEstado.setModel(model);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar trámites",
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // OBTENER ID DEL TRÁMITE SELECCIONADO
    private int getTramiteSeleccionado() {
        int fila = tblEstado.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un trámite",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return -1;
        }
        return Integer.parseInt(tblEstado.getValueAt(fila, 0).toString());
    }
}
