/**************************************
 * Matricola    Cognome     Nome
 * 754530       Galimberti  Riccardo
 * 755152       Paredi      Giacomo
 * 753252       Radice      Lorenzo
 * Sede: Como
***************************************/

package src.common_static_methods;

/**
 * Raccolta di metodi statici utilizzati da più classi.
 * @author Lorenzo Radice
 * @version 0.10.1
 */
public class CommonMethods {
    // TODO aggiungere metodo che crea un file e aggiunge un header
    // TODO aggiungere metodo che controlla se il file ha un header e se non c'è lo aggiunge
    /**
     * Controlla che il nome inserito sia valido.
     * @param name nome
     * @return true se il nome è valido
     */
    // TODO change name: insert ASCII in name
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
