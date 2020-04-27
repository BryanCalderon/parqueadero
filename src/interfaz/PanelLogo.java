package interfaz;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author estudiante
 */
public class PanelLogo extends JPanel {

    ImageIcon imagenExterna = new ImageIcon(MainFrame.LOCAL_PATH + "/src/assets/logo.png");
    MainFrame mainFrame;
    JLabel jLabel;

    public PanelLogo(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        jLabel = new JLabel(imagenExterna);
        setPreferredSize(new Dimension(imagenExterna.getIconWidth(), imagenExterna.getIconHeight()));
        add(jLabel, CENTER_ALIGNMENT);
        setVisible(true);
    }
}
