package matriculacion.app.Main.util;

import javax.swing.*;
import java.awt.*;

public class ImageUtil {

    public static void setImage(
            JLabel label,
            String path,
            int width,
            int height
    ) {
        ImageIcon icon = new ImageIcon(
                ImageUtil.class.getResource(path)
        );

        Image img = icon.getImage().getScaledInstance(
                width,
                height,
                Image.SCALE_SMOOTH
        );

        label.setIcon(new ImageIcon(img));
        label.setText("");
    }
}
