package src.common_static_methods.common;

public class CommonMethods {
    /**
     * Crea una riga che può essere aggiunta ad un file CSV.
     * La stringa creata include il newline successivo.
     * @param record array di Strings
     * @return stringa per CSV
     */
    public static String toCSVLine(String [] record) {
        // To be returned
        String str = "";
        // For each field
        for (String s : record) {
            // If cointains , put ""
            if (s.contains(","))
                str += "\"" + s + "\"";
            else
                str += s;
            // Separator
            str += ", ";
        }
        // Add newline
        str = str.substring(0, str.length() - 2 ) + "\n";
        // Return String
        return str;
    }
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
