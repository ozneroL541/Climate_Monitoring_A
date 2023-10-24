/**************************************
 * Matricola    Cognome     Nome
 * 754530       Galimberti  Riccardo
 * 755152       Paredi      Giacomo
 * 753252       Radice      Lorenzo
 * Sede: Como
***************************************/

package src.table;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Un oggetto della classe <code>Table</code> rappresenta una tabella
 * che contiene i valori dei prametri rilevati da una zona geografica.
 * La tabella ha 7 categorie per i valori inseriti.
 * @author Lorenzo Radice
 * @version 0.0.2
 */
public class Table {
    // Number of categories
    private final static short n_categories = 7;
    // Max characters number for notes
    private final static short max_char_notes = 256;
    // Min score
    private final static short min_score = 1;
    // Max score
    private final static short max_score = 5;
    // Category's scores
    private short[] scores = { 1, 1, 1, 1, 1, 1, 1 };
    // Category's notes
    private String[] notes = { "", "", "", "", "", "", "" };
    // Score and note
    private class Data {
        // Score
        private short score = 0;
        // Note
        private String note = "";
    }
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
        if ( s == null || s.length != n_categories ) {
            // Error output because the lenght is not valid
            System.err.println("Errore: lunghezza array valori parametri tabella errata.");
        } else {
            // For every category assign the correct score
            for ( short i = 0; i < n_categories; i++) {
                // If the score is valid assign it
                if ( Table.isScoreCorrect(s[i]) ) {
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
        if ( s == null || s.length != n_categories ) {
            // Exception because the lenght is not valid
            System.err.println("Errore: lunghezza array valori parametri tabella errata.");
        } else if ( note == null || note.length != n_categories ) {
            // Exception because the lenght is not valid
            System.err.println("Errore: numero valori note tabella errati.");
        } else {
            // For every category
            for ( short i = 0; i < n_categories; i++) {
                // If the score is valid assign it
                if ( Table.isScoreCorrect(s[i]) ) {
                    // Assign Score
                    this.scores[i] = s[i];
                    // If the string is less than 256 assign it
                    if ( Table.isNoteShort(note[i]) ) {
                        // Assign the note
                        this.notes[i] = note[i];
                    } else {
                        // Exception, note must be 256 character or less
                        System.err.println("Lunghezza nota errata.\nLunghezza massima: 256 caratteri.");
                    }
                } else {
                    // Exception, scores must be between 1 and 5
                    System.err.println("Errore: i valori fuori intervallo(1-5).");
                }
            }
        }
    }
    /*
     * Costruttore con classe privata Data
     */
    public Table( Data[] d ) {
        if ( d == null && d.length != n_categories ) {
            // Exception because the lenght is not valid
            System.err.println("Errore: lunghezza array valori dati tabella errata.");
        } else {
            // For every category
            for (int i = 0; i < n_categories; i++) {
                // If the score is valid assign it
                if ( d[i].score >= min_score  && d[i].score <= max_score ) {
                    this.scores[i] = d[i].score;
                    // If the string is less than 256 assign it
                    if ( d[i].note.length() > max_char_notes ) {
                        // Exception, note must be 256 character or less
                        System.err.println("Lunghezza nota errata.\nLunghezza massima: 256 caratteri.");
                    } else {
                        // Assign the note
                        this.notes[i] = d[i].note;
                    }
                } else {
                    // Exception, scores must be between 1 and 5
                    System.err.println("Errore: i valori fuori intervallo(1-5).");
                }
            }
        }
    }
    /**
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
        for ( short i = 0; i < n_categories; i++ ) {
            // Add the line of the table
            str += "\n" + climate_categories[i] + "\t" + explanations[i] + "\t  " + this.scores[i] + "\t" + this.notes[i];
        }
        return str;
    }
    /**
     * Chiede all'utante di inserire i parametri della ricerca e ritorna la tabella che li contiene.
     */
    public static Table MakeTable() {
        // Datas Input
        Data[] datas = new Data[n_categories];
        // Table
        Table t = null;
        // For every category
        for (short i = 0; i < n_categories; i++) {
            // If one input is null
            if ( (datas[i] = askData(i)) == null )
                // Return null
                return null;
        }
        // Make Table
        t = new Table(datas);
        // Return datas
        return t;
    }
    private static Data askData( short index ) {
        // Input data
        Data data_in = null;
        // Question to the user
        String question = "Inserire ";
        // Complete question
        switch (index) {
            case 0:
                question += "Vento:\t";
                break;
            case 1:
                question += "Umidità:\t";
                break;
            case 2:
                question += "Pressione:\t";
                break;
            case 3:
                question += "Temperatura:\t";
                break;
            case 4:
                question += "Precipitazioni:\t";
                break;
            case 5:
                question += "Altitudine ghiacchiai:\t";
                break;
            case 6:
                question += "Massa dei ghiacciai:\t";
                break;
            default:
                System.err.println("Indice errato.");
                return null;
        }
        // Exit condition
        boolean exit = true;
        // Integer input
        Integer integ_in = 0;
        // String input
        String str_in = "";
        // Short input
        short short_in = 0;
        try (Scanner sc = new Scanner(System.in)){
            // Output question
            System.out.print(question);
            // Input Score
            do {
                // TODO fix this input
                // Integer Input
                integ_in = sc.nextInt();
                // Pass input as a short
                short_in = integ_in.shortValue();
                // Check the input
                if ( ! Table.isScoreCorrect(short_in) ) {
                    // Do not exit
                    exit = false;
                    // Error message
                    System.out.println("Valore non valido.\nInserire un valore compreso tra " + min_score + " e " + max_score + ".");
                    // Reinsert
                    System.out.println("Reinserire:\t");
                } else {
                    // Assign it to the data
                    data_in.score = short_in;
                    // Exit
                    exit = true;
                }
            } while ( ! exit);
            // Note question
            System.out.println("Vuoi inserire una nota(S/N)?");
            // Answer input
            str_in = sc.nextLine();
            // Uppercase
            str_in = str_in.toUpperCase();
            if ( str_in.charAt(0) == 'S' || str_in.charAt(0) == 'Y' ) {
                // Request input
                System.out.println("Inserire nota: ");
                // Input Note
                do {
                    // Note input
                    str_in = sc.nextLine();
                    // Check the input
                    if ( ! Table.isNoteShort(str_in) ) {
                        // Do not exit
                        exit = false;
                        // Error message
                        System.out.println("La nota non può essere più lunga di " + max_char_notes + " caratteri.");
                        // Reinsert
                        System.out.println("Reinserire:\t");
                    } else {
                        // Assign it to the note of data
                        data_in.note = str_in;
                        // Exit
                        exit = true;
                    }
                } while ( ! exit);
            } else {
                data_in.note = null;
            }
        } catch ( InputMismatchException e ) {
            // Error output
            e.printStackTrace();
            // Return null
            return null;
        } catch (Exception e) {
            // Error output
            e.printStackTrace();
            // Return null
            return null;
        }
        return data_in;
    }
    // Check if the score is in the correct range
    private static boolean isScoreCorrect( short i ) {
        return ( i >= min_score && i <= max_score );
    }
    // Check if the note is shorter than the max
    private static boolean isNoteShort( String str ) {
        return (str.length() <= max_char_notes);
    }

    //TODO Remove test main
    public static void main(String[] args) {
        Table t = null;
        t = Table.MakeTable();
        System.out.println(t.toString());
    }
}
