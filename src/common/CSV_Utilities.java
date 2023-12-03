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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

/**
 * Raccolta di metodi statici utili per la gestione dei file CSV.
 * @author Lorenzo Radice
 * @author Giacomo Paredi
 * @version 0.11.0
 */
public class CSV_Utilities {
    /**
     * Crea una riga che può essere aggiunta ad un file CSV.
     * Controlla la presenza di virgole e la gestisce.
     * @param linecells celle della riga
     * @return stringa per CSV
     */
    public static String toCSVLine(String [] linecells) {
        // Check if linecells exist
        if (linecells == null || linecells.length < 1) {
            // Exit
            return null;
        }
        // To be returned
        String str = "";
        // For each field
        for (String s : linecells) {
            // Add the formatted String
            str += CSVFormat(s);
            // Separator
            str += ",";
        }
        // Remove last comma
        str = str.substring(0, str.length() - 1 );
        // Return String
        return str;
    }
    /*
     * Ritorna la stringa con le virgolette se contiene un carattere separatore.
     * @param str stringa
     * @return stringa formattata
     */
    private static String CSVFormat( String str ) {
        // Separator
        final String separator = ",";
        // If cointains a separator put ""
        if (str.contains(separator))
            str = addQuotes(str);
        // Return formatted string
        return str;
    }
    /*
     * Aggiunge le virgolette all'inizio e alla fine della stringa
     * @param str stringa
     * @return stringa formattata
     */
    private static String addQuotes(String str) {
        return ("\"" + str + "\"");
    }
    /*
     * Scrive la linea passata come argomento alla fine del file CSV.
     * Si può scegliere se aggiungere una nuova linea alla fine della scrittura.
     * Ritorna true se la scrittura ha avuto esito positivo, false se non è avvenuta.
     * @param file file CSV
     * @param line linea
     * @param add_newline aggiunge una nuova linea se true
     * @return esito della scrittura
     */
    private static boolean WriteEOF_CSV( File file, String line, boolean add_newline) {
        // Check file existence
        if (! file.exists()) {
            // File name
            String f_str = file.getName();
            // FIle Path
            String f_path = file.getParent();
            // Error Output
            System.err.println("ERRORE: il file " + f_str + " non si trova nella cartella \'" + f_path + "\'.\n" );
            // Return false
            return false;
        }
        try{
            // Create a buffer writer at EOF
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            // Write the line
            bw.write( line );
            // Check add_newline
            if (add_newline) {
                // Add newline
                bw.write("\n");
            }
            // Close the Buffer Writer
            bw.close();
        } catch ( IOException e ) {
            // Return false
            return false;
        } catch (Exception e) { //to catch any exception inside try block
            // Not managed Error
            e.printStackTrace(); //used to print a throwable class along with other dataset class
            // Return false;
            return false;
        }
        return true;
    }
    /*
     * Aggiunge la prima linea ad un file.
     * @param file file
     * @param header prima linea
     * @return true se la scrittura ha avuto successo
     */
    private static boolean addHeader( File file, String header ) {
        try {
            // Buffer Reader
            BufferedWriter bf = new BufferedWriter(new FileWriter(file, true));
            // Add header
            bf.append(header + "\n");
            // Close file
            bf.close();
            // Return true
            return true;
        } catch (Exception e) {
            // In case an exception occurs
            return false;
        }
    }
    /*
     * Aggiunge una linea al file, se il file è vuoto o non ha linee aggiunge l'intestazione.
     * Ritorna true se l'esecuzione è avvenuta correttamente.
     * @param file file
     * @param line linea
     * @param header intestazione
     * @deprecated  Questo metodo non controlla la correttezza della stringa
     * <p> usa invece {@link CSV_Utilities#addArraytoCSV( File file, String[] linecells, String header )}.
     * @return true se l'esecuzione è avvenuta correttamente
     */
    private static boolean addLinewithCheck( File file, String line, String header ) {
        // Check if file has at least one line
        if ( ! fileHasLines(file) ) {
            // Check if method execution succeded
            if (! addHeader(file, header)) {
                // If error return false
                return false;
            }
        }
        // Write the line on file and return the result of method execution
        return WriteEOF_CSV(file, line, true);
    }
    /**
     * Aggiunge un array di stringhe ad un file CSV.
     * Se il file CSV è vuoto o non ha linee aggiunge l'intestazione.
     * Ritorna true se l'esecuzione è avvenuta correttamente.
     * @param file file CSV
     * @param linecells celle della linea da aggiungere
     * @param header intestazione
     * @return true se l'esecuzione è avvenuta correttamente
     */
    public static boolean addArraytoCSV( File file, String[] linecells, String header ) {
        // Line
        String line = toCSVLine(linecells);
        // Add line to File
        return addLinewithCheck(file, line, header);
    }
    /*
     * Controlla che il file esista e abbia almeno una riga, in caso contrario ritorna false
     * @param file file
     * @return true se ha almeno una linea
     */
    private static boolean fileHasLines( File file ) {
        // Error managing
        try {
            // If file has more than 0 line
            if ( file.exists() && (Files.lines(file.toPath()).count() > 0) )
                // Return true
                return true;
            // If file does not exist or has no line
            else
                // Retunr false
                return false;
        } catch (IOException e) {
            // In case of error return false
            return false;
        }
    }

    /**
     * Aggiorna una cella di un file CSV
     * @param file file CSV
     * @param update nuovo valore che la cella assumerà
     * @param line linea della cella escludendo l'intestazione del file
     * @param col colonna della cella
     * @return true se l'esecuzione è avvenuta correttamente
     */
    public static boolean updateCellInCSV(File file, String update, int line, int col){
        
        // Check file existence
        if (! file.exists()) {
            // File name
            String f_str = file.getName();
            // FIle Path
            String f_path = file.getParent();
            // Error Output
            System.err.println("ERRORE: il file " + f_str + " non si trova nella cartella \'" + f_path + "\'.\n" );
            // Return false
            return false;
        }

        try {
            //Read existing file 
            CSVReader reader=new CSVReader(new FileReader(file));
            List<String[]> csvBody=reader.readAll();
            //get CSV row column and replace with by using row and column
            csvBody.get(line)[col]=update;
            reader.close();

            // Write to CSV file which is open
            CSVWriter writer = new CSVWriter(new FileWriter(file));
            writer.writeAll(csvBody);
            writer.close();
            
        } catch ( IOException e ) {
            // Return false
            return false;
        } catch (Exception e) { //to catch any exception inside try block
            // Not managed Error
            e.printStackTrace(); //used to print a throwable class along with other dataset class
            // Return false;
            return false;
        }
        return true;
    }


    /**
     * Aggiorna una cella di un file CSV
     * @param file file CSV
     * @param update nuovo valore che la cella assumerà
     * @param line linea della cella escludendo l'intestazione del file
     * @return true se l'esecuzione è avvenuta correttamente
     */
    public static boolean updateCellInCSV2(File file, String update, int line){
        
        // Check file existence
        if (! file.exists()) {
            // File name
            String f_str = file.getName();
            // FIle Path
            String f_path = file.getParent();
            // Error Output
            System.err.println("ERRORE: il file " + f_str + " non si trova nella cartella \'" + f_path + "\'.\n" );
            // Return false
            return false;
        }

        final String temp_file_name = ".tempfile";
        String temp_file_path = FileSystems.getDefault().getPath(file.getParent(), temp_file_name).toString();
        File f = new File(temp_file_path);
        File temp_file = file;
        // Rename file
        if (! temp_file.renameTo(f)) {
            // Error Output
            System.err.println("ERRORE: Creazione file temporaneo fallita.");
            // Return Error
            return false;
        }
        temp_file = f;
        f = file;

        try {
            if ( ! f.createNewFile() ) {
                // Error Output
                System.err.println("ERRORE: Creazione file di scrittura fallita.");
                // Return Error
                return false;
            }
            BufferedReader read = new BufferedReader(new FileReader(temp_file));
            BufferedWriter write = new BufferedWriter(new FileWriter(f));
            String[] currentLine = new String[2];
            int i = 0;
            boolean stop = false;

            for( i = 0; i < line && ! stop; i++){
                //read records before the record that need to be updated
                currentLine[i%2] = read.readLine();
                // Error catcher
                if ( currentLine[i%2] == null ) {
                    // Exit the loop
                    stop = true;
                } else {
                    //write records before the record that need to be updated
                    write.write(currentLine[i%2] );
                }
            }
            if (!stop) {
                //read the record to update
                currentLine[i%2] = read.readLine();
                // Check
                if ( currentLine[i%2] != null ) {
                    // Update
                    currentLine[i%2] += update;
                    // Read next record
                    currentLine[( ++i % 2 )] = read.readLine();
                    // Copy
                    do {
                        // Write the previous line
                        write.write(currentLine[ (i - 1) % 2 ]);
                    // Read the next line
                    } while( ( currentLine[++i % 2]  = read.readLine()) != null );
                }
            }
            write.close();
            read.close();
            
            if ( ! temp_file.delete() ) {
                // Error Output
                System.err.println("ERRORE: Eliminazione file temporaneo fallita.");
                // Return Error
                return false;
            }
            
        } catch ( IOException e ) {
            // Return false
            return false;
        } catch (Exception e) { //to catch any exception inside try block
            // Not managed Error
            e.printStackTrace(); //used to print a throwable class along with other dataset class
            // Return false;
            return false;
        }
        return true;
    }
}
