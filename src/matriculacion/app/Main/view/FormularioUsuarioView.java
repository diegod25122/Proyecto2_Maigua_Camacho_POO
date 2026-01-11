package matriculacion.app.Main.view;

import matriculacion.app.Main.CRUD_DATOS.UsuarioDao;
import matriculacion.app.Main.model.Usuario;

import javax.swing.*;

public class FormularioUsuarioView extends JFrame {

    private JPanel panelPrincipal;
    private JTextField txtCedula;
    private JTextField txtNombre;
    private JTextField txtUsername;
    private JPasswordField txtContraseña;
    private JComboBox<String> cmbRol;
    private JComboBox<String> cmbEstado;
    private JButton guardarButton;
    private JButton cancelarButton;

    // si es null -> crear | si tiene valor -> editar
    private Integer idUsuario = null;

    // crear
    public FormularioUsuarioView() {
        inicializar();
    }

    // editar
    public FormularioUsuarioView(int idUsuario) {
        this.idUsuario = idUsuario;
        inicializar();
        cargarUsuario();
    }

    // metodo comun
    private void inicializar() {
        setContentPane(panelPrincipal);
        setTitle("Formulario de Usuario");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        cargarCombos();

        guardarButton.addActionListener(e -> guardar());
        cancelarButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    // combos
    private void cargarCombos() {
        cmbRol.removeAllItems();
        cmbRol.addItem("ADMIN");
        cmbRol.addItem("ANALISTA");

        cmbEstado.removeAllItems();
        cmbEstado.addItem("ACTIVO");
        cmbEstado.addItem("INACTIVO");
    }

    // cargar users
    private void cargarUsuario() {
        try {
            UsuarioDao dao = new UsuarioDao();
            Usuario u = dao.obtenerUsuarioPorId(idUsuario);

            if (u != null) {
                txtCedula.setText(u.getCedula());
                txtNombre.setText(u.getNombre());
                txtUsername.setText(u.getUsername());
                cmbRol.setSelectedItem(u.getRol());
                cmbEstado.setSelectedItem(u.getEstado());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar usuario",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // guardar usuarios
    private void guardar() {

        String cedula = txtCedula.getText().trim();
        String nombre = txtNombre.getText().trim();
        String username = txtUsername.getText().trim();
        String rol = (String) cmbRol.getSelectedItem();
        String estado = (String) cmbEstado.getSelectedItem();

        if (cedula.isEmpty() || nombre.isEmpty() || username.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Complete todos los campos",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            UsuarioDao dao = new UsuarioDao();
            Usuario u = new Usuario();

            u.setCedula(cedula);
            u.setNombre(nombre);
            u.setUsername(username);
            u.setRol(rol);
            u.setEstado(estado);

            if (idUsuario == null) {
                // CREAR
                String password = new String(txtContraseña.getPassword());
                if (password.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Ingrese una contraseña",
                            "Validación",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                u.setPassword(password);
                dao.insertarUsuario(u);
            } else {
                // EDITAR
                u.setId(idUsuario);
                dao.actualizarUsuario(u);
            }

            JOptionPane.showMessageDialog(this,
                    "Usuario guardado correctamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar usuario",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
