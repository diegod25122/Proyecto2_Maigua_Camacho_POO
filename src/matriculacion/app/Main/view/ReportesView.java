package matriculacion.app.Main.view;

import matriculacion.app.Main.CRUD_DATOS.ReporteDao;
import matriculacion.app.Main.util.ExportarCSV;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Date;
import java.sql.ResultSet;

public class ReportesView extends JFrame{
    private JTextField txtDesde;
    private JTextField txtHasta;
    private JComboBox<String> cmbEstado;
    private JComboBox<String> cmbTipoLicencia;
    private JButton buscarButton;
    private JButton exportarCSVButton;
    private JTextField txtCedula;
    private JTable tblReportes;
    private JPanel panelPrincipal;
    private JLabel lblTotal;
    private JLabel lblPendientes;
    private JLabel lblLicenciasEmitidas;
    private JLabel lblAprobados;

    public ReportesView(){
        setContentPane(panelPrincipal);
        setTitle("Resportes y Estadisticas");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cargarCombos();

        buscarButton.addActionListener(e -> buscar());

        exportarCSVButton.addActionListener(e -> ExportarCSV.Tabla(tblReportes));
        setVisible(true);
    }
    //cargamos combos
    private void cargarCombos(){
        cmbEstado.removeAllItems();
        cmbEstado.addItem("TODOS");
        cmbEstado.addItem("pendiente");
        cmbEstado.addItem("aprobado");
        cmbEstado.addItem("reprobado");
        cmbEstado.addItem("licencia_emitida");

        cmbTipoLicencia.removeAllItems();
        cmbTipoLicencia.addItem("TODOS");
        cmbTipoLicencia.addItem("A");
        cmbTipoLicencia.addItem("B");
        cmbTipoLicencia.addItem("C");
        cmbTipoLicencia.addItem("D");
        cmbTipoLicencia.addItem("E");
        cmbTipoLicencia.addItem("F");
    }
    // buscar
    private void buscar(){
        DefaultTableModel model = new DefaultTableModel(
                new String []{
                        "Cedula",
                        "Nombre",
                        "Tipo Licencia",
                        "Estado",
                        "Fecha"
                },0
        );
        //contadores
        int total = 0;
        int pendientes = 0;
        int aprobados = 0;
        int emitidas = 0;

        try{
            ReporteDao reporteDao = new ReporteDao();
            Date desde = txtDesde.getText().isEmpty()
                    ? null
                    : Date.valueOf(txtDesde.getText());
            Date hasta = txtHasta.getText().isEmpty()
                    ? null
                    : Date.valueOf(txtHasta.getText());
            ResultSet rs = reporteDao.buscar(
                    desde,
                    hasta,
                    cmbEstado.getSelectedItem().toString(),
                    cmbTipoLicencia.getSelectedItem().toString(),
                    txtCedula.getText().trim()
            );
            while(rs.next()){
                String estado = rs.getString("estado");
                model.addRow(new Object[]{
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("tipo_licencia"),
                        estado,
                        rs.getDate("fecha_solicitud")
                });

                //contadores
                total ++;
                switch (estado) {
                    case "pendiente" -> pendientes++;
                    case "aprobado" -> aprobados++;
                    case "licencia_emitida" -> emitidas++;
                }
            }
            tblReportes.setModel(model);
            //mostrar totales
            lblTotal.setText(String.valueOf(total));
            lblPendientes.setText(String.valueOf(pendientes));
            lblAprobados.setText(String.valueOf(aprobados));
            lblLicenciasEmitidas.setText(String.valueOf(emitidas));
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al generar el reporte",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            ex.printStackTrace();
        }
    }
}

