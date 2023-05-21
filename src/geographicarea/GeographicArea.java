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
import java.io.FileReader;
import java.nio.file.FileSystems;
import com.opencsv.CSVReader;
import javax.annotation.processing.SupportedOptions;

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
    public int ricercaPerID( int id ) {
        int line = 0;
        try{
            // CSV Reader
            CSVReader creader = new CSVReader( new FileReader(file) );
            // Line read
            String [] nextRecord;
            // Found flag
            boolean found = false;
            // Read data line by line
            while( (nextRecord = creader.readNext()) != null && found ){
                // When the first cell equals the id exit the while
                if ( id == Integer.parseInt(nextRecord[0]) ) {
                    found = true;
                }
                // Line increment
                line++;
            }
            creader.close();
            // If the line hasn't been found return -1 as error
            if ( nextRecord == null && ! found )
                line = -1;
        }catch(Exception e){ //to catch any exception inside try block
            e.printStackTrace();//used to print a throwable class along with other dataset class
        }
        return line;
    }
}
