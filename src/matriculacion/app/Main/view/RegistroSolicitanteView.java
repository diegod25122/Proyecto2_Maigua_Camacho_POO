package matriculacion.app.Main.view;

import matriculacion.app.Main.CRUD_DATOS.SolicitanteDao;
import matriculacion.app.Main.CRUD_DATOS.TramiteDao;
import matriculacion.app.Main.model.Solicitante;
import matriculacion.app.Main.model.Tramite;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.time.LocalDate;

public class RegistroSolicitanteView extends JFrame {
    // panel principal del form
    private JPanel panelPrincipal;
    // campos del formulario
    private JTextField txtCedula;
    private JTextField txtNombre;
    private JComboBox<String> cmbTipoLicencia;
    private JLabel lblFechaSolicitud;

    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton regresarButton;

    // tabla de registrados
    private JTable tblSolicitantes;
    private JScrollPane scrollSolicitantes;

    public RegistroSolicitanteView(int idUsuarioLogueado) {
        // conectamos al form
        setContentPane(panelPrincipal);
        setTitle("Registro de Solicitante");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // fecha automatica
        lblFechaSolicitud.setText(LocalDate.now().toString());

        //tipos de licencia

        cmbTipoLicencia.removeAllItems();
        cmbTipoLicencia.addItem("A");
        cmbTipoLicencia.addItem("B");
        cmbTipoLicencia.addItem("C");
        cmbTipoLicencia.addItem("D");
        cmbTipoLicencia.addItem("E");
        cmbTipoLicencia.addItem("F");

        //boton guardar
        guardarButton.addActionListener(e -> {
            String cedula = txtCedula.getText().trim();
            String nombre = txtNombre.getText().trim();
            String tipoLicencia = (String) cmbTipoLicencia.getSelectedItem();
            if (cedula.isEmpty() || cedula.length() < 10 || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Numero de cedula incorrecto o nombre invalido",
                        "Validacion",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            try {
                //creamos al solicitante
                Solicitante s = new Solicitante();
                s.setCedula(cedula);
                s.setNombre(nombre);

                SolicitanteDao dao = new SolicitanteDao();
                dao.insertarSolicitante(s);

                //obtenemos el id del solicitante
                int idSolicitante = dao.obtenerIdPorCedula(cedula);

                //crear tramite
                Tramite t = new Tramite();
                t.setSolicitanteId(idSolicitante);
                t.setTipoLicencia(tipoLicencia);
                t.setEstado("pendiente");
                t.setCreatedBy(idUsuarioLogueado);

                TramiteDao tramiteDao = new TramiteDao();
                tramiteDao.crearTramite(t);
                // refresh ba la tabla
                cargarSolicitantes();

                JOptionPane.showMessageDialog(
                        this,
                        "Solicitante y trámite registrados correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );

                limpiarCampos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al registrar solicitante",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // BOTÓN LIMPIAR
        limpiarButton.addActionListener(e -> limpiarCampos());

        // BOTÓN REGRESAR
        regresarButton.addActionListener(e -> dispose());

        setVisible(true);
        cargarSolicitantes();
    }

    private void limpiarCampos() {
        txtCedula.setText("");
        txtNombre.setText("");
        cmbTipoLicencia.setSelectedIndex(0);
    }

    private void cargarSolicitantes() {
        String[] columnas = {
                "Cédula",
                "Nombre",
                "Tipo Licencia",
                "Fecha Solicitud",
                "Estado"
        };
        DefaultTableModel model = new DefaultTableModel(null, columnas);
        try {
            SolicitanteDao dao = new SolicitanteDao();
            ResultSet rs = dao.listarSolicitantesConTramite();
            while (rs.next()) {
                Object[] fila = {
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("tipo_licencia"),
                        rs.getDate("fecha_solicitud"),
                        rs.getString("estado")
                };
                model.addRow(fila);
            }
            tblSolicitantes.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar solicitantes",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
