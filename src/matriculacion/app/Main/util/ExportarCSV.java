package matriculacion.app.Main.util;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.FileWriter;

public class ExportarCSV {
    public static void Tabla(JTable tabla) {
        JFileChooser chooser = new JFileChooser();

        if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            try (FileWriter fw = new FileWriter(chooser.getSelectedFile() + ".csv")) {

                TableModel model = tabla.getModel();

                // encabezados
                for (int i = 0; i < model.getColumnCount(); i++) {
                    fw.write(model.getColumnName(i) + ",");
                }
                fw.write("\n");

                // filas
                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        fw.write(model.getValueAt(i, j).toString() + ",");
                    }
                    fw.write("\n");
                }

                JOptionPane.showMessageDialog(null,
                        "CSV exportado correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Error al exportar CSV",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
