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
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import src.research.Research;

import com.opencsv.CSVReader;

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
            while( (nextRecord = creader.readNext()) != null && --l > 0 ){}
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
     * Ricerca un Geoname ID nelle aree di ricerca e ritorna le righe in cui è contenuto
     * @param id Geoname ID
     * @return Numero delle righe
    */
    public static int ricercaPerID( int id ) {
        String is_str = ((Integer) id).toString();
        return Research.OneStringInCol(file, IndexOf.geoname_id, is_str);
    }
    /**
     * Ricerca un Nome nelle aree di ricerca e ritorna le righe in cui è contenuto
     * @param nome Nome
     * @return Numero delle righe
     */
    private static Integer[] ricercaPerNome(String nome){
        return Research.AllStringInCol(file, IndexOf.name, nome);
    }
    /**
     * Ricerca un Nome in formato ASCII nelle aree di ricerca e ritorna le righe in cui è contenuto
     * @param nome Nome in formato ASCII
     * @return Numero delle righe
     */
    private static Integer[] ricercaPerASCIINome(String ascii_n){
        return Research.AllStringInCol(file, IndexOf.ascii_name, ascii_n);
    }
    /**
     * Ricerca un nome in qualsiasi formato nelle aree di ricerca e ritorna le righe in cui è contenuto
     * @param nome Nome
     * @return Numeri delle righe
     */
    public static Integer[] ricercaPerNomeGenerico( String n ){
        // If is ASCII
        if ( Charset.forName("US-ASCII").newEncoder().canEncode(n) ) {
            // Use only ASCII research
            return ricercaPerASCIINome(n);
        // If is not ASCII
        } else {
            // Use non ASCII research
            return ricercaPerNome(n);
        }
    }
    /**
     * Ricerca un Country Code nelle aree di ricerca e ritorna le righe in cui è contenuto
     * @param nome Country Code
     * @return Numero delle righe
     */
    public static Integer[] ricercaPerCodiceNazione(String c_c){
        return Research.AllStringInCol(file, IndexOf.country_code, c_c);
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
    /**
     * Ritorna il Geoname ID come int
     * @return geoname_id
     */
    public int getGeoname_id() {
        return this.geoname_id;
    }
    /**
     *  Ritorna il Name come String
     * @return name
     */
    public String getName() {
        return this.name;
    }
    /**
     * Ritorna ASCII Name come String
     * @return ascii_name
     */
    public String getAscii_name() {
        return this.ascii_name;
    }
    /**
     * Ritorna Country Code come String
     * @return country_code
     */
    public String getCountry_code() {
        return this.country_code;
    }
    /**
     * Ritorna Country Name come String
     * @return country_name
     */
    public String getCountry_name() {
        return this.country_name;
    }
    /**
     * Ritorna Coordinates come array di double.
     * L'array contiene 2 elementi.
     * Il primo elemento è la latitudine e il secondo è la longitudine.
     * @return coordinates
     */
    public double[] getCoordinates() {
        return this.coordinates;
    }
    @Override
    public String toString() {
        String str = "";
        str += "Geoname ID:\t"   + this.geoname_id + "\n";
        str += "Name:\t\t"       + this.name + "\n";
        str += "ASCII Name:\t"   + this.ascii_name + "\n";
        str += "Country Code:\t" + this.country_code + "\n";
        str += "Latitude:\t"     + this.coordinates[0] + "\n" ;
        str += "Longitude:\t"    + this.coordinates[1];
        return str;
    }
}
