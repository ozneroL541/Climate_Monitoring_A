package src.table;

public class Table {
    // Number of categories
    private final short n_categories = 7;
    // Category's scores
    private short[] scores = { 1, 1, 1, 1, 1, 1, 1, };
    // Category's notes
    private String[] notes = { "", "", "", "", "", "", "", };
    // Void Constructor
    public Table() {}
    // Constructor without notes
    public Table( short[] s ) {
        // If the lenght of the array is not equals to the number of the categories lunch an exception
        if ( s.length != this.n_categories ) {
            // TODO
            // Exception because the lenght is not valid
        } else {
            // For every category assign the correct score
            for ( short i = 0; i < this.n_categories; i++) {
                // If the score is valid assign it
                if ( s[i] >= 1  && s[i] <= 5 ) {
                    this.scores[i] = s[i];
                    // Else lunch an exception
                } else {
                    // TODO
                    // Exception, scores must be between 1 and 5
                }
            }
        }
    }
    // Complete constructor
    public Table( short[] s, String[] note ) {
        // If the lenght of the arraies is not equals to the number of the categories lunch exceptions
        if ( s.length != this.n_categories ) {
            // TODO
            // Exception because the lenght is not valid
        } else if ( note.length != n_categories ) {
            // TODO
            // Exception because the lenght is not valid
        } else {
            // For every category
            for ( short i = 0; i < this.n_categories; i++) {
                // If the score is valid assign it
                if ( s[i] >= 1  && s[i] <= 5 ) {
                    this.scores[i] = s[i];
                    // If the string is less than 256 assign it
                    if ( note[i].length() > 256 ) {
                        // TODO
                        // Exception, note must be 256 character or less
                    } else {
                        // Assign the note
                        this.notes = note;
                    }
                } else {
                    // TODO
                    // Exception, scores must be between 1 and 5
                }
            }
        }
    }
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
