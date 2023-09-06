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
    private final static File file = FileSystems.getDefault().getPath("data", "geonames-and-coordinates.csv").toFile();
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
    /**
     * Ricerca un Geoname ID nelle aree di ricerca e ritorna la riga in cui è contenuto
     * @param id Geoname ID
     * @return Numero della riga
    */
    public static int ricercaPerID( int id ) {
        String is_str = ((Integer) id).toString();
        return researchStringInCol(0, is_str);
    }
    /**
     * Ricerca un Nome nella aree di ricerca e ritorna la riga in cui è contenuto
     * @param nome Nome
     * @return Numero della riga
     */
    public static int ricercaPerNome(String nome){
        return researchStringInCol(1, nome);
    }
    /**
     * Ricerca un Nome in formato ASCII nella aree di ricerca e ritorna la riga in cui è contenuto
     * @param nome Nome in formato ASCII
     * @return Numero della riga
     */
    public static int ricercaPerASCIINome(String ascii_n){
        return researchStringInCol(2, ascii_n);
    }
    /**
     * Ricerca un Country Code nella aree di ricerca e ritorna la riga in cui è contenuto
     * @param nome Country Code
     * @return Numero della riga
     */
    public static int ricercaPerCodice(String c_c){
        return researchStringInCol(3, c_c);
    }
    // Research a String in a Column
    private static int researchStringInCol( int col, String str ) {
        int line = 0;
        try{
            // CSV Reader
            CSVReader creader = new CSVReader( new FileReader(file) );
            // Line read
            String [] nextRecord;
            // Found flag
            boolean found = false;
            // Read first line
            nextRecord = creader.readNext();
            // If columns are less than col exit code -2
            if ( nextRecord.length <= col )
                return -2;
            // First line will not contain any researched element so, increment and go on
            // Line increment
            line++;
            // Read data line by line
            while( (nextRecord = creader.readNext()) != null && !found ){
                // When the first cell equals the id exit the while
                if ( nextRecord[col].equals(str) ) {
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
