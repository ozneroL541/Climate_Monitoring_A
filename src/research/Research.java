package src.research;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import com.opencsv.CSVReader;

public class Research {
    /**
     * Questo metodo ricerca una stringa in un file CSV
     * in una determinata colonna e
     * restituisce la riga corrispondente alla sua prima occorrenza.
     * @param file file CSV
     * @param col  colonna
     * @param str  stringa
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
        }catch(Exception e){ //to catch any exception inside try block
            e.printStackTrace();//used to print a throwable class along with other dataset class
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
     * Il range è costituito dall'errore.
     * L'errore è considerato in km.
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
                c2 = parseCoordinates(nextRecord[col]);
                dist = calculateDistance(c[0], c[1], c2[0], c2[1]);
                // When the first cell equals the id exit the while
                if ( dist <= err) {
                    list.add(++line);
                } else
                    // Line increment
                    line++;
            }
            creader.close();
        }catch(Exception e){ //to catch any exception inside try block
            e.printStackTrace();//used to print a throwable class along with other dataset class
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
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
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
     * Divide la stringa in coordinate
     * @param coo string
     * @return un array con 2 coordinate
     */
    public static double[] parseCoordinates ( String coo ){
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
