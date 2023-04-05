/**************************************
 * Matricola    Cognome     Nome
 * 754530       Galimberti  Riccardo
 * 753747       Masolo      Carlos
 * 755152       Paredi      Giacomo
 * 753252       Radice      Lorenzo
 * Sede: Como
***************************************/

package src.climatemonitorig;
import src.menu.Menu;

public class ClimateMonitor {
    public static void main(String[] args) {
        // Menu Object cration
        Menu menu = new Menu();
        // Short integer for the menu options
        short mainmenu_input = 0;
        // While exit is not selected
        while ( ! menu.isQuit(mainmenu_input) ) {
            // Output the menu
            System.out.println(menu.getMenu());
            // Initialize the main menu input to zero
            mainmenu_input = 0;
            // While the input is not valid
            while ( mainmenu_input < 1 && mainmenu_input > menu.NumberOfOptions() ) {
                // input
                // TODO
                mainmenu_input = 0;
            }
            // Select the method choosen by the user
            switch (mainmenu_input) {
                case 1:
                    // Ricerca aree
                    // TODO
                    break;
                case 2:
                    // Visualizzazione parametri climatici associati
                    // TODO
                    break;
                case 3:
                    // Registrazione (Solo operatori autorizzati)
                    // TODO
                    break;
                case 4:
                    // Login (Solo operatori autorizzati)
                    // TODO
                    break;
                case 5:
                    // Creazione centri di monitoraggio (Solo operatori autorizzati)
                    // TODO
                    break;
                case 6:
                    // Inserirmento valori dei parametri climatici (Solo operatori autorizzati)
                    // TODO
                    break;
                case 7:
                    // Esci
                    break;
                default:
                    // Esci
                    mainmenu_input = menu.NumberOfOptions();
                    break;
            }
        }
    }
}
