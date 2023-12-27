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

package src.users;

/**
 * Classe che contiene il menù operatore.
 * @author Lorenzo Radice
 * @version 0.22.0
 */
class MenuOperator {
    /**
     * Indexes
     */
    protected static final record IndexOf() {
        protected static final short research = 1;
        protected static final short view_areas = 2;
        protected static final short make_area = 3;
        protected static final short add_parameters = 4;
        protected static final short set_centre = 5;
        protected static final short make_centre = 6;
        protected static final short exit = 7;
    }
    // Menu string
    private String menu = null;
    // Exit Option
    private final String exit = "Logout";
    /**
     * Costruisce un oggetto menù
     */
    protected MenuOperator(){
        // Separator string
        final String separator = " - ";
        // Header
        final String header = "\n\tMenù Operatore Autorizzato\n";
        // Options array
        final String[] options = {
            (IndexOf.research       + separator + "Ricerca aree"),
            (IndexOf.view_areas     + separator + "Visualizza informazioni aree"),
            (IndexOf.make_area      + separator + "Crea area"),
            (IndexOf.add_parameters + separator + "Aggiungi parametri"),
            (IndexOf.set_centre     + separator + "Seleziona centro"),
            (IndexOf.make_centre    + separator + "Crea centro di monitoraggio"),
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
    }
    /**
     * Restituisce la stringa che rappresenta il menù
     * @return menù
     */
    protected String getMenu() {
        return this.menu;
    }
}