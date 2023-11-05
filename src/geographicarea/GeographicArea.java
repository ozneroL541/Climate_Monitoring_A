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

import src.common.*;

/**
 * Un oggetto della classe <code>GeographicArea</code>
 * rappresenta un area geografica identificata con id,
 * nome, nome ASCII, stato e coordinate.
 * @author Lorenzo Radice
 * @version 0.13.0
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
    private double [] coordinates = null;
    // Areas File
    private final static File file = FileSystems.getDefault().getPath("data", "geonames-and-coordinates.csv").toFile();
    // Header
    private final static String header = "Geoname ID,Name,ASCII Name,Country Code,Country Name,Coordinates";
    // Indexes in CSV file
    private final static class IndexOf {
        private final static int geoname_id = 0;
        private final static int real_name = 1;
        private final static int ascii_name = 2;
        private final static int generic_name = 10;
        private final static int country_code = 3;
        private final static int country_name = 4;
        private final static int coordinates = 5;
    }
    /**
     * Costruttore di Area Geografica.
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
        this.coordinates  = Coordinates.parseCoordinates(record[IndexOf.coordinates]);
    }
    /**
     * Costruttore di Area Geografica.
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
        this.coordinates  = Coordinates.parseCoordinates(record[IndexOf.coordinates]);
    }
    /**
     * Cotruttore vuoto di Area Geografica.
     */
    public GeographicArea() {
        this.geoname_id   = 0;
        this.name         = "";
        this.ascii_name   = "";
        this.country_code = "";
        this.country_name = "";
        this.coordinates  = null;
    }
    /**
     * Costruttore di Area Geografica.
     * Assegna ogni elemento dell'array di stringhe passato come parametro ai campi di GeographicArea.
     * @param record array di Strings
     */
    public GeographicArea(String[] record) {
        this.geoname_id   = Integer.parseInt(record[IndexOf.geoname_id]);
        this.name         = record[IndexOf.real_name];
        this.ascii_name   = record[IndexOf.ascii_name];
        this.country_code = record[IndexOf.country_code];
        this.country_name = record[IndexOf.country_name];
        this.coordinates  = Coordinates.parseCoordinates(record[IndexOf.coordinates]);
    }
    /*
     * Ricerca un Geoname ID nelle aree di ricerca e ritorna la riga in cui &egrave contenuto.
     * @param id Geoname ID
     * @return Numero della riga
    */
    private static int ricercaPerID( int id ) {
        // Translate id into string
        String is_str = ((Integer) id).toString();
        return Research.OneStringInCol(file, IndexOf.geoname_id, is_str);
    }
    /*
     * Ricerca un Geoname ID nelle aree di ricerca e ritorna le righe in cui &egrave contenuto
     * in un array di Integer di un elemento.
     * Se non viene trovato nulla ritorna null.
     * @param id Geoname ID
     * @return Numero della riga
     */
    private static Integer[] ricercaPerID( String id ) {
        // Output array
        Integer [] o = new Integer[1];
        // Try parsing
        try {
            // Research
            o[0] = ricercaPerID(Integer.parseInt(id));
            // ID not found
            if ( o[0] == null || o [0] == -1 ) {
                // Return null
                return null;
            }
            // Integer is valid only if it is positive
            if ( o[0] < 0 ) {
                // Error output
                System.err.println("Il Geoname ID inserito non è valido.");
                // Return nothing
                return null;
            } else
                // Return the output
                return o;
        } catch (Exception e) {
            // Error Output
            System.err.println("Il Geoname ID deve essere formato solo da numeri.\nIl Geoname ID inserito è errato.");
            // Return nothing
            return null;
        }
    }
    /*
     * Ricerca un Nome nelle aree di ricerca e ritorna le righe in cui &egrave contenuto
     * @param nome Nome
     * @return Numeri elle righe
     */
    private static Integer[] ricercaPerRealeNome(String nome){
        return Research.AllStringInCol(file, IndexOf.real_name, nome);
    }
    /*
     * Ricerca un Nome in formato ASCII nelle aree di ricerca e ritorna le righe in cui &egrave contenuto
     * @param ascii_n Nome in formato ASCII
     * @return Numeri delle righe
     */
    private static Integer[] ricercaPerASCIINome(String ascii_n){
        return Research.AllStringInCol_notCaseS(file, IndexOf.ascii_name, ascii_n);
    }
    /*
     * Ricerca un nome in qualsiasi formato nelle aree di ricerca e ritorna le righe in cui &egrave contenuto
     * @param n Nome
     * @return Numeri delle righe
     */
    private static Integer[] ricercaPerNomeGenerico( String n ){
        // Index
        short index = 0;
        // If is ASCII
        if ( Charset.forName("US-ASCII").newEncoder().canEncode(n) ) {
            // Assign ASCII index
            index = IndexOf.ascii_name;
        // If is not ASCII
        } else {
            // Assign name index
            index = IndexOf.real_name;
        }
        return Research.AllStringInCol_notCaseS(file, index, n);
    }
    /*
     * Ricerca un Country Code nelle aree di ricerca e ritorna le righe in cui &egrave contenuto
     * @param c_c Country Code
     * @return Numeri delle righe
     */
    private static Integer[] ricercaPerCodiceNazione(String c_c){
        return Research.AllStringInCol(file, IndexOf.country_code, c_c.toUpperCase());
    }
    /*
     * Ricerca un Country Name nelle aree di ricerca e ritorna le righe in cui &egrave contenuto
     * @param c_n Country Name
     * @return Numeri delle righe
     */
    private static Integer[] ricercaPerNazione(String c_n){
        return Research.AllStringInCol_notCaseS(file, IndexOf.country_name, c_n);
    }
    /*
     * Ricerca le coordinate di un'area di ricerca e ritorna le righe dove sono contenute.
     * Se le coordinate sono inesatte si restituiranno le righe delle coordinate contenute in un range vicino a quelle fornite.
     * @param c Coordinates
     * @return Numeri delle righe
     * @see ricercaPerCoordinate
     */
    private static Integer[] ricercaPerCoordinate( String c ){
        try {
            // Pass to double
            double [] coordinates = Coordinates.parseCoordinates(c);
            // Use search with doubles
            return ricercaPerCoordinate(coordinates);   
        } catch (Exception e) {
            // Error message
            System.err.println("Formato coordinate incorretto.");
            //Exit
            return null;
        }
    }
    /*
     * Ricerca le coordinate di un'area di ricerca e ritorna le righe dove sono contenute.
     * Se le coordinate sono inesatte si restituiranno le x righe delle coordinate più vicine a quelle fornite.
     * @param coo Coordinates
     * @return Numeri delle righe
     */
    private static Integer[] ricercaPerCoordinate( double [] coo ){
        // If coordinates do not exist abort
        if (coo == null) {
            // Error message
            System.err.println("Formato coordinate incorretto.");
            //Exit
            return null;
        }
        // If coordinates are less than 2 abort
        if ( coo.length != 2 ) {
            // Error message
            System.err.println("Errore coordinate.");
            // Exit
            return null;
        }
        // If the coordinates are not in the range of the Earth
        if ( coo[0] > 90.0 || coo[0] < -90.0 || coo[1] > 180.0 || coo[1] < -180.0 ) {
            // Error message
            System.err.println("Valori coordinate errati.");
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
            // Return the first x nearest areas
            out = Research.CoordinatesAdvancedV2(file, IndexOf.coordinates, coo);
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
     * Ritorna il Name come String
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
     * Il primo elemento &egrave la latitudine e il secondo &egrave la longitudine.
     * @return coordinates
     */
    public double[] getCoordinates() {
        return this.coordinates;
    }
    /**
     * Ritorna Coordinates come String.
     * Il formato &egrave il seguente:
     * "<em>latitudine, longitudine</em>"
     * @return coordinate
     */
    public String getCoordinatestoString() {
        // Copy coordinates
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
     * Il secondo parametro &egrave l'argomento della ricerca.
     * Il terzo parametro &egrave il numero di aree da stampare in caso di lista troppo grande.
     * Se <code>runtime_print</code> è 0 o negativo il numero di aree stampate sarà di valore fissato.
     * Numero massimo di aree stampabili insieme:    20
     * Numero di aree stampate in caso di <code>runtime_print == 0</code>:   10
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
        final int huge = 20;
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
                System.err.println("Errore: codice lista inesistente");
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
                    // Print remaining resoults
                    System.out.println("Risultati rimanenti: " + ( lines.length - i ));
                    // If you can still pront something
                    if ( i < lines.length ) {
                        // Output for Scanner
                        System.out.print("Continuare l'elenco(S/N)? ");
                        // Input
                        ans = InputScanner.INPUT_SCANNER.next();
                        // Up all the letters
                        ans = ans.toUpperCase();
                        // If quit, exit
                        if ( ans.contains("N") || ans.contains("Q") || ans.contains("ESC") || ans.contains("EXIT")) {
                            // Exit
                            l = -1;
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
     * Metodo di test che non ammette input per calcolare la velocità di esecuzione pura.
     * Cerca delle area geografiche e ne stampa la lista.
     * Il primo parametro si riferisce al tipo di ricerca.
     * Il secondo parametro &egrave l'argomento della ricerca.
     * Il terzo parametro &egrave il numero di aree da stampare in caso di lista troppo grande.
     * Se <code>runtime_print</code> è 0 o negativo il numero di aree stampate sarà di valore fissato.
     * Numero massimo di aree stampabili insieme:    20
     * Numero di aree stampate in caso di <code>runtime_print == 0</code>:   10
     * @param col_index numero della ricerca
     * @param arg argomento da ricercare
     * @param runtime_print numero di item da stampare
     */
    public static void SearchListTEST( int col_index, String arg, int runtime_print ) {
        // Output Integer array
        Integer [] lines = new Integer[1];
        // Minimum run constant
        final int min_run = 10;
        // Huge number of lines
        final int huge = 20;
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
                System.err.println("Errore: codice lista inesistente");
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
                for ( l = 0; l < limit && i < lines.length; i++) {
                        // Print runtime the string
                        System.out.println(RunTimeLine(lines[i], i + 1 ));
                        // Increase limit counter
                        l++;
                    }
            } else
                System.out.println(toList(lines));
        } else {
            // Message if there is no output
            System.out.println("Non è stata trovata alcuna Area Geografica coi parametri di ricerca selezionati.");
        }
    }
    /*
     * Ritorna la lista di tutte le aree geografiche presenti nelle righe in argomento.
     * @param lines righe
     * @return list
     */
    private static String toList( Integer[] lines ) {
        String out = "";
        // For every result
        for (int i = 0; i < lines.length; i++) {
            out += RunTimeLine(lines[i], i+1) + "\n";
        }
        return out;
    }
    // Runtime List
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
    /**
     * Stampa il menu delle possibili opzioni di ricerca.
     * Vengono indicati gli indici a fianco delle opzioni
     */
    public static void printIndexesMenu() {
        // Name of the possible researches
        final String [] col_names = {
            "Geoname ID",
            "Name",
            "ASCII Name",
            "Country Code",
            "Country Name",
            "Coordinates",
            "Generic Name"
        };
        // Counter
        int i = 0;
        // Array of possible selections
        int[] ind = new int[7];
        // Possible options
        ind[i++] += IndexOf.geoname_id;
        ind[i++] += IndexOf.real_name;
        ind[i++] += IndexOf.ascii_name;
        ind[i++] += IndexOf.country_code;
        ind[i++] += IndexOf.country_name;
        ind[i++] += IndexOf.coordinates;
        ind[i++] += IndexOf.generic_name;
        // Error catcher
        if ( ind.length != col_names.length ) {
            System.err.println("Errore Opzioni Area Geografica.");
        } else {
            // Output String
            String s = "";
            // Create the Option menu
            for ( i = 0; i < ind.length; i++) {
                s += String.format("%2d - %s\n", ind[i], col_names[i]);
            }
            s = s.substring(0, s.length() -1 );
            // Print Menu
            System.out.println(s);
        }
    }
    /**
     * Controlla se nell'indice selezionato esiste.
     * @param in input
     * @return true se l'indice esiste
     */
    public static boolean IndexExist( int in ) {
        // If index exist return true
        if (in == IndexOf.geoname_id)
            return true;
        if (in == IndexOf.real_name)
            return true;
        if (in == IndexOf.ascii_name)
            return true;
        if (in == IndexOf.country_code)
            return true;
        if (in == IndexOf.country_name)
            return true;
        if (in == IndexOf.coordinates)
            return true;
        if (in == IndexOf.generic_name)
            return true;
        // The index does not exist: return false
        return false;
    }
    /**
     * Controlla la correttezza dell'argomento, campo dell'area geografica.
     * Se l'argomento &egrave valido restituisce true altrimenti false.
     * @param str argomento
     * @param col_index indice della colonna
     * @return true se l'argomento &egrave valido
     */
    public static boolean argumentCorrect( String str, int col_index ) {
        // If str in null exit
        if (str == null || str.length() < 1) {
            // Return false
            return false;
        }
        // Search
        switch (col_index) {
            // Check Geoname ID
            case IndexOf.geoname_id:
                // Try parsing
                try {
                    // Research
                    Integer id = Integer.parseInt(str);
                    // Integer is valid only if it is positive
                    if ( id < 0 ) {
                        // Error output
                        System.out.println("Il Geoname ID inserito non è valido.");
                        // Return false
                        return false;
                    }
                // Parsing Error
                } catch (Exception e) {
                    // Error output
                    System.out.println("Il Geoname ID deve essere formato solo da numeri.\nIl Geoname ID inserito è errato.");
                    // Return false
                    return false;
                }
                // Return true
                return true;
            // Check Real Name
            case IndexOf.real_name:
            // Check Generic name
            case IndexOf.generic_name:
                // Check if name is valid
                if (CommonMethods.isValidName(str)) {
                    // Return True
                    return true;
                } else {
                    // Error output
                    System.out.println("Il nome inserito contiene caratteri non validi.");
                    // Return False
                    return false;
                }
            // Check ASCII name
            case IndexOf.ascii_name:
            // Check Country Name
            case IndexOf.country_name:
                // If is ASCII return true
                if (Charset.forName("US-ASCII").newEncoder().canEncode(str)) {
                    if ( CommonMethods.isValidASCIIName(str) ) {
                        // Return True
                        return true;
                    } else {
                        // Error output
                        System.out.println("Il nome inserito contiene caratteri non validi.");
                        // Return False
                        return false;
                    }
                } else {
                    // Error output
                    System.out.println("Il nome inserito deve essere formato solo da caratteri ASCII.");
                    // Return False
                    return false;
                }
            // Check Country Code
            case IndexOf.country_code:
                // Check cc lenght
                if ( str.length() != 2 ) {
                    // Error Ouptut
                    System.out.println("Lunghezza Country Code errata.");
                    System.out.println("Il Country Code deve essere di 2 caratteri.");
                    // Return False
                    return false;
                } else if ( ! Charset.forName("US-ASCII").newEncoder().canEncode(str)) {
                    // Error output
                    System.out.println("Il codice inserito deve essere formato solo da caratteri ASCII.");
                    // Return False
                    return false;
                } else if (! (str.matches("[a-zA-Z]+"))) {
                    // Error output
                    System.out.println("Il codice inserito deve essere formato solo da lettere.");
                    // Return False
                    return false;
                } else {
                    // Return True
                    return true;
                }
            // Check Coordinates
            case IndexOf.coordinates:
                // Try Parsing
                try {
                    // Pass to double
                    double [] coo = Coordinates.parseCoordinates(str);
                    // If the coordinates are not in the range of the Earth
                    if ( coo[0] > 90.0 || coo[0] < -90.0 || coo[1] > 180.0 || coo[1] < -180.0 ) {
                        // Error message
                        System.out.println("Valori coordinate errati.");
                        System.out.println("La latitudine deve essere compresa tra -90.00 e 90.00.");
                        System.out.println("La longitudine deve essere compresa tra -180.00 e 180.00.");
                        // Return False
                        return false;
                    }
                } catch (NullPointerException e) {
                    // Error message
                    System.out.println("Formato Coordinate incorretto.");
                    System.out.println("Il formato delle coordinate deve essere il seguente: \"lat, lon\".");
                    // Return False
                    return false;
                } catch ( Exception e ) {
                    // Not managed exception
                    // Error
                    e.printStackTrace();
                    // Return false
                    return false;
                }
                // Return True
                return true;
            default:
                // Error
                System.err.println("ERRORE: codice lista inesistente");
                return false;
        }
    }
    /**
     * Permette di creare un area di interesse inserendone i dati e la ritorna.
     * Se la creazione fallisce ritorna null.
     * @return area di interesse creata
     */
    public static GeographicArea createArea() {
        // ISO-3166 fil
        final File f_iso = FileSystems.getDefault().getPath("data", "iso-3166-coutries.csv").toFile();
        // Error string
        final String error = "Creazione area geografica terminata: creazione fallita";
        // Check if file exist
        if ( ! f_iso.exists() ) {
            // Print Error
            System.err.println("ERRORE: il file " + f_iso.getName() + " non si trova nella cartella \'" + f_iso.getParent() + "\'.\n" );
            // Return null
            return null;
        }
        // Geographic Area to be returned
        GeographicArea ga = new GeographicArea();
        // Array of strings of fields
        String[] fieldStrings = new String[IndexOf.coordinates + 1];
        // Input String
        String in = "";
        // Exit condition
        boolean exit = false;
        // Try catch for Input Exception
        try {
            do {
                // Request
                System.out.print("Inserire Geoname ID:\t\t");
                // Input
                in = InputScanner.INPUT_SCANNER.nextLine();
                // Check if it is correct
                if ((exit = argumentCorrect(in, IndexOf.geoname_id))) {
                    // Check if there is another area wth the same id
                    if ( file.exists() && Research.OneStringInCol(file, IndexOf.geoname_id, in) >= 0 ) {
                        // Output
                        System.out.println("Esiste già un'area geografica con lo stesso ID.");
                        System.out.println(error);
                        // Exit
                        return null;
                    } else {
                        // Assign input to geoname_id
                        fieldStrings[IndexOf.geoname_id] = in;
                    }
                }
            } while (!exit);
            // Request
            System.out.print("Inserire nome area:\t\t");
            // Input
            in = InputScanner.INPUT_SCANNER.nextLine();
            // Assign input to real_name
            fieldStrings[IndexOf.real_name] = in;
            // If input is ASCII
            if (Charset.forName("US-ASCII").newEncoder().canEncode(in) && CommonMethods.isValidASCIIName(in)) {
                // Assign input to ascii_name
                fieldStrings[IndexOf.ascii_name] = in;
            // Else request for ascii_name
            } else {
                do {
                    // Request
                    System.out.print("Inserire nome in formato ASCII:\t");
                    // Input
                    in = InputScanner.INPUT_SCANNER.nextLine();
                    // Check if input is ASCII
                    if ( (exit = argumentCorrect(in, IndexOf.ascii_name))) {
                        // If input is ASCII assign it to real_name
                        fieldStrings[IndexOf.ascii_name] = in;
                        // Exit
                        exit = true;
                    } else{
                        // Do not exit
                        exit = false;
                    }
                } while (!exit);
            }
            do {
                // Request
                System.out.print("Inserire codice nazione:\t");
                // Input
                in = InputScanner.INPUT_SCANNER.nextLine();
                // Country Code must be made of 2 characters
                if ( ! argumentCorrect(in, IndexOf.country_code) ) {
                    // Stay in loop
                    exit = false;
                } else {
                    // To upper case
                    in = in.toUpperCase();
                    // Record array
                    String [] cc_array = Research.getRecordByData(f_iso, 1, in);
                    // If Country code does not exist
                    if (cc_array == null ) {
                        // Output
                        System.out.println("Non è stata trovata alcuna nazione col codice inserito.");
                        // Stay in loop
                        exit = false;
                    } else {
                        // Assign Country Code
                        fieldStrings[IndexOf.country_code] = in;
                        // Assign Country Name
                        fieldStrings[IndexOf.country_name] = cc_array[0];
                        // Output
                        System.out.println("Nazione selezionata:\t\t" + fieldStrings[IndexOf.country_name] );
                        // Exit
                        exit = true;
                    }
                }
            } while (!exit);
            do {
                // Request
                System.out.print("Inserire cordinate geografiche:\t");
                // Input
                in = InputScanner.INPUT_SCANNER.nextLine();
                // Check if coordinates are valid
                if ( exit = argumentCorrect(in, IndexOf.coordinates) ) {
                    if ( file.exists() && Research.OneStringInCol(file, IndexOf.coordinates, in) >= 0 ) {
                        // Output
                        System.out.println("Esiste già un'area geografica con le stesse coordinate.");
                        System.out.println(error);
                        // Exit
                        return null;
                    } else {
                        // Assign Coordinates
                        fieldStrings[IndexOf.coordinates] = in;
                    }
                }
            } while (!exit);
        } catch (Exception e) {
            // Output Exception
            e.printStackTrace();
        }
        // Build Geographic Area
        ga = new GeographicArea(fieldStrings);
        // Return Geographic Area
        return ga;        
    }
    /**
     * Crea una riga coi campi dell'Area Geografica che può essere aggiunta ad un file CSV.
     * La stringa creata include il newline successivo.
     * @return stringa per CSV
     */
    public String toCSVLine() {
        // Return CSV line
        return CSV_Utilities.toCSVLine(toStringRecord());
    }
    // Create a record of strings from the fields
    private String[] toStringRecord() {
        // To be returned
        String[] record = new String[IndexOf.coordinates + 1];
        record[IndexOf.geoname_id]      = "" + this.geoname_id;
        record[IndexOf.real_name]       = this.name;
        record[IndexOf.ascii_name]      = this.ascii_name;
        record[IndexOf.country_code]    = this.country_code;
        record[IndexOf.country_name]    = this.country_name;
        record[IndexOf.coordinates]     = "" + this.coordinates[0] + ", " + this.coordinates[1];
        return record;
    }
    /**
     * Aggiunge l'Area Geografica al file CSV.
     * @return true se l'esecuzione è avvenuta correttamente
     */
    public boolean addToCSV() {
        // Array of fields
        String [] fields_arr = toStringRecord();
        // If there are no fields
        if ( fields_arr == null || fields_arr.length < 1 ) {
            // Exit without write
            return false;
        }
        // Add to CSV File
        return CSV_Utilities.addArraytoCSV(file, fields_arr, header);
    }
    /**
     * Controlla l'esistenza del file CSV.
     * @return true se il file esiste
     */
    public static boolean doesCSVExist() {
        // Check file existence
        return file.exists();
    }
}
