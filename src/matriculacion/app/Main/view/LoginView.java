package matriculacion.app.Main.view;

import matriculacion.app.Main.Conexion.Conexion_Base;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//Librerias de conexión
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;


public class LoginView extends JFrame {
    private JTextField txtUser;
    private JButton btnIngreso;
    private JPasswordField passField;
    private JLabel intentos;
    public LoginView() {
        //Estilos de la venta
        setTitle("Login");
        setSize(300, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        //Componentes de la ventana
        JLabel lblUser=new JLabel("Usuario:");
        JLabel lblPass=new JLabel("Contraseña:");

        //Objetos
        txtUser=new JTextField();
        passField=new JPasswordField();
        btnIngreso=new JButton("Ingresar");
        //Estilos de los objetos
        lblUser.setBounds(20,20,80,25);
        txtUser.setBounds(110,20,150,25);

        lblPass.setBounds(20,60,80,25);
        passField.setBounds(110,60,150,25);

        btnIngreso.setBounds(90,110,100,30);

        //Añadir los botones al Panel
        add(lblUser);
        add(txtUser);
        add(lblPass);
        add(passField);
        add(btnIngreso);

        btnIngreso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Varibles
                String user = txtUser.getText();
                String pass = new  String(passField.getPassword());
                //Procedimiento
                try {
                    //Conexion
                    Connection conexion = Conexion_Base.conectar();
                    String sqlR="select nombre, rol  from usuario " +
                            "where username = '" + user + "' " +
                            "AND password = '" + pass + "'" +
                            "AND estado = 'ACTIVO'";
                    Statement st = conexion.createStatement(); //Mensaje para la BD
                    //Respuesta
                    ResultSet rs = st.executeQuery(sqlR);

                    //Validacion de credenciales
                    if (rs.next()) {
                        String nombre = rs.getString("nombre");
                        String rol = rs.getString("rol");

                        if (rol.equals("ADMIN")) {
                            new MenuAnalistaView(nombre, rol).setVisible(true);
                        }
                        else {

                        }
                        dispose();

                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
                    }
                    conexion.close();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
                }
            }
        });
    }
}

