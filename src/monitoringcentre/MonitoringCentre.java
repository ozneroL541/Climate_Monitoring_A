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

package src.monitoringcentre;
// TODO Remove unused imports
import java.io.File;
import java.nio.file.FileSystems;
import java.util.ArrayList;

import src.common.CSV_Utilities;
import src.common.InputScanner;
import src.common.Research;

/**
 * Classe che contiene il centro di monitoraggio.
 * @author Riccardo Galimberti
 * @version 0.09.0
 */
public class MonitoringCentre {
    // private String via, civico, cap, comune, provincia;
    private String nome;
    private String [] indirizzo = new String[5];
    private String [] areeInteresse;
    private short userid;
    private final static String header = "nome, via/piazza, numero civico, cap, comune, provincia, userID, aree";

    private final static File f = FileSystems.getDefault().getPath("data", "CentroMonitoraggio.dati.csv").toFile();
    // Indexes in CSV file
    private final static class IndexOf {
        private final static short name = 0;
        private final static short address = 1;
        private final static short areas = 2;
        private final static short userid = 3;
        // Number of indexes
        private final static short indexes = 4;
        // Max index value
        private final static short max_index = 3;
    }

    /*
     * A cosa serve un costruttore che non inizializza i campi?
     * A cosa servono i campi se tanto il costruttore non li inizializza?
     */
    public MonitoringCentre(String nome, String [] indirizzo, String [] areeInteresse, short userid){
        if(CenterExistence(nome))
            registraCentroAree(nome, indirizzo, areeInteresse, userid);
    }

    //costruttore vuoto
    public MonitoringCentre(){
    }
    /*
     * Perché devo inserire i campi che dovrebbero essere assegnati nel costruttore?
     */
    public void registraCentroAree(String nome, String [] indirizzo, String [] areeInteresse, short userid){
        this.nome = nome;
        if(indirizzo.length == this.indirizzo.length){
            this.indirizzo = indirizzo;
        }else{
            /* 
             * Stampa l'errore ma memorizza lo stesso!
             */
            System.err.println("Errore: lunghezza array errata."); 
        }
        if(areeInteresse.length > 0){
            this.areeInteresse = areeInteresse;
        }else{
            System.err.println("Errore: Lista vuota."); 
        }
        this.userid = userid;
        memorizzaCentroAree(nome, indirizzo, areeInteresse, userid);
    }

    /**
     * Ritorna un array di stringhe dei nomi dei Centri di Monitoraggio.
     * Se non ci sono Centri ritorna null.
     * @return nomi dei centri
     */
    public String[] getCentri(){
        return Research.getColArray(f,0);
    }

    private void memorizzaCentroAree(String nome, String [] indirizzo, String [] areeInteresse, short userid){
        ArrayList<String> str = new ArrayList<String>();
        String aree = "";
        str.add(nome);
        short i = 0;
        for ( i = 0; i < indirizzo.length; i++) {
            str.add(indirizzo[i]);
        }
        str.add(String.valueOf(userid));
        
        for ( i = 0; i < areeInteresse.length - 1; i++) {
            aree += areeInteresse[i] + "-" ;
        }
        aree += areeInteresse[i];
        str.add(aree);
        String s[] = str.toArray(new String[str.size()]);
        CSV_Utilities.addArraytoCSV(f,s,header);
    }

    private boolean CenterExistence(String nome){
        boolean exists = false;
        if( f.exists() && Research.isStringInCol(f,0,nome))
            exists = true;
        else
            exists = false;

        return exists;
    }
    // TODO Modificare per aggiungere il centro metodo prototipale: totalmente incompleto e non testato
    /**
     * Permette di creare un Centro di Monitoraggio inserendone i dati e la ritorna.
     * Se la creazione fallisce ritorna null.
     * @return Centro di Monitoraggio creato
     */
    public static MonitoringCentre createArea() {
        // Provinces
        final File f_province = FileSystems.getDefault().getPath("data", "Provincia.csv").toFile();
        // Error string
        final String error = "Creazione del centro di monitoraggio terminata: creazione fallita";
        // Check if file exist
        if ( ! f_province.exists() ) {
            // Print Error
            System.err.println("ERRORE: il file " + f_province.getName() + " non si trova nella cartella \'" + f_province.getParent() + "\'.\n" );
            // Return null
            return null;
        }
        // Monitoring Centre to be returned
        MonitoringCentre mc = null;        
        // Array of strings of fields
        String[] fieldStrings = new String[IndexOf.max_index + 1];
        // Input String
        String in = "";
        // Exit condition
        boolean exit = false;
        // Try catch for Input Exception
        try {
            do {
                // Request
                System.out.print("Inserire nome centro:\t\t");
                // Input
                in = InputScanner.INPUT_SCANNER.nextLine();
                // Check if it is correct
                if ((exit = argumentCorrect(in, IndexOf.name))) {
                    // Check if there is another area wth the same name
                    if ( f.exists() && Research.OneStringInCol(f, IndexOf.name, in) >= 0 ) {
                        // Output
                        System.out.println("Esiste già un centro con lo stesso nome.");
                        System.out.println(error);
                        // Exit
                        return null;
                    } else {
                        // Assign input to name
                        fieldStrings[IndexOf.name] = in;
                    }
                }
            } while (!exit);
            // Request
            System.out.print("Inserire via:\t\t");
            // Input
            in = InputScanner.INPUT_SCANNER.nextLine();
            // TODO Inserire controllo

            // Assign input to real_name
            fieldStrings[IndexOf.address] = in;
            do {
                // Request
                System.out.print("Inserire codice provincia:\t");
                // TODO: usare il codice provincia per ottenere regione (se necessario) e nazione (anche se è per forza Italia).
                // Input
                in = InputScanner.INPUT_SCANNER.nextLine();
                // Country Code must be made of 2 characters
                // TODO
                if ( ! argumentCorrect(in, IndexOf.address) ) {
                    // Stay in loop
                    exit = false;
                } else {
                    // To upper case
                    in = in.toUpperCase();
                    // Record array
                    String [] cc_array = Research.getRecordByData(f_province, 1, in);
                    // If Country code does not exist
                    if (cc_array == null ) {
                        // Output
                        System.out.println("Non è stata trovata alcuna provincia col codice inserito.");
                        // Stay in loop
                        exit = false;
                    } else {
                        // Assign Provincia
                        // TODO
                        // Exit
                        exit = true;
                    }
                }
            } while (!exit);
        } catch (Exception e) {
            // Output Exception
            e.printStackTrace();
        }
        // Build Monitoring Centre
        // TODO
        // Return Geographic Area
        return mc;        
    }
    //TODO Field checker
    private static boolean argumentCorrect(String in, short name) {
        // Suggestion
        /*
        // If str in null exit
        if (str == null || str.length() < 1) {
            // Return false
            return false;
        }
        // Search
        switch (col_index) {
            // Check Geoname ID
            case IndexOf.geoname_id:
                // Try parsing
                try {
                    // Research
                    Integer id = Integer.parseInt(str);
                    // Integer is valid only if it is positive
                    if ( id < 0 ) {
                        // Error output
                        System.out.println("Il Geoname ID inserito non è valido.");
                        // Return false
                        return false;
                    }
                // Parsing Error
                } catch (Exception e) {
                    // Error output
                    System.out.println("Il Geoname ID deve essere formato solo da numeri.\nIl Geoname ID inserito è errato.");
                    // Return false
                    return false;
                }
                // Return true
                return true;
            // Check Real Name
            case IndexOf.real_name:
            // Check Generic name
            case IndexOf.generic_name:
                // Check if name is valid
                if (CommonMethods.isValidName(str)) {
                    // Return True
                    return true;
                } else {
                    // Error output
                    System.out.println("Il nome inserito contiene caratteri non validi.");
                    // Return False
                    return false;
                }
            // Check ASCII name
            case IndexOf.ascii_name:
            // Check Country Name
            case IndexOf.country_name:
                // If is ASCII return true
                if (Charset.forName("US-ASCII").newEncoder().canEncode(str)) {
                    if ( CommonMethods.isValidASCIIName(str) ) {
                        // Return True
                        return true;
                    } else {
                        // Error output
                        System.out.println("Il nome inserito contiene caratteri non validi.");
                        // Return False
                        return false;
                    }
                } else {
                    // Error output
                    System.out.println("Il nome inserito deve essere formato solo da caratteri ASCII.");
                    // Return False
                    return false;
                }
            // Check Country Code
            case IndexOf.country_code:
                // Check cc lenght
                if ( str.length() != 2 ) {
                    // Error Ouptut
                    System.out.println("Lunghezza Country Code errata.");
                    System.out.println("Il Country Code deve essere di 2 caratteri.");
                    // Return False
                    return false;
                } else if ( ! Charset.forName("US-ASCII").newEncoder().canEncode(str)) {
                    // Error output
                    System.out.println("Il codice inserito deve essere formato solo da caratteri ASCII.");
                    // Return False
                    return false;
                } else if (! (str.matches("[a-zA-Z]+"))) {
                    // Error output
                    System.out.println("Il codice inserito deve essere formato solo da lettere.");
                    // Return False
                    return false;
                } else {
                    // Return True
                    return true;
                }
            // Check Coordinates
            case IndexOf.coordinates:
                // Try Parsing
                try {
                    // Pass to double
                    double [] coo = Coordinates.parseCoordinates(str);
                    // If the coordinates are not in the range of the Earth
                    if ( coo[0] > 90.0 || coo[0] < -90.0 || coo[1] > 180.0 || coo[1] < -180.0 ) {
                        // Error message
                        System.out.println("Valori coordinate errati.");
                        System.out.println("La latitudine deve essere compresa tra -90.00 e 90.00.");
                        System.out.println("La longitudine deve essere compresa tra -180.00 e 180.00.");
                        // Return False
                        return false;
                    }
                } catch (NullPointerException e) {
                    // Error message
                    System.out.println("Formato Coordinate incorretto.");
                    System.out.println("Il formato delle coordinate deve essere il seguente: \"lat, lon\".");
                    // Return False
                    return false;
                } catch ( Exception e ) {
                    // Not managed exception
                    // Error
                    e.printStackTrace();
                    // Return false
                    return false;
                }
                // Return True
                return true;
            default:
                // Error
                System.err.println("ERRORE: codice lista inesistente");
                return false;
        }
         */
        return false;
    }

    // TODO Remove test main
    public static void main(String[] args) {
        String nome = "Centro Prova";
        String [] indirizzo = { "Via Regina Teodolinda" ,"37", "Como", "CO", "Italia" };
        String [] areeInteresse = {"123456", "1234567", "123456" };
        short userid = 00002;
        /*
         * Perché devo inserire 2 volte le stesse cose?
         */
        MonitoringCentre m = new MonitoringCentre(nome, indirizzo, areeInteresse, userid);
        /*
         * A cosa serve registraAree pubblico se tanto lo fa già il costruttore?
         */
        m.registraCentroAree(nome, indirizzo, areeInteresse, userid);
        
    }
}
