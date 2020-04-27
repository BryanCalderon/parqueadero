package interfaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import modelo.Parqueadero;

/**
 *
 * @author Bryan Calderon
 */
public class MainFrame extends JFrame {

    Parqueadero parqueadero;
    PanelMatriz panelMatriz;
    public static final String LOCAL_PATH = System.getProperty("user.dir");

    public MainFrame() {
        parqueadero = new Parqueadero();
        parqueadero.loadFile();
        MenuBar bar = new MenuBar();
        Menu menu = new Menu("Menú");
        MenuItem menuItem = new MenuItem("reservar cupo");
        menuItem.addActionListener(listenerReservarCupo);
        menu.add(menuItem);
        bar.add(menu);
        panelMatriz = new PanelMatriz(this);
        panelMatriz.setLayout(new GridLayout((int) parqueadero.countZonas(), 1));
        setTitle("APP parking");
        setSize(900, 600);
        setLayout(new BorderLayout(3, 3));
        setLocationRelativeTo(null);
        setMenuBar(bar);
        addWindowListener(listenerOnClose);
        add(new PanelLogo(this), BorderLayout.NORTH);
        add(panelMatriz, BorderLayout.CENTER);
    }

    private final ActionListener listenerReservarCupo = (ActionEvent e) -> {
        ModalReservarCupo modalReservarCupo = new ModalReservarCupo(this);
        modalReservarCupo.setLocationRelativeTo(null);
        modalReservarCupo.setVisible(Boolean.TRUE);
    };

    private final WindowAdapter listenerOnClose = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            parqueadero.saveData();
            e.getWindow().dispose();
            System.exit(0);
        }
    };

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(Boolean.TRUE);
    }
}
