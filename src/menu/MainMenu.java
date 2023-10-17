/**************************************
 * Matricola    Cognome     Nome
 * 754530       Galimberti  Riccardo
 * 753747       Masolo      Carlos
 * 755152       Paredi      Giacomo
 * 753252       Radice      Lorenzo
 * Sede: Como
***************************************/

package src.menu;
/**
 * Classe che contiene il menù principale del programma.
 * @author Lorenzo Radice
 * @version 0.0.0
 */
public class MainMenu {
    // Menu string
    private String menu = null;
    // Number of options
    private short op_number = 0;
    // Number of the exit option
    private short exit_number = 0;
    // Exit Option
    private final String exit = "Esci";
    /**
     * Costruisce un oggetto menù
     */
    public MainMenu(){
        // Options array
        final String[] options = {
            "Ricerca aree",
            "Login",
            "Registrazione",
            exit
        };
        // Separator string
        final String separator = " - ";
        // Initialize menu
        this.menu = "";
        // For every element in the options array
        for ( short i = 0; i < options.length; i++ ) {
            // Create the menu string
            this.menu += (i + 1) +  separator + options[i] + '\n';
            // If the current option string is equal to exit than 
            if ( exit.equals(options[i]) )
                // The exit option number is the current number
                this.exit_number = (short) (i + 1);
        }
        // Number of options is the number of elements of the array
        this.op_number = (short) options.length;
    }
    // This method returns the menu string
    /**
     * Restituisce la stringa che rapresenta il menù
     * @return menù
     */
    public String getMenu() {
        return this.menu;
    }
    // Check if the integer number corrisponds to the Exit command
    /**
     * Controlla che l'intero inserito abbia lo stesso indice del comando di uscita
     * @param n input
     * @return true se l'input &egrave uguale all'uscita
     * @return false se l'input &egrave diverso dall'uscita
     */
    public boolean isQuit( short n ) {
        return n == exit_number;
    }
    // Return the Number of the Exit Option
    /**
     * Restituisce l'indice del comando di uscita
     * @return l'intero dell'uscita
     */
    public short getQuit() {
        return exit_number;
    }
    // Return the Number of Options of the Menu
    // It's the max (which is the last) number displayed by the Menu
    /**
     * Restituisce il numero di opzioni del menù
     * @return il numero delle opzioni
     */
    public short NumberOfOptions() {
        return op_number;
    }
}
