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
 * @author Giacomo Paredi
 * @author Lorenzo Radice
 * @version 1.0.0
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
    /**
     * Controlla che il valore inserito sia una stringa di caratteri ASCII accettabili per un indirizzo.
     * @param add indirizzo da controllare
     * @return true se l'indirizzo è valido
     */
    public static boolean isASCIIValidAddress( String add ) {
        // If does not exist
        if ( add == null || add.length() <= 0 ) {
            return false;
        }
        // For every character in string
        for ( Character c : add.toCharArray() ) {
            // Check the validity of the character
            if ( !isASCIILetter(c) && !Character.isDigit(c) && c != ' ' && c != '\'' && c != ',' && c != '/' ) {
                // Character not valid: Return false
                return false;
            }
        }
        // Character valid: return true
        return true;
    }
    /**
     * Controlla che la stringa inserita come arogmento sia formata
     * solo da cifre e che sia un numero intero positivo.
     * @param str stringa
     * @return true se il valore in argomento è valido
     */
    public static boolean isOnlyInt( String str ) {
        // Try parsing
        try {
            // Research
            Integer id = Integer.parseInt(str);
            // Integer is valid only if it is positive
            if ( id < 0 ) {
                // Return false
                return false;
            }
        // Parsing Error
        } catch (Exception e) {
            // Return false
            return false;
        }
        // Return true
        return true;
    }
    /**
     * Controlla che la stringa inserita sia formata da esattamente 2 lettere ASCII.
     * @param s stringa
     * @return true se l'argomento è valido
     */
    public static boolean isTwoLetters( String s ) {
        // Must be 2 lettes
        if ( s == null || s.length() != 2 ) {
            return false;
        }
        // For each letter
        for (Character c : s.toCharArray()) {
            // If the letter are not ASCII then is false
            if (!isASCIILetter(c)) {
                // Return false
                return false;
            }
        }
        // Return true
        return true;
    }
    /**
     * Ritorna una stringa trasformata in modo tale che sia tutta maiuscola e le vocali non abbiano accenti.
     * @param str string
     * @return stringa trasformata
     */
    public static String toNoAccent( String str ) {
        // Exit String
        String s = "";
        // To Upper Case
        str = str.toUpperCase();
        // For each letter
        for (char c : str.toCharArray()) {
            // Exchange letter into valid one
            switch (c) {
                case 'À':
                case 'Á':
                    s += "A'";
                    break;
                case 'È':
                case 'É':
                    s += "E'";
                    break;
                case 'Ì':
                case 'Í':
                    s += "I'";
                    break;
                case 'Ò':
                case 'Ó':
                    s += "O'";
                    break;
                case 'Ù':
                case 'Ú':
                    s += "U'";
                    break;
                default:
                    s += c;
                    break;
            }
        }
        // Return valid String
        return s;
    }
    /**
     * Controlla che la stringa in argomento sia una una stringa contenente caratteri per uscire da un loop.
     * @param input input
     * @return true se esce dal loop
     */
    public static boolean ExitLoop( String input ) {
        // Input to upper case
        input = input.toUpperCase();
        return input.contains("N") || input.contains("Q") || input.contains("ESC") || input.contains("EXIT");
    }
    /**
     * Controlla che un carattere sia una lettera ASCII.
     * @param c carattere da controllare
     * @return true se la lettere è un carattere
     */
    private static boolean isASCIILetter( char c ) {
        // Return if it is an ASCII Letter
        return ( ( c >= 'a' && c <= 'z' ) || ( c >= 'A' && c <= 'Z' ) );
    }
}
