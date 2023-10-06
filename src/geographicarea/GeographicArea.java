/**************************************
 * Matricola    Cognome     Nome
 * 754530       Galimberti  Riccardo
 * 755152       Paredi      Giacomo
 * 753252       Radice      Lorenzo
 * Sede: Como
***************************************/

package src.geographicarea;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.util.Scanner;

import src.research.Research;

/**
 * Un oggetto della class <code>GeographicArea</code>
 * rappresenta un area geografica identificata con id,
 * nome, nome ASCII, stato e coordinate.
 * @author Lorenzo Radice
 * @version 0.2.0
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
        public final static int real_name = 1;
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
    public GeographicArea ( Integer line ) {
        // Copy the record in a auxiliary variable
        String[] record = Research.getRecord(file, line);
        // Save the datas
        this.geoname_id   = Integer.parseInt(record[IndexOf.geoname_id]);
        this.name         = record[IndexOf.real_name];
        this.ascii_name   = record[IndexOf.ascii_name];
        this.country_code = record[IndexOf.country_code];
        this.country_name = record[IndexOf.country_name];
        this.coordinates  = Research.parseCoordinates(record[IndexOf.coordinates]);
    }
    /**
     * Costruttore di Area Geografica
     * Fornito un dato in input crea l'oggetto Area Geografica utilizzando i dati appartenenti al corrispondente.
     * Se viene fornito in input un ID e come secondo argomento 0 l'Area Geografica sarà univoca.
     * Se viene fornito un qualsiasi altro dato verrà creata un'Area Geografica corrispondenta alla sua prima occorrenza.
     * I dati che vengono salvati sono
     * Geoname ID, Name, ASCII Name, Country Code, Country Name, Coordinates
     * @param data dato
     * @param col colonna
     */
    public GeographicArea ( String data, int col ) {
        // Copy the record in a auxiliary variable
        String[] record = Research.getRecordByData(file, col, data);
        // Save the datas
        this.geoname_id   = Integer.parseInt(record[IndexOf.geoname_id]);
        this.name         = record[IndexOf.real_name];
        this.ascii_name   = record[IndexOf.ascii_name];
        this.country_code = record[IndexOf.country_code];
        this.country_name = record[IndexOf.country_name];
        this.coordinates  = Research.parseCoordinates(record[IndexOf.coordinates]);
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
        try {
            // Research
            o[0] = ricercaPerID(Integer.parseInt(id));
            // If the output is valid
            if ( o[0] >= 0 )
                // Return the output
                return o;
            else
                // Return nothing
                return null;
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * Ricerca un Nome nelle aree di ricerca e ritorna le righe in cui è contenuto
     * @param nome Nome
     * @return Numero delle righe
     */
    private static Integer[] ricercaPerRealeNome(String nome){
        return Research.AllStringInCol(file, IndexOf.real_name, nome);
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
            return ricercaPerRealeNome(n);
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
        // If coordinates are not two
        if (coo == null || coo.length != 2)
            // Exit
            return null;
        // If the coordinates are not in the range of the Earth
        if ( coo[0] > 90.0 || coo[0] < -90.0 || coo[1] > 180.0 || coo[1] < -180.0 ) {
            // Exit
            return null;
        }
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
            // Set error limit
            double limit = 10000;
            // While there is no point of interest
            while ( out.length <= 0 && err < limit ) {
                // Search the nearest point
                out = Research.CoordinatesAdvanced(file, IndexOf.coordinates, coo, err);
                // Increase the range
                err += inc;
                // Increase the increment: the increment is not linear
                inc += err;
            }
            // If the error is bigger than the security limit abort
            if ( err > limit ){
                return null;
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
    /**
     * Cerca delle area geografiche e ne stampa la lista.
     * Il primo parametro si riferisce al tipo di ricerca.
     * Il secondo parametro è l'argomento della ricerca.
     * Il terzo parametro è il numero di aree da stampare in caso di lista troppo grande.
     * @param col_index numero della ricerca
     * @param arg argomento da ricercare
     * @param runtime_print numero di item da stampare
     */
    public static void SearchList( int col_index, String arg, int runtime_print ) {
        // Output Integer array
        Integer [] lines = new Integer[1];
        // Minimum run constant
        final int min_run = 10;
        // Huge number of lines
        final int huge = 250;
        // Search
        switch (col_index) {
            // Univocal item
            case IndexOf.geoname_id:
                lines = ricercaPerID(arg);
                // Impossible to have more than one case
                runtime_print = -1;
                break;
            // Multiple, but few, items
            case IndexOf.real_name:
                lines = ricercaPerRealeNome(arg);
                break;
            // Multiple, but few, items
            case IndexOf.ascii_name:
                lines = ricercaPerASCIINome(arg);
                break;
            // Multiple, but few, items
            case IndexOf.generic_name:
                lines = ricercaPerNomeGenerico(arg);
                break;
            // Huge list
            case IndexOf.country_code:
                lines = ricercaPerCodiceNazione(arg);
                break;
            // Huge list
            case IndexOf.country_name:
                lines = ricercaPerNazione(arg);
                break;
            // Multiple, but few, items
            case IndexOf.coordinates:
                lines = ricercaPerCoordinate(arg);
                break;
            default:
                // Error
                System.out.println("Errore: codice lista inesistente");
                return;
        }
        // Print if there is something
        if (lines != null && lines.length > 0) {
            // If the number of lines is huge force runtime_print
            if ( lines.length > huge && runtime_print <= 0) {
                runtime_print = min_run;
            }
            // If runtime print is enable print in runtime mode
            if ( runtime_print > 0 ) {
                // Limit of item to print
                int limit = runtime_print;
                // limit counter
                int l = 0;
                // Lines counter
                int i = 0;
                // String which
                String ans = "N";
                do {
                    for ( l = 0; l < limit && i < lines.length; i++) {
                        // Print runtime the string
                        System.out.println(RunTimeLine(lines[i], i + 1 ));
                        // Increase limit counter
                        l++;
                    }
                    // If you can still pront something
                    if ( i < lines.length ) {
                        // Input Scanner
                        Scanner sc = new Scanner(System.in);
                        // Output for Scanner
                        System.out.print("\nContinuare l'elenco(S/N)? ");
                        // Input
                        ans = sc.next();
                        // Up all the letters
                        ans = ans.toUpperCase();
                        // If quit, exit
                        if ( ans.contains("N") || ans.contains("Q") || ans.contains("ESC") || ans.contains("EXIT")) {
                            // Exit
                            l = -1;
                            // Close input scanner
                            sc.close();
                        }
                        // Add a line
                        System.out.println();
                    } else
                        // Exit the loop
                        l = -1;
                } while ( l >= 0);
            } else
                System.out.println(toList(lines));
        } else {
            // Message if there is no output
            System.out.println("Non è stata trovata alcuna Area Geografica coi parametri di ricerca selezionati.");
        }
    }
    /**
     * Ritorna la lista di tutte le aree geografiche presenti nelle righe in argomento.
     * @param lines righe
     * @return list
     */
    public static String toList( Integer[] lines ) {
        String out = "";
        // For every result
        for (int i = 0; i < lines.length; i++) {
            out += RunTimeLine(lines[i], i+1) + "\n";
        }
        return out;
    }
    // Testing Runtime List
    private static String RunTimeLine( Integer line, int index ){
        GeographicArea ga = new GeographicArea(line);
        // Output string
        String out = "";
        // If is the first line
        if( index <= 1 )
        // Put a head
            out += "N\tGeoname ID\tName\t\tASCII Name\tCountry Code\tCountry Name\tCoordinates\n";
        // Write the index
        out += String.format("%5d", index);
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
        return out;
    }
}
