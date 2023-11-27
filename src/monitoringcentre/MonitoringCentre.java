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
import src.common.CommonMethods;
import src.common.InputScanner;
import src.common.Research;

/**
 * Classe che contiene il centro di monitoraggio.
 * @author Riccardo Galimberti
 * @version 0.09.1
 */
public class MonitoringCentre {
    // private String via, civico, cap, comune, provincia;
    private String nome;
    private String [] indirizzo = new String[IndexOf.Iadd.length];
    private ArrayList<String> areeInteresse=new ArrayList<String>();
    private final static String header = "nome, via/piazza, numero civico, cap, comune, provincia, aree";

    private final static File f = FileSystems.getDefault().getPath("data", "CentroMonitoraggio.dati.csv").toFile();
    // Cities List
    private final static File listcomuni = FileSystems.getDefault().getPath("data", "comuni-localita-cap-italia.csv").toFile();
    private final static class col_comuni {
        private final static short comune = 0;
        private final static short CAP = 2;
        private final static short provincia = 1;        
    }
    // CAP length
    private final static short cap_length = 5;
    // Indexes in CSV file
    private final static class IndexOf {
        private final static short name = 0;
        private final static short address = 1;
        private final static short areas = 2;
        private final static class Iadd {
            private final static short via = 0;
            private final static short civico = 1;
            private final static short CAP = 2;
            private final static short comune = 3;
            private final static short prov = 4;
            // Length of address array
            private final static short length = 5;
        }
        // Number of indexes
        private final static short indexes = 3;
        // Max index value
        private final static short max_index = 2;
    }

    //TODO rimuovere
    /*
     * A cosa serve un costruttore che non inizializza i campi?
     * A cosa servono i campi se tanto il costruttore non li inizializza?
     */
    public MonitoringCentre(String nome, String [] indirizzo, String [] areeInteresse){
        if(CenterExistence(nome))
            registraCentroAree(nome, indirizzo, areeInteresse);
    }


    //TODO
    //javadoc
    public MonitoringCentre(String nome, String [] indirizzo, ArrayList<String> areeInteresse){
        this.nome=nome;
        this.indirizzo=indirizzo;
        this.areeInteresse=areeInteresse;
    }

    //costruttore vuoto
    public MonitoringCentre(){
    }

    //TODO rimuovere il commento
    //serve ad autorized operator per ottenere il nome del centro
    //TODO javadoc
    public String getNome(){
        return this.nome;
    }

    //TODO rimuovere
    /*
     * Perché devo inserire i campi che dovrebbero essere assegnati nel costruttore?
     */
    public void registraCentroAree(String nome, String [] indirizzo, String [] areeInteresse){
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
            // TODO Fix error; areeInteresse have a fixed number, why use a List?
            //this.areeInteresse = areeInteresse;
        }else{
            System.err.println("Errore: Lista vuota."); 
        }
        //memorizzaCentroAree(nome, indirizzo, areeInteresse, userid);
    }

    /**
     * Ritorna un array di stringhe dei nomi dei Centri di Monitoraggio.
     * Se non ci sono Centri ritorna null.
     * @return nomi dei centri
     */
    public static String[] getCentri(){
        return Research.getColArray(f,IndexOf.name);
    }

    private static void memorizzaCentroAree(String nome, String [] indirizzo, ArrayList<String> areeInteresse){
        ArrayList<String> str = new ArrayList<String>();
        String aree = "";
        str.add(nome);
        short i = 0;
        for ( i = 0; i < indirizzo.length; i++) {
            str.add(indirizzo[i]);
        }
        
        for ( i = 0; i < areeInteresse.size() - 1; i++) {
            aree += areeInteresse.get(i) + "-" ;
        }
        aree += areeInteresse.get(i);
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
    //TODO rimuovere il TODO sotto se va bene
    // TODO Modificare per aggiungere il centro metodo prototipale: totalmente incompleto e non testato
    /**
     * Permette di creare un Centro di Monitoraggio inserendone i dati e la ritorna.
     * Se la creazione fallisce ritorna null.
     * @return Centro di Monitoraggio creato
     */
    public static MonitoringCentre createCentre() {
        // Error string
        final String error = "Creazione del centro di monitoraggio terminata: creazione fallita";
        //TODO rimuovere
        // Monitoring Centre to be returned
        MonitoringCentre mc = null;        
        // Input String
        String in = "";
        String nome="";
        String [] indirizzo=new String [IndexOf.Iadd.length];
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
                if ((exit = fieldCorrect(in, IndexOf.name))) {
                    // Check if there is another area wth the same name
                    if ( f.exists() && Research.OneStringInCol(f, IndexOf.name, in) >= 0 ) {
                        // Output
                        System.out.println("Esiste già un centro con lo stesso nome.");
                        System.out.println(error);
                        // Exit
                        return null;
                    } else {
                        // Assign input to name
                        nome = in;
                    }
                }
            } while (!exit);



            //TODO AGGIUNGERE CONTROLLO SU TUTTO L'INDIRIZZO
            //es di indirizzo
            //VIALE ROMAGNA 12/A
            //20133 MILANO MI
            //tutto maiuscolo, niente punteggiatura


            //per l'indirizzo in input, appena preso l'input (prima di controllarlo), fare .toUpperCase(),
            //così i controlli sono corretti e quando lo si salva è già tutto maiuscolo

            // TODO Inserire controllo
            // Request
            System.out.print("Inserire via:\t\t");
            // Input
            indirizzo[IndexOf.Iadd.via] = InputScanner.INPUT_SCANNER.nextLine();
           
            // TODO Inserire controllo
            System.out.print("Inserire numero civico:\t\t");
            // Input
            indirizzo[IndexOf.Iadd.civico]=Integer.toString(InputScanner.INPUT_SCANNER.nextInt());

            // TODO Inserire controllo
            //controllare che sia lungo 5 e che abbia solo numeri
            System.out.println("Inserire il cap: ");
            indirizzo[IndexOf.Iadd.CAP]=InputScanner.INPUT_SCANNER.nextLine();

            // TODO Inserire controllo
            System.out.println("Inserire il nome del comune: ");
            indirizzo[IndexOf.Iadd.comune]=InputScanner.INPUT_SCANNER.nextLine();

            do {
                // Request
                System.out.print("Inserire codice provincia:\t");
                // TODO: usare il codice provincia per ottenere regione (se necessario) e nazione (anche se è per forza Italia).
                // Input
                in = InputScanner.INPUT_SCANNER.nextLine();
                // Country Code must be made of 2 characters
                // TODO
                if ( ! AddElExist(in, IndexOf.Iadd.prov) ) {
                    // Stay in loop
                    exit = false;
                } else {
                    // To upper case
                    in = in.toUpperCase();
                    // TODO Not sure it works now
                    // Record array
                    String [] cc_array = Research.getRecordByData(listcomuni, 2, in);
                    // If Country code does not exist
                    if (cc_array == null ) {
                        // Output
                        System.out.println("Non è stata trovata alcuna provincia col codice inserito.");
                        // Stay in loop
                        exit = false;
                    } else {
                        // Assign Provincia
                        indirizzo[IndexOf.Iadd.prov]=cc_array[3];
                        // Exit
                        exit = true;
                    }
                }
            } while (!exit);
        } catch (Exception e) {
            // Output Exception
            e.printStackTrace();
        }
        // TODO
        // Return Geographic Area
        ArrayList<String> aree=new ArrayList<String>();
        aree=null;
 
        memorizzaCentroAree(nome, indirizzo, aree);
        //return Monitoring Centre
        return new MonitoringCentre(nome, indirizzo, aree);        
    }
    /*
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
                // TODO
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
    /*
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
                return b && Research.isStringInCol(listcomuni, col_comuni.CAP, elem);
            // City
            case IndexOf.Iadd.comune:
                return b && Research.isStringInCol(listcomuni, col_comuni.comune, elem);
            // Province
            case IndexOf.Iadd.prov:
                return b && Research.isStringInCol(listcomuni, col_comuni.provincia, elem);
            default:
                // Error
                System.err.println("ERRORE: indice elemento array indirizzo inesistente");
                return false;
        }
    }
    // Check if two CAPs correpond to each other
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
    // TODO Remove test main
    public static void main(String[] args) {

        String test = "Cantù";
        System.out.println(CommonMethods.toNoAccent(test));


        /*
        String [] fake_address = { "Via Regina Teodolinda" ,"37/h", "20100", "MILANO", "MI" };
        System.out.println();
        if (isAddressCorrect(fake_address)) {
            System.out.println("L'indirizzo è corretto.");
        } else {
            System.out.println("L'indirizzo NON è corretto.");
        }



        /*
        String nome = "Centro Prova";
        String [] indirizzo = { "Via Regina Teodolinda" ,"37", "Como", "CO", "Italia" };
        String [] areeInteresse = {"123456", "1234567", "123456" };
        short userid = 00002;
        */
        /*
         * Perché devo inserire 2 volte le stesse cose?
         */
        //MonitoringCentre m = new MonitoringCentre(nome, indirizzo, areeInteresse, userid);
        /*
         * A cosa serve registraAree pubblico se tanto lo fa già il costruttore?
         */
        //m.registraCentroAree(nome, indirizzo, areeInteresse, userid);
        
    }
}
