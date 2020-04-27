package modelo;

import interfaz.MainFrame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author estudiante
 */
public class Parqueadero {

    private final List<Factura> facturas;
    private final List<Zona> zonas;
    private static final String URL = MainFrame.LOCAL_PATH + "/src/baseDatos.txt";

    public Parqueadero() {
        this.facturas = new LinkedList<>();
        this.zonas = new LinkedList<>();
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    public List<Factura> getFacturasByZona(String zona) {
        return facturas.stream()
                .filter(e -> e.getZona().getZona().equals(zona) && !e.isFacturaCancelada())
                .collect(Collectors.toList());
    }

    public Zona getZona(String zona) {
        return zonas.stream()
                .filter(e -> e.getZona().equals(zona))
                .findFirst()
                .orElse(null);
    }

    public List<Zona> getZonas() {
        return zonas;
    }

    public void addFactura(Factura factura) {
        this.facturas.add(factura);
    }

    public long countFacturas() {
        return this.facturas.size();
    }

    public long countZonas() {
        return this.zonas.size();
    }

    public void addZona(Zona zona) {
        this.zonas.add(zona);
    }

    public Factura getFacturaByPlaca(String placa) {
        return this.facturas.stream().filter(e -> e.getCarro().getPlaca().equals(placa)).findFirst().orElse(null);
    }

    public void reservarCupo(String nombreZona, String placa) {
        final Zona zona = getZona(nombreZona);
        zona.ocuparEspacio();
        addFactura(new Factura(new Carro(placa), zona, (int) countFacturas() + 1));
    }

    /**
     * Carga datos del parqueadero de un archivo.
     */
    public void loadFile() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(new File(URL)));
            String line = reader.readLine();
            if (Objects.nonNull(line) && !line.isEmpty()) {
                String[] split;
                for (int i = 0; i < Integer.parseInt(line); i++) {
                    split = reader.readLine().split(",");
                    addZona(new Zona(split[0].trim(), Double.valueOf(split[1].trim()), Integer.parseInt(split[2].trim())));
                }
            }

            line = reader.readLine();
            if (Objects.nonNull(line) && !line.isEmpty()) {
                Factura fact;
                String[] split;
                Zona zona;
                for (int i = 0; i < Integer.parseInt(line); i++) {
                    split = reader.readLine().split(",");
                    zona = getZona(split[5].trim());
                    fact = new Factura(
                            Integer.parseInt(split[0].trim()),
                            new Carro(split[1].trim()),
                            new Date(Long.parseLong(split[2].trim())),
                            !split[3].trim().isEmpty() ? new Date(Long.parseLong(split[3].trim())) : null,
                            !split[4].trim().isEmpty() ? Double.parseDouble(split[4].trim()) : null,
                            zona,
                            Boolean.parseBoolean(split[6].trim())
                    );
                    addFactura(fact);
                    if (!fact.isFacturaCancelada()) {
                        zona.ocuparEspacio();
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found: " + ex.getMessage());
            saveData();
        } catch (IOException ex) {
            System.out.println("Error reading line: " + ex.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Parqueadero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Guarda los datos del parqueadero en un archivo.
     */
    public void saveData() {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(new File(URL)));
            StringBuilder read = new StringBuilder();
            read.append(countZonas()).append(System.lineSeparator());
            getZonas().forEach(zona -> read.append(zona.getString()).append(System.lineSeparator()));
            read.append(countFacturas()).append(System.lineSeparator());
            getFacturas().forEach(factura -> read.append(factura).append(System.lineSeparator()));
            bufferedWriter.write(read.toString());
            bufferedWriter.flush();
        } catch (IOException ex) {
            System.out.println("Error > " + ex.getMessage());
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Parqueadero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
