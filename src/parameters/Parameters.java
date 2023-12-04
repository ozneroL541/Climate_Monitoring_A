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

/**
 * Un oggetto della classe <code>Parameters</code> rappresenta i parametri
 * rilevati di un'area geografica in una determinata area per un determinato
 * centro di monitoraggio sotto forma di una tabella.
 * @author Lorenzo Radice
 * @author Giacomo Paredi
 * @version 0.2.0
 */
public class Parameters {
    // Parameters File
    private final static File file = FileSystems.getDefault().getPath("data", "ParametriClimatici.dati.csv").toFile();
    // Header
    private final static String header = "Geoname ID,Data,Centro,Vento,Umidità,Pressione,Temperatura,Precipitazioni,Altitudine dei ghiacciai,Massa dei ghiacciai,Note Vento,Note Umidità,Note Pressione,Note Temperatura,Note Precipitazioni,Note Altitudine dei ghiacciai,Note Massa dei ghiacciai";
    // Geoname ID
    private short geoname_id = 0;
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
        private static final short max_cols = (table + table_length);
    }
    /**
     * Costruttore dell'oggetto Parameters
     * @param id geoname_id
     * @param d date
     * @param c centre
     * @param t table
     */
    public Parameters( short id, String d, String c, Table t){
        this.geoname_id = id;
        this.date = d;
        this.centre = c;
        this.table = t;
    }
    /**
     * Crea un oggetto Parameters e lo ritorna.
     * @return oggetto Parameters
     */
    public static Parameters MakeParameters(String centre) {
        // Output
        Parameters p = null;
        // Geoname ID
        short id = 0;
        // Date
        String d = "";
        // Monitoring Centre
        String c = "";
        // Table
        Table t = null;
        // Assign Geoname ID
        id = insertID();
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
    private static short insertID() {
        // Geoname ID
        short id = 0;

        //TODO
        //partendo dal nome del centro, leggere file dei centri, prendere ultima colonna(aree)
        //fare scegliere aree tra quelle valide
        //ritornare una area

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
        if ( t != null && t.length == IndexOf.table_length ) {
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
     * Aggiungi i parametri alla fine del file CSV
     * @param ga Area Geografica a cui si riferisce
     * @return true se l'esecuzione è avvenuta correttamente.
     */
    public boolean printToFile( GeographicArea ga ) {
        // Execution succeded
        return CSV_Utilities.addArraytoCSV(file, toStrings(), header);
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
    // TODO JD
    public static void SearchList( String area, int runtime_print ) {
        // TODO Check Method
        // Output Integer array
        Integer [] lines = new Integer[1];
        // Minimum run constant
        final int min_run = 10;
        // Huge number of lines
        final int huge = 20;
        // Search
        lines = ricercaPerArea(area);
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
            System.out.println("Non è stata trovata alcuna Area Geografica coi parametri di ricerca selezionati.");
        }
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
    private static char[] toList(Integer[] lines) {
        // TODO
        return null;
    }
    private static char[] RunTimeLine(Integer integer, int i) {
        // TODO
        return null;
    }
    //TODO rimuovere 
    public static void main (String [] args){
        System.out.println(insertDate());
    }
}
