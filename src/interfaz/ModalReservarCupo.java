package interfaz;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import modelo.Zona;

/**
 *
 * @author usuario
 */
public class ModalReservarCupo extends JDialog {

    MainFrame mainFrame;

    public ModalReservarCupo(MainFrame frame) {
        mainFrame = frame;
        setTitle("Reservas");
        setLocation(new Point(400, 400));
        setSize(250, 250);
        setLayout(new GridLayout(4, 1));

//          *******FIELD PLACA*******
        JPanel fieldPane = new JPanel();
        fieldPane.setBorder(new EmptyBorder(0, 10, 0, 10));
        fieldPane.setLayout(new GridLayout(2, 1));
        JLabel labelPlaca = new JLabel("Ingrese la placa");
        JTextField fieldPlaca = new JTextField(7);
        fieldPane.add(labelPlaca);
        fieldPane.add(fieldPlaca);
//          *******END FIELD PLACA*******

//          *******FIELD disponibilidad*******
        JPanel labelPane = new JPanel();
        JLabel jLabel = new JLabel("Hay disponibilidad");
        labelPane.add(jLabel);
//          *******END FIELD disponibilidad*******

//          *******BOX ZONAS*******
        JPanel comboPane = new JPanel();
        comboPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        comboPane.setLayout(new GridLayout(0, 1));
        JComboBox selectZona = new JComboBox(mainFrame.parqueadero.getZonas().toArray());
        Zona zonaDefault = (Zona) selectZona.getSelectedItem();
        boolean dispo = zonaDefault.hayDisponibilidad();

        jLabel.setForeground(dispo ? Color.GREEN : Color.RED);
        jLabel.setText((!dispo ? "NO " : "") + jLabel.getText());
        comboPane.add(selectZona);

//          *******END BOX ZONAS*******            
//          *******Create a button*******
        JPanel buttonPane = new JPanel();
        JButton button = new JButton("Guardar");
        button.setEnabled(dispo && !fieldPlaca.getText().isEmpty());
        buttonPane.add(button);
//          *******END Create a button*******

        button.addActionListener((ActionEvent e1) -> {
            Zona zonaSelected = (Zona) selectZona.getSelectedItem();
            mainFrame.parqueadero.reservarCupo(zonaSelected.getZona(), fieldPlaca.getText());
            mainFrame.panelMatriz.pintarCasilla(zonaSelected);
            dispose();
        });

        fieldPlaca.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                button.setEnabled(dispo && !fieldPlaca.getText().isEmpty());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                button.setEnabled(dispo && !fieldPlaca.getText().isEmpty());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        selectZona.addActionListener((ActionEvent e1) -> {
            Zona zonaSelected = (Zona) selectZona.getSelectedItem();
            boolean hayDisponibilidad = zonaSelected.hayDisponibilidad();
            jLabel.setForeground(hayDisponibilidad ? Color.GREEN : Color.RED);
            jLabel.setText((!hayDisponibilidad ? "NO " : "") + jLabel.getText());
            button.setEnabled(hayDisponibilidad && !fieldPlaca.getText().isEmpty());
        });

        add(fieldPane);
        add(comboPane);
        add(labelPane);
        add(buttonPane);
    }

}
