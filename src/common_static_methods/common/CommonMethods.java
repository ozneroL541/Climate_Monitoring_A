/**************************************
 * Matricola    Cognome     Nome
 * 754530       Galimberti  Riccardo
 * 755152       Paredi      Giacomo
 * 753252       Radice      Lorenzo
 * Sede: Como
***************************************/
// TODO mettere nella cartella superiore
package src.common_static_methods.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
// TODO mettere i metodi relativi ai file CSV a parte
/**
 * Raccolta di metodi statici utilizzati da più classi.
 * @author Lorenzo Radice
 * @version 0.10.1
 */
public class CommonMethods {
    /**
     * Crea una riga che può essere aggiunta ad un file CSV.
     * @param record array di Strings
     * @return stringa per CSV
     */
    public static String toCSVLine(String [] record) {
        // To be returned
        String str = "";
        // For each field
        for (String s : record) {
            // If cointains , put ""
            if (s.contains(","))
                str += "\"" + s + "\"";
            else
                str += s;
            // Separator
            str += ", ";
        }
        // Remove last comma
        str = str.substring(0, str.length() - 2 );
        // Return String
        return str;
    }
    // TODO mettere in metodo separato
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
     * Scrive la linea passata come argomento alla fine del file CSV.
     * Si può scegliere se aggiungere una nuova linea alla fine della scrittura.
     * Ritorna true se la scrittura ha avuto esito positivo, false se non è avvenuta.
     * @param file file CSV
     * @param line linea
     * @param add_newline aggiunge una nuova linea se true
     * @return esito della scrittura
     */
    public static boolean WriteEOF_CSV( File file, String line, boolean add_newline) {
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
    // TODO aggiungere metodo che crea un file e aggiunge un header
    // TODO aggiungere metodo che controlla se il file ha un header e se non c'è lo aggiunge
    /**
     * Controlla che il nome inserito sia valido.
     * @param name nome
     * @return true se il nome è valido
     */
    public static boolean isValidName(String name){
        // TODO change with:
        /*
    public static boolean onlyLettersSpacesApostrophes(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!Character.isLetter(c) && c != ' ' && c != '\'') {
                return false;
            }
        }
        return true;
    }
        */
         // Return result
        return name.matches("^(?!(?:[^']*'){2})(?!(?:[^ ]* ){2})[a-zA-Z ']{2,}$");
    }
}
