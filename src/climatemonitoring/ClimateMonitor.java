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

package src.climatemonitoring;

import src.header.Header;
import src.menu.MainMenu;

/**
 * Classe che contiene il Main del programma
 * @author Lorenzo Radice
 * @version 0.23.0 Alpha
 */
public class ClimateMonitor {
    /**
     * Main
     * @param args Main arguments
     */
    public static void main(String[] args) {
        // Check if the user entered a valid command
        if ( ! Header.evalCommand(args) ) {
            // Print Header
            Header.print_header();
            // While not ENTER
            while ( Header.doWhat() ) {}
            // Go to Menu
            MainMenu.ChooseOption();
        }
    }
}
