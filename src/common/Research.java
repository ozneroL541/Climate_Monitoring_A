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

package src.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import com.opencsv.CSVReader;

import src.geographicarea.Coordinates;

/**
 * Classe che contiene algoritmi statici di ricerca.
 * @author Lorenzo Radice
 * @version 0.22.0
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
        }catch(NullPointerException e){
            // File not read
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
        }catch(NullPointerException e){
            // File not read
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
        }catch(NullPointerException e){
            // File not read
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
     * Il range è costituito dall'errore.
     * L'errore è considerato in km.
     * @deprecated  Questo metodo è stato sostituito
     * <p> usa invece {@link Research#CoordinatesAdvancedV2( File file, int col, double[] c )}.
     * @param file file CSV
     * @param col colonna
     * @param c coordinata fornita
     * @param err errore/range
     * @return array di Integer contenente le righe
     * @version 1
     */
    @Deprecated
    public static Integer[] CoordinatesAdvancedV1(File file, int col, double[] c, double err ) {
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
                c2 = Coordinates.parseCoordinates(nextRecord[col]);
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
        }catch(NullPointerException e){
            // File not read
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
     * Restituisce tutte le linee che contengono le coordinate più vicine a quella passata in argomento.
     * L'array è restituito con le celle in ordine di vicinanza.
     * @param file file CSV
     * @param col colonna
     * @param c coordinata fornita
     * @return array di Integer contenente le righe
     * @version 2
     */
    public static Integer[] CoordinatesAdvancedV2( File file, int col, double[] c ) {
        // Limit of acceptable distance
        final short limit = 3000;
        // Max number to return
        final short max =  100;
        // Coordinates
        double[] c2 = new double[2];
        // Distance
        double dist = 0.0;
        // Numero della linea
        int line = 1;
        // List of lines
        ArrayList<Integer> linesList = new ArrayList<Integer>();
        // List of distances
        ArrayList<Double> distList = new ArrayList<Double>();
        // Counter
        int i = 0;
        // Copy of coordinates
        double[] c1 = new double[2];
        // Pre-compute coordinates
        c1[0] = Math.toRadians(c[0]);
        c1[1] = Math.toRadians(c[1]);
        // Manage files exceptions
        try {
            // CSV Reader
            CSVReader creader = new CSVReader( new FileReader(file) );
            // Line read
            String [] nextRecord;
            // Read first line
            nextRecord = creader.readNext();
            // If columns are less than col exit
            if ( nextRecord.length <= col )
                // Exit
                return null;
            // First line will not contain any researched element so increment and go on
            // Line increment
            line++;
            // Read data line by line
            while( (nextRecord = creader.readNext()) != null){
                // Parse the coordinates just read
                c2 = Coordinates.parseCoordinates(nextRecord[col]);
                // Calculate distance between coordinates
                dist = calculateDistance(c1[0], c1[1], c2[0], c2[1]);
                // Accept only distances in the limit
                if ( dist < limit ) {
                    // Binsearch the index where insert
                    i = BinSearchArrList(distList, dist);
                    // Add distance to the list
                    distList.add( i, dist);
                    // Add line to the list
                    linesList.add( i, line );
                    // Remove all the exceeding coordinates
                    while ( distList.size() > max ) {
                        // Remove last distance
                        distList.remove( distList.size() - 1 );
                        // Remove last line
                        linesList.remove( linesList.size() - 1 );
                    }
                }
                // Line increment
                line++;
            }
            // Close the CSV Reader
            creader.close();
        } catch(FileNotFoundException e){ // If file not found
            // Return null
            return null;
        }catch(NullPointerException e){
            // File not read
            // Return null
            return null;
        }catch(Exception e){
            // Print Error
            e.printStackTrace();
            System.err.println();
            // Return null
            return null;
        }
        // While
        // Create an array where store the list
        Integer[] out = new Integer[linesList.size()];
        // Put list in the array
        linesList.toArray(out);
        // Return the lines
        return out;
    }
    /**
     * Questo metodo ricerca una stringa in un file CSV
     * in una determinata colonna e
     * restituisce true se è presente, false altrimenti.
     * @param file file CSV
     * @param col  colonna
     * @param str  stringa
     * @return true se la stringa è presente
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
                // When the cell equals the string return true
                if ( nextRecord[col].equals(str) )
                    // Return true
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
        } catch(NullPointerException e) {
            // File not read
            // Return null
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
        }catch(NullPointerException e){
            // File not read
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
     * @return array di stringhe
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
    /**
     * Cerca in un file CSV le due stringhe in input.
     * Ritorna un array di stringhe delle celle adiacenti alla prima occorrenza.
     * @param file file CSV
     * @param col1 colonna della prima stringa
     * @param str1 prima stringa
     * @param col2 colonna della seconda stringa
     * @param str2 seconda stringa
     * @return array di stringhe
     */
    public static String[] getRecordByTwoDatas(File file, int col1, String str1, int col2, String str2){
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
            if ( nextRecord.length <= col1 || nextRecord.length <= col2 )
                return null;
            // First line will not contain any researched element so, increment and go on
            // Read data line by line
            while( (nextRecord = creader.readNext()) != null && !found ){
                // When the first cell equals the id exit the while
                if ( nextRecord[col1].equals(str1) && nextRecord[col2].equals(str2) ) {
                    // String found -> exit
                    found = true;
                    // Return record whose string belongs
                    out = nextRecord;
                }
            }
            // Close
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
        }catch(NullPointerException e){
            // File not read
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
    /**
     * Ritorna tutte le celle appartenenti alla colonna selezionata nel file CSV passato come argomento.
     * @param file file CSV
     * @param col colonna
     * @return array di stringhe della colonna
     */
    public static String[] getColArray(File file, int col) {
        try{
            // CSV Reader
            CSVReader creader = new CSVReader( new FileReader(file) );
            // Line read
            String [] nextRecord = null;
            // List of cell
            ArrayList<String> cellList = new ArrayList<String>();
            // Array of datas
            String [] colArray = null;
            // Read first line
            nextRecord = creader.readNext();
            // If columns are less than col
            if ( nextRecord.length <= col ) {
                // Error Output
                System.err.println("ERRORE: Le colonne nel file sono meno di quelle passate in argomento.\n");
                // Error
                return null;
            }
            // While there is somthing to read
            while ((nextRecord = creader.readNext()) != null) {
                // Add cell to the list
                cellList.add(nextRecord[col]);
            }
            // Close the file reader
            creader.close();
            // Pass arraylist to array
            colArray = ((String[]) cellList.toArray(new String[0]));
            // Return the array
            return colArray;
        }catch(FileNotFoundException e){ // If file not found
            // File name
            String f_str = file.getName();
            // FIle Path
            String f_path = file.getParent();
            // Error Output
            System.err.println("ERRORE: il file " + f_str + " non si trova nella cartella \'" + f_path + "\'.\n" );
            // Return null
            return null;
        }catch(NullPointerException e){
            // File not read
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
     * Controlla se, in un file CSV, esiste una linea in cui c'è
     * sia la prima stringa che la seconda
     * nelle rispettive colonne.
     * @param file file CSv
     * @param col1 colonna della prima stringa
     * @param col2 colonna della seconda stringa
     * @param str1 prima stringa
     * @param str2 seconda stringa
     * @return true se c'e una linea con entrambe le stringhe
     */
    public static boolean areInSameLine( File file, int col1, int col2, String str1, String str2 ) {
        try{
            // CSV Reader
            CSVReader creader = new CSVReader( new FileReader(file) );
            // Line read
            String [] nextRecord;
            // Read first line
            nextRecord = creader.readNext();
            // Read data line by line
            while( (nextRecord = creader.readNext()) != null){
                // When the first cell equals the first string
                if ( nextRecord[col1].equals(str1) ) {
                    // Check if also the second cell equals the second string
                    if ( nextRecord[col2].equals(str2) ) {
                        // Return true
                        return true;
                    }
                }
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
        } catch(NullPointerException e) {
            // File not read
            // Return null
            return false;
        } catch (Exception e) { //to catch any exception inside try block
            // Not managed Error
            e.printStackTrace(); //used to print a throwable class along with other dataset class
            // Return false;
            return false;
        }
        // Return false
        return false;
    }
    /**
     * Ritorna tutte le celle appartenenti alla colonna selezionata nel file CSV passato come argomento.
     * @param file file CSV
     * @param col colonna
     * @return array di stringhe della colonna
     */
    public static String[] getColNoRepetition(File file, int col) {
        try{
            // CSV Reader
            CSVReader creader = new CSVReader( new FileReader(file) );
            // Line read
            String [] nextRecord = null;
            // List of cell
            ArrayList<String> cellList = new ArrayList<String>();
            // Array of datas
            String [] colArray = null;
            // Index 
            int i = 0;
            // Read first line
            nextRecord = creader.readNext();
            // If columns are less than col
            if ( nextRecord.length <= col ) {
                // Error Output
                System.err.println("ERRORE: Le colonne nel file sono meno di quelle passate in argomento.\n");
                // Error
                return null;
            }
            // While there is somthing to read
            while ((nextRecord = creader.readNext()) != null) {
                // Calc index
                i = BinSearchArrListStr(cellList, nextRecord[col]);
                if ( i >= 0 )
                    // Add cell to the list
                    cellList.add( i , nextRecord[col] );
            }
            // Close the file reader
            creader.close();
            // Pass arraylist to array
            colArray = ((String[]) cellList.toArray(new String[0]));
            // Return the array
            return colArray;
        }catch(FileNotFoundException e){ // If file not found
            // File name
            String f_str = file.getName();
            // FIle Path
            String f_path = file.getParent();
            // Error Output
            System.err.println("ERRORE: il file " + f_str + " non si trova nella cartella \'" + f_path + "\'.\n" );
            // Return null
            return null;
        }catch(NullPointerException e){
            // File not read
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
     * Ricerca binaria in lista di double
     * @param arr lista
     * @param target obiettivo di ricerca
     * @return l'indice dove inserire l'obiettivo
     */
    private static int BinSearchArrList(ArrayList<Double> arr, Double target) {
        // Left side
        int left = 0;
        // Right side
        int right = arr.size() - 1;
        // Middle
        int mid = 0;
        // While left is smaller than right
        while (left <= right) {
            // Set middle
            mid = left + (right - left) / 2;
            // Not necessary because the target is never equals to something in the list
            /*if (arr.get(mid) == target) {
                // If the target value is already in the list, return its index
                return mid;
            } else*/
            // If the target is bigger
            if (arr.get(mid) < target) {
                // If the middle element is less than the target, search the right half
                left = mid + 1;
            } else {
                // If the middle element is greater than the target, search the left half
                right = mid - 1;
            }
        }
        // Return the index
        return left;
    }
    // Calculate distance between coordinates using Haversine
    private static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Earth's radius in kilometers
        double R = 6371;
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
     * Ricerca binaria in lista di Strings.
     * In caso la lista esista ritorna il valore -1,
     * @param arr lista
     * @param target obiettivo di ricerca
     * @return l'indice dove inserire l'obiettivo
     */
    private static int BinSearchArrListStr(ArrayList<String> arr, String target) {
        // Left side
        int left = 0;
        // Right side
        int right = arr.size() - 1;
        // Middle
        int mid = 0;
        // While left is smaller than right
        while (left <= right) {
            // Set middle
            mid = left + (right - left) / 2;
            // Not necessary because the target is never equals to something in the list
            if (arr.get(mid).equals(target)) {
                // If the target value is already in the list, return its index
                return -1;
            } else
                // If the target is bigger
                if ( arr.get(mid).compareTo(target) < 0 ) {
                    // If the middle element is less than the target, search the right half
                    left = mid + 1;
                } else {
                    // If the middle element is greater than the target, search the left half
                    right = mid - 1;
                }
        }
        // Return the index
        return left;
    }
}
