/**************************************
 * Matricola    Cognome     Nome
 * 754530       Galimberti  Riccardo
 * 755152       Paredi      Giacomo
 * 753252       Radice      Lorenzo
 * Sede: Como
***************************************/

package src.common;

/**
 * Raccolta di metodi statici utilizzati da più classi.
 * @author Lorenzo Radice
 * @version 0.10.2
 */
public class CommonMethods {
    /**
     * Controlla che il nome inserito sia formato solo da lettere ASCII, apostrofi e spazi.
     * @param name nome
     * @return true se il nome è valido
     */
    public static boolean isValidASCIIName(String name){
        // For every character in string
        for ( Character c : name.toCharArray() ) {
            // Check the validity of the character
            if (!Character.isLetter(c) && c != ' ' && c != '\'') {
                // Character not valid: Return false
                return false;
            }
        }
        // Character valid: return true
        return true;
    }
}
