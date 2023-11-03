/**************************************
 * Matricola    Cognome     Nome
 * 754530       Galimberti  Riccardo
 * 755152       Paredi      Giacomo
 * 753252       Radice      Lorenzo
 * Sede: Como
***************************************/

package src.geographicarea;

/**
 * Classe che contiene metodi per la gestione delle coordinate geografiche.
 * @author Lorenzo Radice
 * @version 0.10.0
 */
public class Coordinates {
    /**
     * Divide la stringa in coordinate.
     * In caso di errore ritorna null.
     * @param coo string
     * @return un array con 2 coordinate
     */
    public static double[] parseCoordinates ( String coo ){
        // Output
        double [] c = new double[2];
        // Spitted string
        String [] splitted = coo.split(", ");
        // If the string is not splitted abort
        if ( splitted.length != 2 )
            return null;
        // If parsing don't work return null
        try {
            // First coordinate
            c[0] = Double.parseDouble(splitted[0]);
            // Second coordinate
            c[1] = Double.parseDouble(splitted[1]);
            // Return the coordinates
            return c;
        } catch (Exception e) {
            // In case of error return null
            return null;
        }
    }
}
