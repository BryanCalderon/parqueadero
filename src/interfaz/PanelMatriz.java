package interfaz;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import modelo.Factura;
import modelo.Zona;

/**
 *
 * @author Bryan Calderon
 */
public class PanelMatriz extends JPanel {

    private MainFrame mainFrame;
    private static final int ROWS = 2;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    public PanelMatriz(MainFrame interfaz) {
        mainFrame = interfaz;
        initPanels();
    }

    private void initPanels() {
        final List<Zona> zonas = mainFrame.parqueadero.getZonas();
        zonas.forEach(zona -> {
            JPanel panelZona = generateZona(zona);
            if (Objects.nonNull(panelZona)) {
                add(panelZona);
            }
        });
    }

    public void pintarCasilla(Zona zona) {
        final Component[] components = getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i].getName().equals(zona.getZona())) {
                Point location = components[i].getLocation();
                this.remove(components[i]);
                JPanel panelZona = generateZona(zona);
                if (Objects.nonNull(panelZona)) {
                    panelZona.setLocation(location);
                    add(panelZona, i);
                }
            }
        }
        this.updateUI();
    }

    private JPanel generateZona(Zona zona) {
        int columnas = zona.getCapacidad() / ROWS;
        JPanel jPanelZona = null;
        if (columnas > 0) {
            jPanelZona = new JPanel(new GridLayout(ROWS, columnas));
            jPanelZona.setToolTipText(zona.getZona());
            jPanelZona.setName(zona.getZona());
            jPanelZona.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.WHITE)));
            generateEspacio(jPanelZona, zona);
        }
        return jPanelZona;
    }

    private void generateEspacio(JPanel jPanelZona, Zona zona) {
        JPanel jPanelEspacio;
        final List<Factura> facturas = mainFrame.parqueadero.getFacturasByZona(zona.getZona());
        for (int j = 0; j < zona.getCapacidad(); j++) {
            jPanelEspacio = new JPanel();
            jPanelEspacio.setBackground(j < zona.getEspaciosOcupados() ? Color.RED : Color.LIGHT_GRAY);
            if (j < facturas.size()) {
                jPanelEspacio.add(new JLabel(facturas.get(j).getCarro().getPlaca()));
                jPanelEspacio.setName(facturas.get(j).getCarro().getPlaca());
            }
            jPanelEspacio.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 10), new EtchedBorder()));
            jPanelEspacio.addMouseListener(onClicMouse);
            jPanelZona.add(jPanelEspacio);
        }
    }

    MouseListener onClicMouse = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            Component componentAt = e.getComponent().getComponentAt(e.getPoint());

            if (componentAt instanceof JLabel) {
                componentAt = componentAt.getParent();
            }

            JPanel jp = (JPanel) componentAt;

            if (jp.getBackground().equals(Color.RED)) {
                Component[] components = componentAt.getParent().getComponents();

                for (Component component : components) {
                    JPanel componentPanel = (JPanel) component;
                    if (!jp.getName().equals(componentPanel.getName()) && !(((CompoundBorder) componentPanel.getBorder()).getInsideBorder() instanceof EtchedBorder)) {
                        if (((LineBorder) ((CompoundBorder) ((CompoundBorder) componentPanel.getBorder()).getInsideBorder()).getOutsideBorder()).getLineColor().equals(Color.GREEN)) {
                            componentPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 10), new EtchedBorder()));
                        }
                    }
                }

                if (!(((CompoundBorder) jp.getBorder()).getInsideBorder() instanceof EtchedBorder)) {
                    if (((LineBorder) ((CompoundBorder) ((CompoundBorder) jp.getBorder()).getInsideBorder()).getOutsideBorder()).getLineColor().equals(Color.GREEN)) {
                        jp.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 10), new EtchedBorder()));
                    }
                } else {
                    jp.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 10), BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GREEN, 2), new EtchedBorder())));
                }

                if (e.getClickCount() > 1) {
                    int showConfirmDialog = JOptionPane.showConfirmDialog(mainFrame, "Realizar pago?");
                    if (Objects.equals(showConfirmDialog, JOptionPane.OK_OPTION)) {
                        Factura factura = mainFrame.parqueadero.getFacturaByPlaca(jp.getName());
                        factura.calcularTotalPagar();
                        String html = "<html>"
                                + "<body width='%1s'style='text-align:center'><h1>" + factura.getCarro().getPlaca() + "</h1>"
                                + "<p>"
                                + "Hora de llegada: " + DATE_FORMAT.format(factura.getFechaIngreso())
                                + "<br>Tiempo de parqueo: " + factura.getTime()
                                + "<br>Valor a cancelar:  " + factura.getTotalPagar()
                                + "<br><br>"
                                + "</p>";
                        int w = 400;
                        JOptionPane.showMessageDialog(mainFrame, String.format(html, w, w), "Factura #" + factura.getConsecutivo(), JOptionPane.DEFAULT_OPTION);
                        factura.realizarPago();
                        factura.getZona().liberarEspacio();
                        pintarCasilla(factura.getZona());
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    };
}
