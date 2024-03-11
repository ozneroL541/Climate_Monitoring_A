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
 * Classe che contiene il menu Operatore Autorizzato.
 * @author Lorenzo Radice
 * @version 1.1.0
 */
class MenuOperator {
    /**
     * Opzioni menu
     */
    protected static final record IndexOf() {
        /**
         * Ricerca aree
         */
        protected static final short research = 1;
        /**
         * Visualizza informazioni aree
         */
        protected static final short view_areas = 2;
        /**
         * Crea area
         */
        protected static final short make_area = 3;
        /**
         * Aggiungi parametri
         */
        protected static final short add_parameters = 4;
        /**
         * Seleziona centro
         */
        protected static final short set_centre = 5;
        /**
         * Crea centro di monitoraggio
         */
        protected static final short make_centre = 6;
        /**
         * Esci
         */
        protected static final short exit = 7;
    }
    /** Stringa del menu */
    private String menu = null;
    /** Uscita */
    private final String exit = "Logout";
    /**
     * Costruisce un oggetto menu
     */
    protected MenuOperator(){
        // Separator string
        final String separator = " - ";
        // Header
        final String header = "\n\tMenu Operatore Autorizzato\n";
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
     * Restituisce la stringa che rappresenta il menu
     * @return menu
     */
    protected String getMenu() {
        return this.menu;
    }
}