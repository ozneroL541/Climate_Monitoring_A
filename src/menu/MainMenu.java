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

package src.menu;

import src.common.InputScanner;
import src.header.Header;
import src.users.*;

/**
 * Classe che contiene il menù principale del programma.
 * @author Lorenzo Radice
 * @version 0.22.0
 */
public class MainMenu {
    /**
     * Indexes
     */
    private static final record IndexOf() {
        private static final short research = 1;
        private static final short view_areas = 2;
        private static final short login = 3;
        private static final short registration = 4;
        private static final short info = 5;
        private static final short exit = 6;
    }
    /**
     * Mostra il menù e permette di sceglierne le opzioni.
     */
    public static void ChooseOption() {
        // Menu Object creation
        MainMenu menu = new MainMenu();
        // Short integer for the menu options
        short mainmenu_input = 0;
        // Input
        String input = "";
        // While exit is not selected
        do {
            // Output the menu
            System.out.println(menu.getMenu());
            // Request
            System.out.print("Inserire codice:\t");
            // Input
            try {
                // Input
                input = InputScanner.INPUT_SCANNER.nextLine();
                // Parse input
                mainmenu_input = (short) Short.valueOf(input);
            } catch (NumberFormatException e) {
                // Set to 0
                mainmenu_input = 0;
            } catch (Exception e) {
                // Set to -1
                mainmenu_input = -1;
            }
            // New line
            System.out.println();
        // Check if exit
        } while ( menu.selectedAction(mainmenu_input) );
    }
    // Menu string
    private String menu = null;
    // Number of options
    private short op_number = 0;
    // Exit Option
    private final String exit = "Esci";
    /**
     * Costruisce un oggetto menù
     */
    public MainMenu(){
        // Separator string
        final String separator = " - ";
        // Header
        final String header = "\n\tMenù principale\n";
        // Options array
        final String[] options = {
            (IndexOf.research       + separator + "Ricerca aree"),
            (IndexOf.view_areas     + separator + "Visualizza informazioni aree"),
            (IndexOf.login          + separator + "Login"),
            (IndexOf.registration   + separator + "Registrazione"),
            (IndexOf.info           + separator + "Info"),
            (IndexOf.exit           + separator + exit)
        };
        // Initialize menu
        this.menu = header;
        // For every element in the options array
        for ( short i = 0; i < options.length; i++ ) {
            // Create the menu string
            this.menu += options[i] + '\n';
            // If the current option string is equal to exit than
        }
        // Number of options is the number of elements of the array
        this.op_number = (short) options.length;
    }
    // This method returns the menu string
    /**
     * Restituisce la stringa che rappresenta il menù
     * @return menù
     */
    public String getMenu() {
        return this.menu;
    }
    // Check if the integer number corrisponds to the Exit command
    /**
     * Controlla che l'intero inserito abbia lo stesso indice del comando di uscita
     * @param n input
     * @return true se l'input è uguale all'uscita
     */
    public boolean isQuit( short n ) {
        return (n == IndexOf.exit);
    }
    // Return the Number of the Exit Option
    /**
     * Restituisce l'indice del comando di uscita
     * @return l'intero dell'uscita
     */
    public short getQuit() {
        return IndexOf.exit;
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
    // Execute selected action
    private boolean selectedAction( short input ) {
        // User
        User user = new User();        
        // Select the method choosen by the user
        switch (input) {
            case IndexOf.research:
                // Ricerca aree
                user.cercaAreaGeografica();
                return true;
            case IndexOf.view_areas:
                // Visualizza info aree
                user.visualizzaAreaGeografica();
                return true;
            case IndexOf.login:
                // Login
                // Autenticate
                user = AutorizedOperator.autenticazione();
                // Check autentication result
                if (user != null && (user instanceof AutorizedOperator )) {
                    // Autorized Operator Menu
                    ((AutorizedOperator) user).menu();
                }
                // Logout
                user = null;
                // Reset user
                user = new User();
                return true;
            case IndexOf.registration:
                // Registrazione
                User.registrazione();
                return true;
            case IndexOf.info:
                // Info
                Header.ChooseOption();
                return true;
            case IndexOf.exit:
                // Esci
                return false;
            default:
                // Error Message
                System.out.println("Il valore inserito non è corretto.");
                System.out.println("Inserire un numero valido per continuare.\n");
                return true;
        }
    }
}
