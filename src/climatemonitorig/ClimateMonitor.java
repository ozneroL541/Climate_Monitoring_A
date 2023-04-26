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
    // MAIN
    public static void main(String[] args) {
        // Menu Object cration
        Menu menu = new Menu();
        // Short integer for the menu options
        short mainmenu_input = 0;
        // While exit is not selected
        while ( ! menu.isQuit(mainmenu_input) ) {
            // Output the menu
            System.out.println(menu.getMenu());
            // input
            // TODO
            // Select the method choosen by the user
            switch (mainmenu_input) {
                case 1:
                    // Ricerca aree
                    // TODO
                    break;
                case 2:
                    // Login
                    // TODO
                    break;
                case 3:
                    // Registrazione
                    // TODO
                    break;
                case 4:
                    // Esci
                    break;
                default:
                    // Exit
                    // Error Message
                    // TODO
                    break;
            }
        }
    }
}
