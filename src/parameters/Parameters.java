/**************************************
 * Matricola    Cognome     Nome
 * 754530       Galimberti  Riccardo
 * 755152       Paredi      Giacomo
 * 753252       Radice      Lorenzo
 * Sede: Como
***************************************/
/*
    This file is part of Climate Monitoring.

    Climate Monitoring is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Climate Monitoring is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Climate Monitoring.  If not, see <http://www.gnu.org/licenses/>.
 */

package src.parameters;

import java.io.File;
import java.nio.file.FileSystems;
import java.text.SimpleDateFormat;
import src.common.CSV_Utilities;
import src.common.CommonMethods;
import src.common.InputScanner;
import src.common.Research;
import src.geographicarea.GeographicArea;
import src.monitoringcentre.MonitoringCentre;

/**
 * Un oggetto della classe <code>Parameters</code> rappresenta i parametri
 * rilevati di un'area geografica in una determinata area per un determinato
 * centro di monitoraggio sotto forma di una tabella.
 * @author Lorenzo Radice
 * @author Giacomo Paredi
 * @version 0.2.1
 */
public class Parameters {
    // Parameters File
    private final static File file = FileSystems.getDefault().getPath("data", "ParametriClimatici.dati.csv").toFile();
    // Header
    private final static String header = "Geoname ID,Data,Centro,Vento,Umidità,Pressione,Temperatura,Precipitazioni,Altitudine dei ghiacciai,Massa dei ghiacciai,Note Vento,Note Umidità,Note Pressione,Note Temperatura,Note Precipitazioni,Note Altitudine dei ghiacciai,Note Massa dei ghiacciai";
    // Geoname ID
    private int geoname_id = 0;
    // Date
    private String date = null;
    // Monitoring Centre
    private String centre = "";
    // Table
    private Table table = null;
    // Indexes
    private static final class IndexOf {
        private static final short geoname_id = 0;
        private static final short date = 1;
        private static final short centre = 2;
        private static final short table = 3;
        private static final short table_length = Table.n_categories;
        private static final short max_cols = (table + (table_length*2));
    }
    /**
     * Costruttore dell'oggetto Parameters
     * @param id geoname_id
     * @param d date
     * @param c centre
     * @param t table
     */
    public Parameters( int id, String d, String c, Table t) {
        this.geoname_id = id;
        this.date = d;
        this.centre = c;
        this.table = t;
    }
    /**
     * Costruttore dell'oggetto Parameters.
     * Crea un oggetto partendo dalla riga in cui è contenuto nel file.
     * @param line riga
     */
    public Parameters( Integer line ) {
        // Copy the record in a auxiliary variable
        String[] record = Research.getRecord(file, line);
        // Error Checker
        boolean error = false;
        // Check validity
        if ( record != null && record.length == IndexOf.max_cols ) {
            // Parameters
            short [] s = new short[IndexOf.table_length];
            // Notes
            String[] n = new String[IndexOf.table_length];
            // Add Table    
            for (short i = 0; i < IndexOf.table_length && !error; i++) {
                try {
                    s[i] = Short.parseShort(record[IndexOf.table + i]);
                    n[i] = record[IndexOf.table + IndexOf.table_length + i];  
                } catch (Exception e) {
                    error = true;
                }
            }
            // Error Checker
            if (!error) {
                this.table = new Table(s, n);
                if (this.table != null) {
                    // Save the datas
                    this.date         = record[IndexOf.date];
                    this.centre       = record[IndexOf.centre];
                    this.geoname_id   = Integer.parseInt(record[IndexOf.geoname_id]);
                }
            }       
        }
    }
    /**
     * Controlla che l'oggetto esista.
     * @return true se l'oggetto esiste
     */
    public boolean Exist() {
        GeographicArea ga = new GeographicArea(this.geoname_id);
        return ga.Exist();
    }
    /**
     * Ritorna il Geoname ID
     * @return geoname_id
     */
    public int getGeoname_id() {
        return this.geoname_id;
    }
    /**
     * Ritorna la Data
     * @return date
     */
    public String getDate() {
        return this.date;
    }
    /**
     * Ritorna il nome del Centro
     * @return centre
     */
    public String getCentre() {
        return this.centre;
    }
    /**
     * Ritorna la tabella dei parametri
     * @return table
     */
    public Table getTable() {
        return this.table;
    }
    @Override
    public String toString() {
        String str = "";
        str += "Geoname ID:\t"  + this.geoname_id   + "\n";
        str += "Data:\t\t"      + this.date         + "\n";
        str += "Centro:\t\t"    + this.centre       + "\n";
        str += "\nParametri\n";
        str += this.table.toString();
        return str;
    }
    /**
     * Crea un oggetto Parameters e lo ritorna.
     * @return oggetto Parameters
     */
    public static Parameters MakeParameters(String centre) {
        // Output
        Parameters p = null;
        // Geoname ID
        int id = 0;
        // Date
        String d = "";
        // Monitoring Centre
        String c = "";
        // Table
        Table t = null;
        // Assign Geoname ID
        id = insertID(centre);
        // Check ID
        if ( ! GeographicArea.IndexExist(id) ) {
            // Error output
            System.err.println("Errore nell'assegnazione dell'Area Geografica.");
            // return Error
            return null;
        }
        // Assign Date
        d = insertDate();
        // Check Date
        if ( d == null ) {
            // Error output
            System.err.println("Errore nell'assegnazione della data.");
            // return Error
            return null;
        }
        // Assign Centre
        c = insertCentre(centre);
        // Check Centre
        if ( c == null || c.length() <= 0) {
            // Error output
            System.err.println("Errore nell'assegnazione del centro.");
            // return Error
            return null;
        }
        // Assign Table
        t = Table.MakeTable();
        // Check Table existance
        if ( t == null ) {
            // Error output
            System.err.println("Errore nella creazione della tabella.");
            // return Error
            return null;
        }
        // MakeParameters
        p = new Parameters(id, d, c, t);
        // Return Parameters
        return p;
    }
    /*
     * Richiede l'inserimento del Geoname ID
     * @return geoname_id
     */
    private static int insertID(String centre) {
        // Geoname ID
        int id = 0;
        String area = null;
        String[] aree=null;
        boolean exit=false;

        MonitoringCentre c=MonitoringCentre.getCentreByName(centre);
        if ( c == null ) {
            System.err.println("Centro inesistente");
            return -1;
        }
        aree=c.getAreeInteresse();

        //show areas to user
        //TODO modificare il messaggio di output?
        System.out.println("Aree associate al centro " + centre + ":");
        for(int i=0;i<aree.length;i++){
            System.out.println(aree[i]);
        }

        //user choose area
        System.out.print("\nScegliere l'area inserendone il Geoname ID: ");
        do{
            area=InputScanner.INPUT_SCANNER.nextLine();
            //check if user input is a valid id
            for(String value: aree){
                if(value.equals(area)){
                    exit=true;
                    try {
                        id = Integer.parseInt(value);
                    } catch (Exception e) {
                        id = -1;
                        System.err.println("Errore: area inestinte salvata nel file dei Centri di Monitoraggio.");
                        exit = false;
                    }
                }
            }
            if (!exit) {
                System.out.print("Area inserita inesistente\nInserire un'area valida: ");
            }
        }while(!exit);

        return id;
    }
    /*
     * Richiede o assegna la data.
     * @return date
     */
    private static String insertDate() {
        // Date
        String today = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());
        return today;
    }
    /*
     * Richiede l'inserimento del centro di monitoraggio
     * @return centre
     */
    private static String insertCentre(String centre) {
        // Centre
        String c = centre;
        return c;
    }
    /*
     * Trasforma i campi della classe in un array di stringhe.
     * @return array di Strings
     */
    private String[] toStrings() {
        String[] strings = new String[IndexOf.max_cols];
        String[] t = this.table.toStrings();
        // Check if t was successful
        if ( t != null && t.length == (IndexOf.table_length*2) ) {
            strings[IndexOf.geoname_id] = "" + this.geoname_id;
            strings[IndexOf.date] = "" + this.date;
            strings[IndexOf.centre] = this.centre;
            for (short i = 0; i < IndexOf.table_length; i++) {
                strings[IndexOf.table + i] = t[i];
            }
            return strings;   
        } else
            return null;
    }
    /**
     * Aggiunge i Parametri al file CSV.
     * @return true se l'esecuzione è avvenuta correttamente
     */
    public boolean addToCSV() {
        // Array of fields
        String [] fields_arr = toStrings();
        // If there are no fields
        if ( fields_arr == null || fields_arr.length < 1 ) {
            // Exit without write
            return false;
        }
        // Add to CSV File
        return CSV_Utilities.addArraytoCSV(file, fields_arr, header);
    }
    /*
     * Stampa una lista di parametri.
     * @param area righe dei parametri
     * @param runtime_print 
     */
    private static void getList( Integer[] lines ) {
        // Number of lines to print
        int runtime_print = 0;
        // Minimum run constant
        final int min_run = 10;
        // Huge number of lines
        final int huge = 20;
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
                        if ( CommonMethods.ExitLoop(ans) ) {
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
            System.out.println("Non sono presenti dati riguardanti l'area selezionata.");
        }
    }
    /*
     * Dato un Geoname ID stampa i parametri riguardanti quell'Area.
     * @param id geoname_id
     */
    public static void MostraParametri( String id ) {
        // Check file existence
        if ( file.exists() ) {
            // Get lines
            Integer[] lines =  ricercaPerArea(id);
            // Index
            int i = 0;
            // Print List
            if ( lines != null ) {
                // Print List
                getList(lines);
                // Ask for better view
                i = askView(lines.length);
                // Check index
                if ( i > 0 ) {
                    // Create Parameter
                    Parameters p = new Parameters(lines[i-1]);
                    // Check for existence
                    if ( p != null && p.Exist() ) {
                        // New Line
                        System.out.println();
                        // Print Parameter
                        System.out.println(p.toString());   
                    }
                }
            } else {
                // Message if there is no output
                System.out.println("Non sono presenti dati riguardanti l'area selezionata.");
            }
        } else {
            System.out.println("Attualmente non ci sono dati disponibili.");
        }
    }
    /**
     * Chiede all'utente di inserire l'indice del parametro che desidera visualizzare.
     * @param max massimo indice
     * @return indice del parametro che si desidera visualizzare
     */
    private static int askView( int max ) {
        // Min
        final short min = 1;
        // Result
        int r = -1;
        // Input
        String in = "";
        // Ask
        System.out.print("Vuoi visualizzare maggiori informazioni su un parametro(S/N)?\t");
        // Input
        in = InputScanner.INPUT_SCANNER.next();
        // Check for positive input
        if ( in != null && in.length() >= 1 && ( in.toUpperCase().charAt(0) == 'S' || in.toUpperCase().charAt(0) == 'Y' )) {
            // Collect garbage
            in = InputScanner.INPUT_SCANNER.nextLine();
            // Ask
            System.out.print("Inserire l'indice della ricerca (" + min + "-" + max + "):\t");
            // Input
            in = InputScanner.INPUT_SCANNER.nextLine();
            // Try to Parse in
            try {
                // Parse input
                r = Integer.parseInt(in);
                // Check if r is in range
                if ( r < min || r > max ) {
                    // Output
                    System.out.println("Il valore inserito non rientra tra quelli proposti.");
                    // Reset r
                    r = -1;
                }
            } catch (Exception e) {
                // Error return
                r = -1;
            }
        }
        // Return value
        return r;
    }
    /*
     * Ricerca tutti i Parameters inseriti di un'Area Geografica.
     * @param area Area Geografica
     * @return l'array delle righe dove sono presenti i parametri
     */
    private static Integer[] ricercaPerArea(String area) {
        // Search all Parameters for this area
        return Research.AllStringInCol(file, IndexOf.geoname_id, area);
    }
    /**
     * Rende un array di righe del file CSV una lista
     * @param lines righe
     * @return lista
     */
    private static String toList(Integer[] lines) {
        // To be returned
        String out = "";
        // For every result
        for (int i = 0; i < lines.length; i++) {
            out += RunTimeLine(lines[i], i+1) + "\n";
        }
        return out;
    }
    /**
     * Ritorna una linea per la lista.
     * @param line riga
     * @param index indice
     * @return linea per lista
     */
    private static String RunTimeLine(Integer line, int index) {
        Parameters ga = new Parameters(line);
        // Output string
        String out = "";
        // If is the first line
        if( index <= 1 )
        // Put a head
            out += "N\tGeoname ID\tData\tCentro\tVento\tUmidità\tPressione\tTemperatura\tPrecipitazioni\tAltitudine dei ghiacciai\tMassa dei ghiacciai\n";
        // Write the index
        out += String.format("%5d", index);
        // Formatted output list
        out += String.format("\t%-10s\t%-10s\t%-10s", ga.getGeoname_id(), ga.getDate(), ga.getCentre());
        // TODO: add Parameters
        // Return String
        return out;
    }
    //TODO rimuovere 
    public static void main (String [] args){
        // TODO Debugging

        System.out.println(insertID("Insubria"));

        //Parameters.MostraParametri("6534484");
        /*
        Parameters p = Parameters.MakeParameters("6534484");
        if (p.addToCSV()) {
            System.out.println("Success");
        } else {
            System.out.println("Fail");
        }
        */
    }
}
