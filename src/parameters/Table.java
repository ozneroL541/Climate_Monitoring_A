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

package src.parameters;

import java.util.InputMismatchException;

import src.common.InputScanner;

/**
 * Un oggetto della classe <code>Table</code> rappresenta una tabella
 * che contiene i valori dei prametri rilevati da una zona geografica.
 * La tabella ha 7 categorie per i valori inseriti.
 * @author Lorenzo Radice
 * @version 0.22.0
 */
public class Table {
    // Score and note
    private static class Data_SN {
        // Score
        public short score = 0;
        // Note
        public String note = "";
        // Costructor
        public Data_SN(){}
    }
    // Number of categories
    public final static short n_categories = 7;
    // Max characters number for notes
    private final static short max_char_notes = 256;
    // Min score
    private final static short min_score = 1;
    // Max score
    private final static short max_score = 5;
    /**
     * Chiede all'utente di inserire i parametri della ricerca e ritorna la tabella che li contiene.
     * @return tabella
     */
    public static Table MakeTable() {
        // Datas Input
        Data_SN[] datas = new Data_SN[n_categories];
        // Table
        Table t = null;
        // For every category
        for (short i = 0; i < n_categories; i++) {
            // Initialize data
            datas[i] = new Data_SN();
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
    // Ask about datas
    private static Data_SN askData( short index ) {
        // Input data
        Data_SN data_in = new Data_SN();
        // Question to the user
        String question = "Inserire ";
        // Complete question
        switch (index) {
            case 0:
                question += "Vento:\t\t\t";
                break;
            case 1:
                question += "Umidità:\t\t";
                break;
            case 2:
                question += "Pressione:\t\t";
                break;
            case 3:
                question += "Temperatura:\t\t";
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
        // String input
        String str_in = "";
        // Short input
        short short_in = 0;
        // Output question
        System.out.print(question);
        // Input Score
        do {
            try{
                // Short Input
                short_in = InputScanner.INPUT_SCANNER.nextShort();
                // Check the input
                if ( ! Table.isScoreCorrect(short_in) ) {
                    // Do not exit
                    exit = false;
                    // Error message
                    System.out.println("Valore non valido.");
                    System.out.println("Inserire un numero compreso tra " + min_score + " e " + max_score + ".");
                    // Reinsert
                    System.out.print("Reinserire:\t\t\t");
                } else {
                    // Reset input scanner
                    InputScanner.INPUT_SCANNER.nextLine();
                    // Assign it to the data
                    data_in.score = short_in;
                    // Exit
                    exit = true;
                }
            } catch ( InputMismatchException e ) {
                // Reset input scanner
                InputScanner.INPUT_SCANNER.nextLine();
                // Error message
                System.err.println("Valore non valido.");
                System.err.println("Inserire un numero compreso tra " + min_score + " e " + max_score + ".");
                // Reinsert
                System.err.print("Reinserire:\t\t\t");
                // Do not exit
                exit = false;
            } catch (Exception e) {
                // Error output
                e.printStackTrace();
                // Return null
                return null;
            }
        } while ( ! exit);
        // Note question
        System.out.print("Vuoi inserire una nota(S/N)?\t");
        // Answer input
        str_in = InputScanner.INPUT_SCANNER.nextLine();
        // Uppercase
        str_in = str_in.toUpperCase();
        if ( str_in != null && ! str_in.isEmpty() && (str_in.charAt(0) == 'S' || str_in.charAt(0) == 'Y') ) {
            // Request input
            System.out.print("Inserire nota:\t\t\t");
            // Input Note
            do {
                try {
                    // Note input
                    str_in = InputScanner.INPUT_SCANNER.nextLine();
                    // Check the input
                    if ( ! Table.isNoteShort(str_in) ) {
                        // Do not exit
                        exit = false;
                        // Error message
                        System.out.println("La nota non può essere più lunga di " + max_char_notes + " caratteri.");
                        // Reinsert
                        System.out.print("Reinserire:\t\t\t");
                    } else {
                        // Assign it to the note of data
                        data_in.note = str_in;
                        // Exit
                        exit = true;
                    }
                }  catch ( InputMismatchException e ) {
                    // Error message
                    System.err.println("Inserimento non valido.");
                    // Reinsert
                    System.err.print("Reinserire:\t\t\t");
                    // Do not exit
                    exit = false;
                } catch (Exception e) {
                    // Error output
                    e.printStackTrace();
                    // Return null
                    return null;
                }
            } while ( ! exit);
        } else {
            // No note provided 
            data_in.note = null;
        }
        // Return datas
        return data_in;
    }
    // Check if the score is in the correct range
    private static boolean isScoreCorrect( short i ) {
        // Return true if the score is in the correct range
        return ( i >= min_score && i <= max_score );
    }
    // Check if the note is shorter than the max
    private static boolean isNoteShort( String str ) {
        // Return true if the length of the string is acceptable
        return (str.length() <= max_char_notes);
    }
    // Category's scores
    private short[] scores = { 0, 0, 0, 0, 0, 0, 0 }; 
    // Category's notes
    private String[] notes = { "", "", "", "", "", "", "" };
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
        // If the length of the array is not equals to the number of the categories lunch an exception
        if ( s == null || s.length != n_categories ) {
            // Error output because the length is not valid
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
                    System.err.println("Errore: valori fuori intervallo(" + min_score + "-" + max_score + ").");
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
        // If the length of the arraies is not equals to the number of the categories lunch exceptions
        if ( s == null || s.length != n_categories ) {
            // Exception because the length is not valid
            System.err.println("Errore: lunghezza array valori parametri tabella errata.");
        } else if ( note == null || note.length != n_categories ) {
            // Exception because the length is not valid
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
                        this.notes[i] += note[i];
                    } else {
                        // Exception, note must be 256 character or less
                        System.err.println("Lunghezza nota errata.\nLunghezza massima: " + max_char_notes + " caratteri.");
                    }
                } else {
                    // Exception, scores must be between 1 and 5
                    System.err.println("Errore: i valori fuori intervallo(" + min_score + "-" + max_score + ").");
                }
            }
        }
    }
    /*
     * Crea una tabella utilizzando la classe privata Data_SN
     * @param d datas
     */
    private Table( Data_SN[] d ) {
        if ( d == null || d.length != n_categories ) {
            // Exception because the length is not valid
            System.err.println("Errore: lunghezza array valori dati tabella errata.");
        } else {
            // For every category
            for (int i = 0; i < n_categories; i++) {
                // If the score is valid assign it
                if ( Table.isScoreCorrect(d[i].score) ) {
                    this.scores[i] = d[i].score;
                    // If note not null
                    if ( d[i].note == null || d[i].note.isEmpty() ) {
                        // Assign null to note
                        this.notes[i] = "";
                    } else if ( Table.isNoteShort(d[i].note) ) {
                        // Assign the note
                        this.notes[i] += d[i].note;
                    } else {
                        // Assign null to note
                        this.notes[i] = "";
                        // Exception, note must be 256 character or less
                        System.err.println("Lunghezza nota errata.\nLunghezza massima: " + max_char_notes + " caratteri.");
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
        for ( short i = 0; i < n_categories; i++ ) {
            // Add the line of the table
            str += "\n" + climate_categories[i] + "\t" + explanations[i] + "\t  " + this.scores[i] + "\t" + this.notes[i];
        }
        return str;
    }
    // Create an array of strings ad add id at the beginning
    public String[] toStrings() {
        // Array to return
        String[] strings = new String[ (n_categories * 2) ];
        // Check parameter existance
        if ( scores == null ) {
            // Exit
            return null;
        }
        // Parameters
        for (short i = 0; i < n_categories; i++) {
            // Assign every parameter to strings
            strings[i] = "" + this.scores[i];
            // Assign every note to strings
            strings[n_categories + i] = this.notes[i];
        }
        // Return array
        return strings;
    }
}
