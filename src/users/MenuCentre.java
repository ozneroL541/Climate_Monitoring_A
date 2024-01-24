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
 * Classe che contiene il menu per associare l'Operatore Autorizzato ad un Centro di Monitoraggio.
 * @author Giacomo Paredi
 * @version 1.0.1
 */
class MenuCentre {
    /**
     * Opzioni Menu
     */
    protected static final record IndexOf() {
        /**
         * Seleziona centro esistente
         */
        protected static final short existingCentre = 1;
        /**
         * Crea un nuovo centro e associa
         */
        protected static final short newCentre = 2;
        /**
         * Non associare centro
         */
        protected static final short doNothing = 3;
    }
    /** Stringa del menu */
    private String menu = null;
    /**
     * Costruisce un oggetto menu
     */
    protected MenuCentre(){
        // Separator string
        final String separator = " - ";
        // Header
        final String header = "\tMenu associazione centro\n";
        // Options array
        final String[] options = {
            (IndexOf.existingCentre     + separator + "Associazione ad un centro esistente"),
            (IndexOf.newCentre          + separator + "Associazione ad un centro nuovo"),
            (IndexOf.doNothing          + separator + "Associazione in un secondo momento"),
        };
        // Initialize menu
        this.menu = header;
        // For every element in the options array
        for ( short i = 0; i < options.length; i++ ) {
            // Create the menu string
            this.menu += options[i] + '\n';
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
