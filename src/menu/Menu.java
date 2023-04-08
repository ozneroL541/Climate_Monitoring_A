/**************************************
 * Matricola    Cognome     Nome
 * 754530       Galimberti  Riccardo
 * 753747       Masolo      Carlos
 * 755152       Paredi      Giacomo
 * 753252       Radice      Lorenzo
 * Sede: Como
***************************************/

package src.menu;

public class Menu {
    // Menu string
    private String menu = null;
    // Number of options
    private short op_number = 0;
    // Number of the exit option
    private short exit_number = 0;
    // Exit Option
    private final String exit = "Esci";
    // Object constructor
    public Menu(){
        // Options array
        String[] options = {
            "Ricerca aree",
            "Visualizzazione parametri climatici associati",
            "Registrazione (Solo operatori autorizzati)",
            "Login (Solo operatori autorizzati)",
            "Creazione centri di monitoraggio (Solo operatori autorizzati)",
            "Inserirmento valori dei parametri climatici (Solo operatori autorizzati)",
            exit
        };
        // Separator string
        String separator = " - ";
        // Initialize menu
        menu = "";
        // For every element in the options array
        for ( short i = 0; i < options.length; i++ ) {
            // Create the menu string
            this.menu += (i + 1) +  separator + options[i] + '\n';
            // If the current option string is equal to exit than 
            if ( exit.equals(options[i]) )
                // The exit option number is the current number
                exit_number = (short) (i + 1);
        }
        // Number of options is the number of elements of the array
        this.op_number = (short) options.length;
    }
    // This method returns the menu string and, if it doesn't exist it makes it before return
    public String getMenu() {
        return menu;
    }
    //Check if the integer number corrisponds to the Exit command
    public boolean isQuit( short n ) {
        return n == exit_number;
    }
    // Return the Number of the Exit Option
    public short getQuit() {
        return exit_number;
    }
    // Return the Number of Options of the Menu
    // It's the max (which is the last) number displayed by the Menu
    public short NumberOfOptions() {
        return op_number;
    }
}
