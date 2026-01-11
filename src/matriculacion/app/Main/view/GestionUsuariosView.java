package matriculacion.app.Main.view;

import matriculacion.app.Main.CRUD_DATOS.UsuarioDao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;

public class GestionUsuariosView extends JFrame{
    private JTable tblUsuarios;
    private JButton btnModificar;
    private JButton btnDesactivar;
    private JButton btnActivar;
    private JButton btnRegresar;
    private JButton btnCambiarRol;
    private JButton btnCrear;
    private JPanel panelPrincipal;


    public GestionUsuariosView() {

        setContentPane(panelPrincipal);
        setTitle("Gestión de Usuarios");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        cargarUsuarios();

        // CREAR
        btnCrear.addActionListener(e ->
                new FormularioUsuarioView()
        );

        // MODIFICAR
        btnModificar.addActionListener(e -> {
            int id = getUsuarioSeleccionado();
            if (id != -1) {
                new FormularioUsuarioView(id);
            }
        });

        // ACTIVAR / DESACTIVAR
        btnActivar.addActionListener(e -> cambiarEstado("ACTIVO"));
        btnDesactivar.addActionListener(e -> cambiarEstado("INACTIVO"));

        // CAMBIAR ROL
        btnCambiarRol.addActionListener(e -> cambiarRol());

        // REGRESAR
        btnRegresar.addActionListener(e -> dispose());

        setVisible(true);
    }

    // Metodoss

    private void cargarUsuarios() {

        String[] columnas = {"ID", "Nombre", "Usuario", "Rol", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        try {
            UsuarioDao dao = new UsuarioDao();
            ResultSet rs = dao.listarUsuarios();

            while (rs.next()) {
                Object[] fila = {
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("username"),
                        rs.getString("rol"),
                        rs.getString("estado")
                };
                modelo.addRow(fila);
            }

            tblUsuarios.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar usuarios",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private int getUsuarioSeleccionado() {
        int fila = tblUsuarios.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un usuario de la tabla",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return -1;
        }
        return (int) tblUsuarios.getValueAt(fila, 0);
    }

    private void cambiarEstado(String estado) {
        int id = getUsuarioSeleccionado();
        if (id == -1) return;

        try {
            UsuarioDao dao = new UsuarioDao();
            dao.actualizarEstado(id, estado);
            cargarUsuarios();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cambiar estado",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cambiarRol() {
        int id = getUsuarioSeleccionado();
        if (id == -1) return;

        String[] roles = {"ADMIN", "ANALISTA"};
        String nuevoRol = (String) JOptionPane.showInputDialog(
                this,
                "Seleccione nuevo rol",
                "Cambiar Rol",
                JOptionPane.QUESTION_MESSAGE,
                null,
                roles,
                roles[0]
        );

        if (nuevoRol == null) return;

        try {
            UsuarioDao dao = new UsuarioDao();
            dao.actualizarRol(id, nuevoRol);
            cargarUsuarios();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cambiar rol",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

