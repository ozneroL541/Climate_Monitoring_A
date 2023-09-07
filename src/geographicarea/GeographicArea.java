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
import java.util.ArrayList;

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
    // Country Name
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
        public final static int generic_name = 10;
        public final static int country_code = 3;
        public final static int country_name = 4;
        public final static int coordinates = 5;
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
            this.coordinates  = Research.parseCoordinates(nextRecord[IndexOf.coordinates]);
            // Close creader
            creader.close();
        }catch(Exception e){ //to catch any exception inside try block
            e.printStackTrace();//used to print a throwable class along with other dataset class
        }
    }
    /**
     * Ricerca un Geoname ID nelle aree di ricerca e ritorna la riga in cui è contenuto.
     * @param id Geoname ID
     * @return Numero della riga
    */
    public static int ricercaPerID( int id ) {
        String is_str = ((Integer) id).toString();
        return Research.OneStringInCol(file, IndexOf.geoname_id, is_str);
    }
    /**
     * Ricerca un Geoname ID nelle aree di ricerca e ritorna le righe in cui è contenuto
     * in un array di Integer di un elemento.
     * Se non viene trovato nulla ritorna null.
     * @param id Geoname ID
     * @return Numero della riga
     */
    public static Integer[] ricercaPerID( String id ) {
        // Output array
        Integer [] o = new Integer[1];
        // Research
        o[0] = ricercaPerID(Integer.parseInt(id));
        // If the output is valid
        if ( o[0] >= 0 )
            // Return the output
            return o;
        else
            // Return nothing
            return null;
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
     * @param ascii_n Nome in formato ASCII
     * @return Numero delle righe
     */
    private static Integer[] ricercaPerASCIINome(String ascii_n){
        return Research.AllStringInCol(file, IndexOf.ascii_name, ascii_n);
    }
    /**
     * Ricerca un nome in qualsiasi formato nelle aree di ricerca e ritorna le righe in cui è contenuto
     * @param n Nome
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
     * @param c_c Country Code
     * @return Numero delle righe
     */
    public static Integer[] ricercaPerCodiceNazione(String c_c){
        return Research.AllStringInCol(file, IndexOf.country_code, c_c);
    }
    /**
     * Ricerca un Country Name nelle aree di ricerca e ritorna le righe in cui è contenuto
     * @param c_n Country Name
     * @return Numero delle righe
     */
    public static Integer[] ricercaPerNazione(String c_n){
        return Research.AllStringInCol(file, IndexOf.country_name, c_n);
    }
    /**
     * Ricerca le coordinate di un'area di ricerca e ritorna le righe dove sono contenute.
     * Se le coordinate sono inesatte si restituiranno le righe delle coordinate contenute in un range vicino a quelle fornite.
     * @param c Coordinates
     * @return Numero delle righe
     * @see ricercaPerCoordinate(c)
     */
    public static Integer[] ricercaPerCoordinate( String c ){
        // Pass to double
        double [] coordinates = Research.parseCoordinates(c);
        // Use search with doubles
        return ricercaPerCoordinate(coordinates);
    }
    /**
     * Ricerca le coordinate di un'area di ricerca e ritorna le righe dove sono contenute.
     * Se le coordinate sono inesatte si restituiranno le righe delle coordinate contenute in un range vicino a quelle fornite.
     * @param coo Coordinates
     * @return Numero delle righe
     */
    public static Integer[] ricercaPerCoordinate( double [] coo ){
        // If the lenght is wrong
        if(coo.length != 2)
            // Exit
            return null;
        // String of coordinates
        String c = "";
        // Make the string of coordinates
        c += coo[0] + ", " + coo[1];
        // Create a possible output
        Integer[] out = new Integer[1];
        // Make a precise research
        out[0] = Research.OneStringInCol(file, IndexOf.coordinates, c);
        // If the research has result
        if ( out[0] > 0 )
            // Exit
            return out;
        else {
            // Set out to null
            out = new Integer[0];
            // Set range to 1km
            double err = 1;
            // Increaser multiplicator
            double inc = 1;
            // While there is no point of interest
            while ( out.length <= 0 ) {
                // Search the nearest point
                out = Research.CoordinatesAdvanced(file, IndexOf.coordinates, coo, err);
                // Increase the range
                err += inc;
                // Increase the increment: the increment is not linear
                inc += err;
            }
            // Return the output
            return out;
        }
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
    /**
     * Ritorna Coordinates come String.
     * Il formato è il seguente:
     * "latitudine, longitudine"
     * @return coordinate
     */
    public String getCoordinatestoString() {
        String s = String.format("%3.5f* %3.5f", this.coordinates[0], this.coordinates[1]);;
        s = s.replace(",", ".");
        s = s.replace("*", ",");
        return s;
    }
    @Override
    public String toString() {
        String str = "";
        str += "Geoname ID:\t"   + this.geoname_id + "\n";
        str += "Name:\t\t"       + this.name + "\n";
        str += "ASCII Name:\t"   + this.ascii_name + "\n";
        str += "Country Code:\t" + this.country_code + "\n";
        str += "Country Name:\t" + this.country_name + "\n";
        str += "Latitude:\t"     + this.coordinates[0] + "\n" ;
        str += "Longitude:\t"    + this.coordinates[1];
        return str;
    }
    public static String SearchList( int s_number, String arg ) {
        Integer [] lines = new Integer[1];
        switch (s_number) {
            case IndexOf.geoname_id:
                lines = ricercaPerID(arg);
                break;
            case IndexOf.name:
                lines = ricercaPerNome(arg);
            case IndexOf.ascii_name:
                lines = ricercaPerASCIINome(arg);
            case IndexOf.generic_name:
                lines = ricercaPerNomeGenerico(arg);
                break;
            case IndexOf.country_code:
                lines = ricercaPerCodiceNazione(arg);
                break;
            case IndexOf.country_name:
                lines = ricercaPerNazione(arg);
                break;
            case IndexOf.coordinates:
                lines = ricercaPerCoordinate(arg);
                break;
            default:
                System.out.println("Errore: codice lista inesistente");
                return null;
        }
        return toList(lines);
    }
    public static String toList( Integer[] lines ) {
        String out = "N\tGeoname ID\tName\t\tASCII Name\tCountry Code\tCountry Name\tCoordinates";
        // Geographic area object
        GeographicArea ga;
        // For every result
        for (int i = 0; i < lines.length; i++) {
            // Make a GeographicArea object
            ga = new GeographicArea(lines[i]);
            // Write the index
            out += "\n" + ( i + 1 );
            //Cut too long names
            String[] nam = new String[3];
            nam[0] = ga.getName();
            nam[1] = ga.getAscii_name();
            nam[2] = ga.getCountry_name();
            for (int j = 0; j < nam.length; j++) {
                if( nam[j].length() > 15 )
                nam[j] = nam[j].substring(0, 15);
            }
            // Formatted output list
            out += String.format("\t%-10s\t%-10s\t%-10s\t%-10s\t%-11s\t%s", ga.getGeoname_id(), nam[0], nam[1], ga.getCountry_code(), nam[2], ga.getCoordinatestoString());
        }
        return out;
    }
}
