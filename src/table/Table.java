/**************************************
 * Matricola    Cognome     Nome
 * 754530       Galimberti  Riccardo
 * 755152       Paredi      Giacomo
 * 753252       Radice      Lorenzo
 * Sede: Como
***************************************/

package src.table;
/**
 * Un oggetto della classe <code>Table</code> rappresenta una tabella
 * che contiene i valori dei prametri rilevati da una zona geografica.
 * La tabella ha 7 categorie per i valori inseriti.
 * @author Lorenzo Radice
 * @version 0.0.1
 */
public class Table {
    // Number of categories
    private final short n_categories = 7;
    // Category's scores
    private short[] scores = { 1, 1, 1, 1, 1, 1, 1, };
    // Category's notes
    private String[] notes = { "", "", "", "", "", "", "", };
    /**
     * Costruttore vuoto
     */
    public Table() {}
    /**
     * Costruttore senza note.
     * Costruisce la tabella utilizzando solo i valori dei parametri.
     * I valori vanno inseriti come un'array di shorts.
     * L'array deve essere di lunghezza pari al numero categorie.
     * @param s array dei parametri
     */
    public Table( short[] s ) {
        // If the lenght of the array is not equals to the number of the categories lunch an exception
        if ( s == null || s.length != this.n_categories ) {
            // Error output because the lenght is not valid
            System.err.println("Errore: lunghezza array valori parametri tabella errata.");
        } else {
            // For every category assign the correct score
            for ( short i = 0; i < this.n_categories; i++) {
                // If the score is valid assign it
                if ( s[i] >= 1  && s[i] <= 5 ) {
                    this.scores[i] = s[i];
                    // Else lunch an exception
                } else {
                    // Exception, scores must be between 1 and 5
                    System.err.println("Errore: valori fuori intervallo(1-5).");
                }
            }
        }
    }
    /**
     * Costruttore completo.
     * Costruisce la tabella utilizzando sia i valori dei parametri che possibili note.
     * I valori vanno inseriti come un array di shorts.
     * Le note vanno inserite come un array di stringhe.
     * Gli array inseriti devono essere di lunghezza pari al numero di categorie.
     * @param s array dei parametri
     * @param note array delle note
     */
    public Table( short[] s, String[] note ) {
        // If the lenght of the arraies is not equals to the number of the categories lunch exceptions
        if ( s == null || s.length != this.n_categories ) {
            // Exception because the lenght is not valid
            System.err.println("Errore: lunghezza array valori parametri tabella errata.");
        } else if ( note == null || note.length != n_categories ) {
            // Exception because the lenght is not valid
            System.err.println("Errore: numero valori note tabella errati.");
        } else {
            // For every category
            for ( short i = 0; i < this.n_categories; i++) {
                // If the score is valid assign it
                if ( s[i] >= 1  && s[i] <= 5 ) {
                    this.scores[i] = s[i];
                    // If the string is less than 256 assign it
                    if ( note[i].length() > 256 ) {
                        // Exception, note must be 256 character or less
                        System.err.println("Lunghezza nota errata.\nLunghezza massima: 256 caratteri.");
                    } else {
                        // Assign the note
                        this.notes = note;
                    }
                } else {
                    // Exception, scores must be between 1 and 5
                    System.err.println("Errore: i valori fuori intervallo(1-5).");
                }
            }
        }
    }
    /**
     * Restituisce la tabella sotto formato di stringa
     */
    @Override
    public String toString() {
        // Name of the climate categories
        final String[] climate_categories = {
            "Vento                   ",
            "Umidità                 ",
            "Pressione               ",
            "Temperatura             ",
            "Precipitazioni          ",
            "Altitudine dei ghiacciai",
            "Massa dei ghiacciai     ",
        };
        // Explanation of the climate categories
        final String[] explanations = {
            "Velocità del vento (km/h), suddivisa in fasce ",
            "% di Umidità, suddivisa in fasce              ",
            "In hPa, suddivisa in fasce                    ",
            "In °C, suddivisa in fasce                     ",
            "In mm di pioggia, suddivisa in fasce          ",
            "In m, suddivisa in piogge                     ",
            "In kg, suddivisa in fasce                     ",
        };
        // Header Table
        final String table_header = "Climate Category        \tExplanation                                   \tScore\tNotes (max 256 characters)";
        // Output string
        String str = "";
        // Start adding the header to the output string
        str += table_header;
        // For every category
        for ( short i = 0; i < this.n_categories; i++ ) {
            // Add the line of the table
            str += "\n" + climate_categories[i] + "\t" + explanations[i] + "\t  " + this.scores[i] + "\t" + this.notes[i];
        }
        return str;
    }
}
