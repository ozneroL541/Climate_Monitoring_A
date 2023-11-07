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

package src.common;

/**
 * Raccolta di metodi statici utilizzati da più classi.
 * @author Lorenzo Radice
 * @author Giacomo Paredi
 * @version 0.10.4
 */
public class CommonMethods {
    /**
     * Controlla che il nome inserito sia formato solo da lettere, apostrofi e spazi.
     * Il metodo accetta anche lettere accentate e di alcuni alfabeti non italiani.
     * @param name nome
     * @return true se il nome è valido
     */
    public static boolean isValidName(String name){
        // For every character in string
        for ( Character c : name.toCharArray() ) {
            // Check the validity of the character
            if (!Character.isLetter(c) && c != ' ' && c != '\'' && c != ',' ) {
                // Character not valid: Return false
                return false;
            }
        }
        // Character valid: return true
        return true;
    }
    /**
     * Controlla che il nome inserito sia formato solo da lettere, apostrofi e spazi.
     * Il metodo accetta solo lettere non accentate.
     * @param name nome
     * @return true se il nome è valido
     */
    public static boolean isValidASCIIName(String name){
        // For every character in string
        for ( Character c : name.toCharArray() ) {
            // Check the validity of the character
            if ( !isASCIILetter(c) && c != ' ' && c != '\'' && c != ',' ) {
                // Character not valid: Return false
                return false;
            }
        }
        // Character valid: return true
        return true;
    }
    // Check if the character is an ASCII letter
    private static boolean isASCIILetter( char c ) {
        // Return if it is an ASCII Letter
        return ( ( c >= 'a' && c <= 'z' ) || ( c >= 'A' && c <= 'Z' ) );
    }
}
