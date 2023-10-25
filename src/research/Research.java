/**************************************
 * Matricola    Cognome     Nome
 * 754530       Galimberti  Riccardo
 * 755152       Paredi      Giacomo
 * 753252       Radice      Lorenzo
 * Sede: Como
***************************************/

package src.research;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import com.opencsv.CSVReader;
/**
 * Classe che contiene algoritmi statici di ricerca.
 * @author Lorenzo Radice
 * @version 0.10.0
 */
public class Research {
    /**
     * Questo metodo ricerca una stringa in un file CSV
     * in una determinata colonna e
     * restituisce la riga corrispondente alla sua prima occorrenza.
     * In caso non venga trovata un'occorrenza restituisce: -1
     * In caso la colonna in argomento sia maggiore delle colonne del file restituisce: -2
     * In caso il file non esista restituisce -3
     * Altri errori: -4
     * @param file file CSV
     * @param col colonna
     * @param str stringa
     * @return line
     */
    public static int OneStringInCol( File file, int col, String str ) {
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
            if ( nextRecord.length <= col ) {
                // Error Output
                System.err.println("ERRORE: Le colonne nel file sono meno di quelle passate in argomento.\n");
                // Error
                return -2;
            }
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
        }catch(FileNotFoundException e){ // If file not found
            // File name
            String f_str = file.getName();
            // FIle Path
            String f_path = file.getParent();
            // Error Output
            System.err.println("ERRORE: il file " + f_str + " non si trova nella cartella \'" + f_path + "\'.\n" );
            // Return null
            return -3;
        }catch(Exception e){
            // Print Error
            e.printStackTrace();
            System.err.println();
            // Return null
            return -4;
        }
        // Return the line
        return line;
    }
    /**
     * Questo metodo ricerca una stringa in un file CSV
     * in una determinata colonna e
     * restituisce ogni riga in cui occorre.
     * @param file file CSV
     * @param col colonna
     * @param str stringa
     * @return array di Integer contenente le righe
     */
    public static Integer[] AllStringInCol( File file, int col, String str ) {
        // Set the line to 0
        int line = 0;
        // Create a list of int
        ArrayList<Integer> list = new ArrayList<Integer>();
        // Try CSVReader
        try{
            // CSV Reader
            CSVReader creader = new CSVReader( new FileReader(file) );
            // Line read
            String [] nextRecord;
            // Read first line
            nextRecord = creader.readNext();
            // If columns are less than col exit code -2
            if ( nextRecord.length <= col )
                return null;
            // First line will not contain any researched element so, increment and go on
            // Line increment
            line++;
            // Read data line by line
            while( (nextRecord = creader.readNext()) != null){
                // When the first cell equals the id exit the while
                if ( nextRecord[col].equals(str) ) {
                    list.add(++line);
                } else
                    // Line increment
                    line++;
            }
            creader.close();
        }catch(FileNotFoundException e){ // If file not found
            // File name
            String f_str = file.getName();
            // FIle Path
            String f_path = file.getParent();
            // Error Output
            System.err.println("ERRORE: il file " + f_str + " non si trova nella cartella \'" + f_path + "\'.\n" );
            // Return null
            return null;
        }catch(Exception e){
            // Print Error
            e.printStackTrace();
            System.err.println();
            // Return null
            return null;
        }
        // Create an array where store the list
        Integer[] out = new Integer[list.size()];
        list.toArray(out);
        // Return the lines
        return out;
    }
    /**
     * Questo metodo ricerca una stringa in un file CSV
     * in una determinata colonna e
     * restituisce ogni riga in cui occorre.
     * Questo metodo non è case-sensitive.
     * @param file file CSV
     * @param col colonna
     * @param str stringa
     * @return array di Integer contenente le righe
     */
    public static Integer[] AllStringInCol_notCaseS( File file, int col, String str ) {
        // Upper String
        str = str.toUpperCase();
        // Set the line to 0
        int line = 0;
        // Create a list of int
        ArrayList<Integer> list = new ArrayList<Integer>();
        // Try CSVReader
        try{
            // CSV Reader
            CSVReader creader = new CSVReader( new FileReader(file) );
            // Line read
            String [] nextRecord;
            // Read first line
            nextRecord = creader.readNext();
            // If columns are less than col exit code -2
            if ( nextRecord.length <= col )
                return null;
            // First line will not contain any researched element so, increment and go on
            // Line increment
            line++;
            // Read data line by line
            while( (nextRecord = creader.readNext()) != null){
                // When the first cell equals the id exit the while
                if ( str.equals(nextRecord[col].toUpperCase()) ) {
                    list.add(++line);
                } else
                    // Line increment
                    line++;
            }
            creader.close();
        }catch(FileNotFoundException e){ // If file not found
            // File name
            String f_str = file.getName();
            // FIle Path
            String f_path = file.getParent();
            // Error Output
            System.err.println("ERRORE: il file " + f_str + " non si trova nella cartella \'" + f_path + "\'.\n" );
            // Return null
            return null;
        }catch(Exception e){
            // Print Error
            e.printStackTrace();
            System.err.println();
            // Return null
            return null;
        }
        // Create an array where store the list
        Integer[] out = new Integer[list.size()];
        list.toArray(out);
        // Return the lines
        return out;
    }
    /**
     * Questo metodo ricerca le coordinate dei punti più vicini alla coordinata fornita
     * in un file CSV e ne
     * restituisce la riga di appartenenza.
     * Il range &egrave costituito dall'errore.
     * L'errore &egrave considerato in km.
     * @param file file CSV
     * @param col colonna
     * @param c coordinata fornita
     * @param err errore/range
     * @return array di Integer contenente le righe
     */
    public static Integer[] CoordinatesAdvanced(File file, int col, double[] c, double err ) {
        // Set the line to 0
        int line = 0;
        // Create a list of int
        ArrayList<Integer> list = new ArrayList<Integer>();
        // Coordinates
        double[] c2 = new double[2];
        // Distance
        double dist = 0.0;
        // Copy of coordinates
        double[] c1 = new double[2];
        // Pre-compute coordinates
        c1[0] = Math.toRadians(c[0]);
        c1[1] = Math.toRadians(c[1]);
        try{
            // CSV Reader
            CSVReader creader = new CSVReader( new FileReader(file) );
            // Line read
            String [] nextRecord;
            // Read first line
            nextRecord = creader.readNext();
            // If columns are less than col exit code -2
            if ( nextRecord.length <= col )
                return null;
            // First line will not contain any researched element so, increment and go on
            // Line increment
            line++;
            // Read data line by line
            while( (nextRecord = creader.readNext()) != null){
                // Parse the coordinates just read
                c2 = parseCoordinates(nextRecord[col]);
                // Calculate distance betwee coordinates
                dist = calculateDistance(c1[0], c1[1], c2[0], c2[1]);
                // When the first cell equals the id exit the while
                if ( dist <= err) {
                    // Increment line and add the line to the list
                    list.add(++line);
                } else
                    // Line increment
                    line++;
            }
            creader.close();
        }catch(FileNotFoundException e){ // If file not found
            // Return null
            return null;
        }catch(Exception e){
            // Print Error
            e.printStackTrace();
            System.err.println();
            // Return null
            return null;
        }
        // Create an array where store the list
        Integer[] out = new Integer[list.size()];
        list.toArray(out);
        // Return the lines
        return out;
    }
    // Calculate distance between coordinates
    private static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371; // Earth's radius in kilometers
        // Convert latitude and longitude from degrees to radians
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);
        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        // Calculate the distance
        double distance = R * c;
        return distance;
    }
    /**
     * Divide la stringa in coordinate.
     * In caso di errore ritorna null.
     * @param coo string
     * @return un array con 2 coordinate
     */
    public static double[] parseCoordinates ( String coo ){
        // Output
        double [] c = new double[2];
        // Spitted string
        String [] splitted = coo.split(", ");
        // If the string is not splitted abort
        if ( splitted.length != 2 )
            return null;
        // If parsing don't work return null
        try {
            // First coordinate
            c[0] = Double.parseDouble(splitted[0]);
            // Second coordinate
            c[1] = Double.parseDouble(splitted[1]);
            // Return the coordinates
            return c;
        } catch (Exception e) {
            // In case of error return null
            return null;
        }
    }
    /**
     * Questo metodo ricerca una stringa in un file CSV
     * in una determinata colonna e
     * restituisce true se &egrave presente, false altrimenti.
     * @param file file CSV
     * @param col  colonna
     * @param str  stringa
     * @return true se la stringa &egrave presente
     */
    public static boolean isStringInCol(File file, int col, String str) {
        try{
            // CSV Reader
            CSVReader creader = new CSVReader( new FileReader(file) );
            // Line read
            String [] nextRecord;
            // Read first line
            nextRecord = creader.readNext();
            // Read data line by line
            while( (nextRecord = creader.readNext()) != null){
                // When the first cell equals the id return true
                if ( nextRecord[col].equals(str) )
                    return true;
            }
            creader.close();
        } catch (FileNotFoundException e ) {
            // File name
            String f_str = file.getName();
            // FIle Path
            String f_path = file.getParent();
            // Error Output
            System.err.println("ERRORE: il file " + f_str + " non si trova nella cartella \'" + f_path + "\'.\n" );
            // Return false
            return false;
        } catch (Exception e) { //to catch any exception inside try block
            // Not managed Error
            e.printStackTrace(); //used to print a throwable class along with other dataset class
            // Return false;
            return false;
        }
        return false;
    }
    /**
     * Cerca in un file CSV la riga in input.
     * Ritorna un array di stringhe contenente le celle della riga.
     * @param file file CSV
     * @param line riga
     * @return array delle celle della riga
     */
    public static String[] getRecord(File file, int line){
        try{
            // CSV Reader
            CSVReader creader = new CSVReader( new FileReader(file) );
            // Line read
            String [] nextRecord = null;
            // Read following lines
            for(int i = 0; i < line; i++){
                nextRecord = creader.readNext();
            }
            // Close the file reader
            creader.close();
            // Return the record
            return nextRecord;
        }catch(FileNotFoundException e){ // If file not found
            // File name
            String f_str = file.getName();
            // FIle Path
            String f_path = file.getParent();
            // Error Output
            System.err.println("ERRORE: il file " + f_str + " non si trova nella cartella \'" + f_path + "\'.\n" );
            // Return null
            return null;
        }catch(Exception e){
            // Print Error
            e.printStackTrace();
            System.err.println();
            // Return null
            return null;
        }
    }
    /**
     * Cerca in un file CSV la stringa in input.
     * Ritorna un array di stringhe delle celle adiacenti alla prima occorrenza.
     * @param file file CSV
     * @param col  colonna
     * @param str  stringa
     * @return line
     */
    public static String[] getRecordByData(File file, int col, String str){
        // Output String
        String[] out = null; 
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
                return null;
            // First line will not contain any researched element so, increment and go on
            // Read data line by line
            while( (nextRecord = creader.readNext()) != null && !found ){
                // When the first cell equals the id exit the while
                if ( nextRecord[col].equals(str) ) {
                    // String found -> exit
                    found = true;
                    // Return record whose string belongs
                    out = nextRecord;
                }
            }
            creader.close();
        }catch(FileNotFoundException e){ // If file not found
            // File name
            String f_str = file.getName();
            // FIle Path
            String f_path = file.getParent();
            // Error Output
            System.err.println("ERRORE: il file " + f_str + " non si trova nella cartella \'" + f_path + "\'.\n" );
            // Return null
            return null;
        }catch(Exception e){ //to catch any exception inside try block
            // Not managed exception error
            e.printStackTrace(); //used to print a throwable class along with other dataset class
            // Return null
            return null;
        }
        // Return the record
        return out;
    }
}
