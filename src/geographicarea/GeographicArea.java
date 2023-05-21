/**************************************
 * Matricola    Cognome     Nome
 * 754530       Galimberti  Riccardo
 * 753747       Masolo      Carlos
 * 755152       Paredi      Giacomo
 * 753252       Radice      Lorenzo
 * Sede: Como
***************************************/

package src.geographicarea;

import java.io.File;
import java.nio.file.FileSystems;

/**
 * Un oggetto della class <code>GeographicArea</code>
 * rappresenta un area geografica identificata con id,
 * nome, nome ASCII, stato e coordinate.
 * @author Lorenzo Radice
 * @version 0.0.0
 */
public class GeographicArea {
    // Areas File
    private final File file = FileSystems.getDefault().getPath("data", "geonames-and-coordinates.csv").toFile();
    // Geoname ID
    private int geoname_id = 0;
    // Name
    private String name = "";
    // ASCII name
    private String ascii_name = "";
    // Country Code
    private String country_code = "";
    // Coordinates
    private double [] coordinates = { 0.0, 0.0 };
    // Reasearch Areas for ID
    public int ricercaPerID() {
        int line = 0;
        return line;
    }
}
