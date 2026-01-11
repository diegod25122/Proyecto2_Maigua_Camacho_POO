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
    private JButton reqOKButton;
    private JButton registrarExámentButton;
    private JButton registrarLicenciaButton;

    public GestionTramitesView() {

        setContentPane(panelPrincipal);
        setTitle("Gestión de Trámites");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        cargarEstados();
        cargarTramites(null);

        filtrarButton.addActionListener(e -> filtrar());

        verDetalleButton.addActionListener(e -> verDetalle());

        reqOKButton.addActionListener(e -> {
            abrirRequisitos();
        });


        registrarExámentButton.addActionListener(e -> abrirExamenes());

        registrarLicenciaButton.addActionListener(e -> generarLicencia());

        setVisible(true);
    }


    // CARGAR COMBOS
    private void cargarEstados() {
        cmbEstado.removeAllItems();
        cmbEstado.addItem("TODOS");
        cmbEstado.addItem("pendiente");
        cmbEstado.addItem("en_examenes");
        cmbEstado.addItem("aprobado");
        cmbEstado.addItem("reprobado");
        cmbEstado.addItem("licencia_emitida");
    }


    // FILTRAR

    private void filtrar() {
        String estado = cmbEstado.getSelectedItem().toString();
        cargarTramites(estado.equals("TODOS") ? null : estado);
    }


    // CARGAR TABLA

    private void cargarTramites(String estado) {

        DefaultTableModel model = new DefaultTableModel(
                new String[]{
                        "ID",
                        "Cédula",
                        "Nombre",
                        "Tipo Licencia",
                        "Estado",
                        "Fecha"
                }, 0
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
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    // OBTENER TRAMIITE SELECCIONADO

    private int getTramiteSeleccionado() {
        int fila = tblEstado.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un trámite",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return -1;
        }
        return Integer.parseInt(tblEstado.getValueAt(fila, 0).toString());
    }

    // ACCIONES

    private void verDetalle() {
        int id = getTramiteSeleccionado();
        if (id != -1) {
            JOptionPane.showMessageDialog(this,
                    "Detalle del trámite ID: " + id);
        }
    }

    private void abrirRequisitos() {
        int id = getTramiteSeleccionado();
        if (id != -1) {
            new RequisitosView(id);
        }
    }




    // FALTA QUE VALIDES EL BOTON REGISTRAR EXAMEN Y LICENCIA
    private void abrirExamenes() {
        int id = getTramiteSeleccionado();
        if (id != -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Abrir registro de exámenes para trámite ID: " + id
            );
        }
    }

    private void generarLicencia() {
        int id = getTramiteSeleccionado();
        if (id != -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Generar licencia para trámite ID: " + id
            );
        }
    }
}