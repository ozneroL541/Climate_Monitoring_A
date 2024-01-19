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

import java.io.File;
import java.nio.file.FileSystems;
import java.util.ArrayList;

import src.common.*;
import src.geographicarea.GeographicArea;

/**
 * Classe che contiene il centro di monitoraggio.
 * @author Riccardo Galimberti
 * @author Lorenzo Radice
 * @author Giacomo Paredi
 * @version 0.28.0
 */
public class MonitoringCentre {
    /**
     * Indici del file dei comuni
     */
    private final static record col_comuni() {
        /** Comune */
        private final static short comune = 0;
        /** Codice di Avviamento Postale */
        private final static short CAP = 2;
        /** Provincia */
        private final static short provincia = 1;        
    }
    /**
     * Indici del file dei centri di monitoraggio
     */
    private final static record IndexOf() {
        /** Nome Centro */
        private final static short name = 0;
        /** Indirizzo */
        private final static short address = 1;
        /** Aree di interesse associate */
        private final static short areas = 2;
        /** Indici dell'indirizzo */
        private final static record Iadd() {
            /** Via */
            private final static short via = 0;
            /** Numero Civico */
            private final static short civico = 1;
            /** Codice di Avviamento Postale */
            private final static short CAP = 2;
            /** Comune */
            private final static short comune = 3;
            /** Provincia */
            private final static short prov = 4;
            /** Lunghezza array degli indirizzi */
            private final static short length = 5;
        }
        /** Numero di indirizzi */
        private final static short indexes = 3;
    }
    /** Intestazione del file dei Centri di Monitoraggio */
    private final static String header = "Nome,Via,Civico,CAP,Comune,Provincia,Aree";
    /** File dei Centri di Monitoraggio */
    private final static File f = FileSystems.getDefault().getPath("data", "CentroMonitoraggio.dati.csv").toFile();
    /** File dei comuni */
    private final static File listcomuni = FileSystems.getDefault().getPath("resources", "comuni-localita-cap-italia.csv").toFile();
    /** Lunghezza CAP */
    private final static short cap_length = 5;
    /**
     * Permette di costruire un oggetto MonitoringCentre conoscendo solo il nome.
     * I restanti attributi verranno letti dal file CentroMonitoraggio.dati.csv.
     * @param nome nome del centro
     * @return centro di monitoraggio
     */
    public static MonitoringCentre getCentreByName(String nome){

        String[] indirizzo=new String[IndexOf.Iadd.length];
        String[] areeInteresse = null;

        String[] record=Research.getRecordByData(f, IndexOf.name, nome);
        if(record==null){
            return null;
        }else{
            indirizzo[IndexOf.Iadd.via]     = record[IndexOf.Iadd.via + IndexOf.areas];
            indirizzo[IndexOf.Iadd.civico]  = record[IndexOf.Iadd.civico + IndexOf.areas];
            indirizzo[IndexOf.Iadd.CAP]     = record[IndexOf.Iadd.CAP + IndexOf.areas];
            indirizzo[IndexOf.Iadd.comune]  = record[IndexOf.Iadd.comune + IndexOf.areas];
            indirizzo[IndexOf.Iadd.prov]    = record[IndexOf.Iadd.prov + IndexOf.areas];
            areeInteresse=record[IndexOf.Iadd.length - 1 + IndexOf.areas].split("-");
            return new MonitoringCentre(nome, indirizzo, areeInteresse);
        }
    }
    /**
     * Ritorna un array di stringhe dei nomi dei Centri di Monitoraggio.
     * Se non ci sono Centri ritorna null.
     * @return nomi dei centri
     */
    public static String[] getCentri(){
        // Check file existence
        if (!f.exists()) {
            return null;
        }
        return Research.getColArray(f,IndexOf.name);
    }
    /**
     * Controlla se il nome inserito esiste all'interno del file CentroMonitoraggio.dati.csv
     * @param nome nome del centro
     * @return true se il nome è presente nel file
     */
    public static boolean CenterExistence(String nome) {
        return (f.exists() && Research.isStringInCol(f,IndexOf.name,nome));
    }
    /**
     * Richiede di creare un centro, se la creazione ha avuto successo, la salva sul file.
     * @return nome del centro se l'esecuzione ha avuto successo.
     */
    public static String registraCentroAree() {
        // Create Centre
        MonitoringCentre mc = createCentre();
        // Check if center was created
        if ( mc == null || !mc.Exist() ) {
            // Do not insert the centre
            return null;
        }
        // Save Centre
        if (mc.memorizzaCentro())
            return mc.getNome();
        else
            return null;
    }
    /**
     * Controlla l'esistenza del file dei Centri di Monitoraggio.
     * @return true se il file esiste
     */
    public static boolean FileExist() {
        return f.exists();
    }
    /**
     * Permette di creare un Centro di Monitoraggio e lo ritorna.
     * Se la creazione fallisce ritorna null.
     * @return Centro di Monitoraggio creato
     */
    private static MonitoringCentre createCentre() {
        // Error string
        final String error = "Creazione del centro di monitoraggio terminata: creazione fallita";
        String nome = null;
        String [] indirizzo = new String [IndexOf.Iadd.length];

        // Name
        nome = AskName();
        // Check name
        if (nome == null) {
            System.err.println(error);
            return null;
        } else {
            // Address
            indirizzo = AskAddress();
        }
        // Geographic Areas
        String[] aree = setAreeGeografiche();

        //return Monitoring Centre
        return new MonitoringCentre(nome, indirizzo, aree);        
    }
    /**
     * Chiede all'utente il nome del centro,
     * @return nome del centro
     */
    private static String AskName() {
        boolean exit = true;
        String in = "";
        String name = null;
        do {
            // Request
            System.out.print("Inserire nome centro:\t\t\t");
            // Input
            in = InputScanner.INPUT_SCANNER.nextLine();
            // Check if it is correct
            if ((exit = fieldCorrect(in, IndexOf.name))) {
                // Check if there is another area wth the same name
                if ( f.exists() && Research.OneStringInCol(f, IndexOf.name, in) >= 0 ) {
                    // Output
                    System.out.println("Esiste già un centro con lo stesso nome.");
                    // Exit
                    name = null;
                    exit = true;
                } else {
                    // Assign input to name
                    name = in;
                    // Exit
                    exit = true;
                }
            } else {
                // Output
                System.out.println("Caratteri inseriti non validi.");
            }
        } while (!exit);
        // Return the name
        return name;
    }
    /**
     * Chiede all'utente di inserire l'indirizzo del centro.
     * @return indirizzo
     */
    private static String[] AskAddress() {
        // Address
        String[] address = new String[IndexOf.Iadd.length];
        // Exit
        boolean exit = true;
        // Input
        String in = null;
    
        //per l'indirizzo in input, appena preso l'input (prima di controllarlo), fare .toUpperCase(),
        //così i controlli sono corretti e quando lo si salva è già tutto maiuscolo

        do {
            // Ask Street
            do {
                // Request
                System.out.print("Inserire Via/Piazza:\t\t\t");
                // Input
                in = CommonMethods.toNoAccent( InputScanner.INPUT_SCANNER.nextLine() );
                if (exit = isAddElCorrect(in, IndexOf.Iadd.via)) {
                    address[IndexOf.Iadd.via] = in ;
                } else {
                    System.out.println("L'indirizzo inserito non è corretto.");
                }
            } while (!exit);
            // Ask Number
            do {
                // Request
                System.out.print("Inserire Numero Civico:\t\t\t");
                // Input
                in = CommonMethods.toNoAccent( InputScanner.INPUT_SCANNER.nextLine() );
                if (exit = isAddElCorrect(in, IndexOf.Iadd.civico)) {
                    address[IndexOf.Iadd.civico] = in;
                } else {
                    System.out.println("Il numero civico inserito non è corretto.");
                }
            } while (!exit);
            // Ask CAP
            do {
                // Request
                System.out.print("Inserire Codice di Avviamento Postale:\t");
                // Input
                in = CommonMethods.toNoAccent( InputScanner.INPUT_SCANNER.nextLine() );
                if (exit = isAddElCorrect(in, IndexOf.Iadd.CAP)) {
                    address[IndexOf.Iadd.CAP] = in;
                } else {
                    System.out.println("Il CAP inserito non è corretto.");
                }
            } while (!exit);
            // Ask City
            do {
                // Request
                System.out.print("Inserire Comune:\t\t\t");
                // Input
                in = CommonMethods.toNoAccent( InputScanner.INPUT_SCANNER.nextLine() );
                if (exit = isAddElCorrect(in, IndexOf.Iadd.comune)) {
                    address[IndexOf.Iadd.comune] = in;
                } else {
                    System.out.println("Il Comune inserito non è corretto.");
                }
            } while (!exit);
            //Ask Provice
            do {
                // Request
                System.out.print("Inserire Codice Provincia:\t\t");
                // Input
                in = CommonMethods.toNoAccent( InputScanner.INPUT_SCANNER.nextLine() );
                // Country Code must be made of 2 characters
                if ( ! AddElExist(in, IndexOf.Iadd.prov) ) {
                    // Error
                    System.out.println("Codice errato.\n");
                    // Stay in loop
                    exit = false;
                } else {
                    // If province does not exist
                    if ( listcomuni.exists() && !Research.isStringInCol(listcomuni, col_comuni.provincia, in) ) {
                        // Output
                        System.out.println("Non è stata trovata alcuna provincia col codice inserito.");
                        // Stay in loop
                        exit = false;
                    } else {
                        // Assign Provincia
                        address[IndexOf.Iadd.prov] = in;
                        // Exit
                        exit = true;
                    }
                }
            } while (!exit);
            // Check all address
            exit = isAddressCorrect(address);
            if ( ! exit ) {
                System.out.println("Non è stato trovato alcun CAP corrispondente al Comune e nella Provincia inserita.");
                System.out.println("Inserire nuovamente l'indirizzo.");
            }
        } while ( ! exit );

        return address;
    }
    /**
     * Associa le aree geografiche al centro.
     * @return id delle aree
     */
    private static String[] setAreeGeografiche(){

        ArrayList<String> aree = new ArrayList<String>();
        String[] out = null;
        final String endInput = "ESCI";
        boolean exit = false, already_in = false;
        String input = "";
        short contAree = 0;
        final short max_areas = 1000;

        System.out.println("\nINSERIMENTO AREE GEOGRAFICHE");
        if (!GeographicArea.doesCSVExist()) {
            System.out.println("\nNon ci sono aree disponibili da associare al centro.\n");
            return null;
        }
        do{
            System.out.println("\nAree inserite: " + contAree);
            System.out.print("Inserire il codice delle aree geografiche relative al centro.\nInserire " + endInput + " per confermare le aree inserite: ");

            input = InputScanner.INPUT_SCANNER.nextLine();

            // Check exit
            exit = CommonMethods.ExitLoop(input);
            // If area exist add it to the list
            if ( ! exit && fieldCorrect(input, IndexOf.areas) && GeographicArea.doesIDExist(input)) {
                already_in = false;
                // Check if area has already been inserted
                for (short i = 0; i < aree.size() && !already_in; i++) {
                    already_in = input.equals(aree.get(i));
                }
                // If the area is already in
                if ( already_in ) {
                    System.out.println("Area già inserita");
                } else {
                    aree.add(input);
                    contAree++;
                    // Safety exit
                    if (contAree >= max_areas) {
                        System.out.println("Numero massimo di aree raggiunto.");
                        exit = true;
                    }
                }
            }

        }while( ! exit );

        out = aree.toArray(new String[0]);

        System.out.println("Elenco Aree Inserite");
        System.out.println( GeographicArea.ListIDs(out) );

        return out;
    }
    /**
     * Controlla che la stringa inserita sia valida.
     * @param str stringa
     * @param index tipo di controllo
     * @return true se la stringa è corretta
     */
    private static boolean fieldCorrect(String str, short index) {
        // If str in null exit
        if (str == null || str.length() < 1) {
            // Return false
            return false;
        }
        // Search
        switch (index) {
            // Check Name
            case IndexOf.name:
                // Check if the name is valid
                return CommonMethods.isValidName(str);
            // Check Area
            case IndexOf.areas:
                // Check if area is valid
                return CommonMethods.isOnlyInt(str);
            default:
                // Error
                System.err.println("ERRORE: codice lista inesistente");
                return false;
        }
    }
    /**
     * Questo metodo controlla che esista un indirizzo con i campi inseriti.
     * Non viene controllata l'esistenza della via e del civico, ma solo la sua correttezza sintattica.
     * @param address indirizzo
     * @return true se l'indirizzo esiste
     */
    private static boolean isAddressCorrect( String[] address ) {
        // If str in null exit
        if (address == null || address.length != IndexOf.Iadd.length) {
            // Return false
            return false;
        }
        // For each element of the address
        for (short i = 0; i < IndexOf.Iadd.length; i++) {
            // Check if the element is correct
            if ( !isAddElCorrect(address[i], i) )
                // Element is incorrect return false
                return false;
        }
        // Check file existance
        if (! listcomuni.exists()) {
            return true;
        }
        // Array of strings
        String [] arr_str = Research.getRecordByTwoDatas(listcomuni, col_comuni.comune, address[IndexOf.Iadd.comune], col_comuni.provincia, address[IndexOf.Iadd.prov]);
        // If there is no city in that province return false
        if ( arr_str == null ) {
            return false;
        }
        // Check if CAP corrispond
        if ( ! equalsCAP( address[IndexOf.Iadd.CAP], arr_str[col_comuni.CAP] ) )
            return false;
        // Return true
        return true;
    }
    /**
     * Controlla la correttezza formale dell'elemento selezionato.
     * @param elem elemento
     * @param index indice
     * @return true se l'elemento è corretto
     */
    private static boolean isAddElCorrect( String elem, short index ) {
        // Check correctness
        switch (index) {
            // Street
            case IndexOf.Iadd.via:
                return CommonMethods.isASCIIValidAddress(elem);
            // Number
            case IndexOf.Iadd.civico:
                return CommonMethods.isASCIIValidAddress(elem);
            // CAP
            case IndexOf.Iadd.CAP:
                return (elem.length() == cap_length) && CommonMethods.isOnlyInt(elem);
            // City
            case IndexOf.Iadd.comune:
                return CommonMethods.isValidName(elem);
            // Province
            case IndexOf.Iadd.prov:
                return CommonMethods.isTwoLetters(elem);
            default:
                // Error
                System.err.println("ERRORE: indice elemento array indirizzo inesistente");
                return false;
        }
    }
    /**
     * Controlla l'esistenza dell'elemento selezionato.
     * @param elem elemento
     * @param index indice
     * @return true se l'elemento esiste
     */
    private static boolean AddElExist( String elem, short index ) {
        // First result
        boolean b = true;
        // Check element correctness
        b = isAddElCorrect(elem, index);
        // Check correctness
        switch (index) {
            // Street
            case IndexOf.Iadd.via:
            // Number
            case IndexOf.Iadd.civico:
                return b;
            // CAP
            case IndexOf.Iadd.CAP:
                return b && !(listcomuni.exists() && !Research.isStringInCol(listcomuni, col_comuni.CAP, elem));
            // City
            case IndexOf.Iadd.comune:
                return b && !(listcomuni.exists() && !Research.isStringInCol(listcomuni, col_comuni.comune, elem));
            // Province
            case IndexOf.Iadd.prov:
                return b && !(listcomuni.exists() && !Research.isStringInCol(listcomuni, col_comuni.provincia, elem));
            default:
                // Error
                System.err.println("ERRORE: indice elemento array indirizzo inesistente");
                return false;
        }
    }
    /**
     * Controlla che i due CAP corrispondano.
     * @param cap1 CAP 1
     * @param cap2 CAP 2
     * @return true se i CAP corrispondono
     */
    private static boolean equalsCAP( String cap1, String cap2 ) {
        // x character
        final char x = 'x';
        // CAP character
        char c1 = 0;
        char c2 = 0;
        // If CAP are already equas
        if (cap1.equals(cap2))
            return true;
        // For each character
        for (short i = 0; i < cap_length; i++) {
            // First CAP char
            c1 = cap1.charAt(i);
            // Second CAP char
            c2 = cap2.charAt(i);
            // If they are equals is OK
            if ( c1 != c2 ) {
                // If at least one of them is x is OK
                if ( c1 != x && c2 != x ) {
                    // Test not passed -> return false
                    return false;
                }
            }
        }
        // Test passed
        return true;
    }
    /** Nome Centro */
    private String nome = null;
    /** Indirizzo */
    private String [] indirizzo = new String[IndexOf.Iadd.length];
    /** Aree di interesse associate al centro */
    private String[] areeInteresse = null;
    /**
     * Costruttore dell'oggetto MonitoringCentre.
     * @param nome nome del centro
     * @param indirizzo indirizzo del centro
     * @param areeInteresse aree di interesse
     */
    public MonitoringCentre(String nome, String [] indirizzo, String[] areeInteresse){
        this.nome=nome;
        this.indirizzo=indirizzo;
        this.areeInteresse=areeInteresse;
    }
    /**
     * Ritorna il nome del centro.
     * @return nome
    */
    public String getNome(){
        return this.nome;
    }
    /**
     * Ritorna un array di stringhe contenente gli ID delle aree di interresse.
     * @return aree di interesse
     */
    public String[] getAreeInteresse() {
        return areeInteresse;
    }
    /**
     * Ritorna l'array di stringhe contenete l'indirizzo del centro di monitoraggio.
     * @return indirizzo del centro
     */
    public String[] getIndirizzo() {
        return indirizzo;
    }
    @Override
    public String toString() {
        String str = "";
        str += "Nome:\t" + this.nome + "\n";
        str += addresstoFormat() + "\n";
        str += "Aree di Interesse\n" + ListAreas();
        return str;
    }
    /**
     * Ritorna la lista delle aree di interesse del centro.
     * @return lista aree
     */
    public String ListAreas() {
        return GeographicArea.ListIDs(this.areeInteresse);
    }
    /**
     * Memorizza il centro nel file.
     * @return true se la memorizzazione ha successo
     */
    public boolean memorizzaCentro(){
        // Check existance
        if ( !Exist() ) {
            return false;
        }
        return CSV_Utilities.addArraytoCSV(f,toStringRecord(),header);
    }
    /**
     * Controlla l'esistenza dell'oggetto centro
     * @return true se l'oggetto esiste
     */
    public boolean Exist() {
        return (this.nome != null && this.nome.length() > 0);
    }
    /**
     * Trasforma tutti i campi della classe in un array di stringhe
     * @return array dei campi
     */
    private String[] toStringRecord() {
        // To be returned
        String[] record = new String[IndexOf.indexes + IndexOf.Iadd.length - 1];

        record[IndexOf.name] = this.nome;
        record[IndexOf.address + IndexOf.Iadd.via]    = this.indirizzo[IndexOf.Iadd.via];
        record[IndexOf.address + IndexOf.Iadd.civico] = this.indirizzo[IndexOf.Iadd.civico];
        record[IndexOf.address + IndexOf.Iadd.CAP]    = this.indirizzo[IndexOf.Iadd.CAP];
        record[IndexOf.address + IndexOf.Iadd.comune] = this.indirizzo[IndexOf.Iadd.comune];
        record[IndexOf.address + IndexOf.Iadd.prov]   = this.indirizzo[IndexOf.Iadd.prov];
        record[IndexOf.Iadd.length - 1 + IndexOf.areas] = areasforCSV();

        return record;
    }
    /**
     * Crea la cella delle aree per essere inserita nel file CSV.
     * @return cella
     */
    private String areasforCSV() {
        final String delimiter = "-";
        String str = "";
        short i = 0;
        // Check
        if (this.areeInteresse == null || this.areeInteresse.length <= 0) {
            return "";
        }
        for ( i = 0; i < this.areeInteresse.length; i++) {
            str += this.areeInteresse[i];
            if ( i < this.areeInteresse.length - 1) {
                str += delimiter;
            }
        }
        return str;
    }
    /**
     * Ritorna l'indirisso formattato secondo lo standard di Poste Italiane.
     * @return indirizzo
     */
    private String addresstoFormat() {
        String str = "";
        str += this.indirizzo[IndexOf.Iadd.via] + " " + this.indirizzo[IndexOf.Iadd.civico] + "\n";
        str += this.indirizzo[IndexOf.Iadd.CAP] + " " + this.indirizzo[IndexOf.Iadd.comune] + " " + this.indirizzo[IndexOf.Iadd.prov];
        return str;
    }
}
