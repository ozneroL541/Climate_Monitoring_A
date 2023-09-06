/**************************************
 * Matricola    Cognome     Nome
 * 754530       Galimberti  Riccardo
 * 753747       Masolo      Carlos
 * 755152       Paredi      Giacomo
 * 753252       Radice      Lorenzo
 * Sede: Como
***************************************/

package src.geographicarea;

import java.beans.IndexedPropertyChangeEvent;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
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
    // Geoname ID
    private int geoname_id = 0;
    // Name
    private String name = "";
    // ASCII name
    private String ascii_name = "";
    // Country Code
    private String country_code = "";
    // Countri Name
    private String country_name = "";
    // Coordinates
    private double [] coordinates = { 0.0, 0.0 };
    // Areas File
    private final static File file = FileSystems.getDefault().getPath("data", "geonames-and-coordinates.csv").toFile();
    // Indexes in CSV file
    private final static class IndexOf {
        public final static int geoname_id = 0;
        public final static int name = 1;
        public final static int ascii_name = 2;
        public final static int country_code = 3;
        public final static int country_name = 4;
        public final static int coordinate = 5;
    }
    /**
     * Costruttore di Area Geografica
     * Data una riga in input crea l'oggetto Area Geografica utilizzando i dati appartenenti a tale riga.
     * I dati che vengono salvati sono
     * Geoname ID, Name, ASCII Name, Country Code, Country Name, Coordinates
     * @param line riga
     */
    //TODO Testing, not tasted
    public GeographicArea ( int line ) {
        // Copy line
        int l = line;
        // If columns are line is less than 0 exit
        if ( l <= 0 )
            return;
        try{
            // CSV Reader
            CSVReader creader = new CSVReader( new FileReader(file) );
            // Line read
            String [] nextRecord;
            // Read data line by line until you reach the correct one
            while( (nextRecord = creader.readNext()) != null && --l <= 0 ){}
            // Seve the data
            this.geoname_id   = Integer.parseInt(nextRecord[IndexOf.geoname_id]);
            this.name         = nextRecord[IndexOf.name];
            this.ascii_name   = nextRecord[IndexOf.ascii_name];
            this.country_code = nextRecord[IndexOf.country_code];
            this.country_name = nextRecord[IndexOf.country_name];
            this.coordinates  = parseCoordinates(nextRecord[IndexOf.coordinate]);
            // Close creader
            creader.close();
        }catch(Exception e){ //to catch any exception inside try block
            e.printStackTrace();//used to print a throwable class along with other dataset class
        }
    }
    /**
     * Ricerca un Geoname ID nelle aree di ricerca e ritorna la riga in cui è contenuto
     * @param id Geoname ID
     * @return Numero della riga
    */
    public static int ricercaPerID( int id ) {
        String is_str = ((Integer) id).toString();
        return researchStringInCol(IndexOf.geoname_id, is_str);
    }
    /**
     * Ricerca un Nome nella aree di ricerca e ritorna la riga in cui è contenuto
     * @param nome Nome
     * @return Numero della riga
     */
    public static int ricercaPerNome(String nome){
        return researchStringInCol(IndexOf.name, nome);
    }
    /**
     * Ricerca un Nome in formato ASCII nella aree di ricerca e ritorna la riga in cui è contenuto
     * @param nome Nome in formato ASCII
     * @return Numero della riga
     */
    public static int ricercaPerASCIINome(String ascii_n){
        return researchStringInCol(IndexOf.ascii_name, ascii_n);
    }
    /**
     * Ricerca un Country Code nella aree di ricerca e ritorna la riga in cui è contenuto
     * @param nome Country Code
     * @return Numero della riga
     */
    public static int ricercaPerCodice(String c_c){
        return researchStringInCol(IndexOf.country_code, c_c);
    }
    // Research a String in a Column
    private static int researchStringInCol( int col, String str ) {
        // Set the line to 0
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
        // Return the line
        return line;
    }
    // Parse Coordinates
    private static double[] parseCoordinates ( String coo ){
        // Output
        double [] c = new double[2];
        // Spitted string
        String [] splitted = coo.split(", ");
        // First coordinate
        c[0] = Double.parseDouble(splitted[0]);
        // Second coordinate
        c[1] = Double.parseDouble(splitted[1]);
        // Return the coordinates
        return c;
    }
}
