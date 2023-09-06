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
}
