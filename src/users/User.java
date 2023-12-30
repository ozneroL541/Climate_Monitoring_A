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

package src.users;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.InputMismatchException;
import java.util.regex.Pattern;

import src.common.*;
import src.geographicarea.GeographicArea;
import src.monitoringcentre.MonitoringCentre;
import src.parameters.Parameters;

/**
 * Un oggetto della classe <code>User</code> rappresenta un utente.
 * Ciò che l'utente può fare è descritto nei metodi che gli appartengono.
 * @author Giacomo Paredi
 * @version 0.22.1
 */
public class User {
    /**
     * Indici dei file CSV
     */
    protected final static record IndexOf() {
        final static short matricola=0;
        final static short nome=1;
        final static short cognome=2;
        final static short codice_fiscale=3;
        final static short email=4;
        final static short password=5;
        final static short centro=6;
        //number of indexes
        final static short indexes = 7;
    }
    // File header
    private final static String header = "Matricola,Nome,Cognome,Codice Fiscale,Indirizzo Email,Password,Centro di Monitoraggio";
    /**
     * File degli Operatori Registrati.
     */
    protected final static File file = FileSystems.getDefault().getPath("data", "OperatoriRegistrati.dati.csv").toFile();
    /**
     * Valore di default del centro.
     */
    protected final static String defaultValueOfCentre="";
    /**
     * Permette all'utente di registrarsi come Operatore Autorizzato.
     * I dati del nuovo operatore vengono salvati sul file OperatoriRegistrati.dati.csv
     */
    public static void registrazione(){
        // Max number of operators
        final int max_operators = 99999;
        String [] campi=new String[IndexOf.indexes];
        String campo;
        String [] nomi_campi=header.split(",");
        //swtich password with centre
        //centre comes after password in the file
        //but is asked first during registration
        String temp=nomi_campi[IndexOf.password];
        nomi_campi[IndexOf.password]=nomi_campi[IndexOf.centro];
        nomi_campi[IndexOf.centro]=temp;

        try{
            if(file.exists() && Files.lines(file.toPath()).count() > (max_operators + 1)){
                System.err.println("Numero massimo di operatori raggiunto.\nNon è possibile effettuare la registrazione");
            }else{
                System.out.println("Benvenuto nel form per la registrazione!\nPrego, inserisca le informazioni richieste\n");

                for(int i=1;i<IndexOf.indexes;i++){
                    if (IndexOf.password != i) {
                        System.out.print(nomi_campi[i] + ": ");
                    }
                    do{
                        campo=campoValido(i);
                    }while(campo==null);
                    campi[i]=campo;
                }
                //swtich password with centre
                //centre comes after password in the file
                //but is asked first during registration
                temp=campi[IndexOf.password];
                campi[IndexOf.password]=campi[IndexOf.centro];
                campi[IndexOf.centro]=temp;
            }
        }catch ( IOException e ){
            // Print Error
            e.printStackTrace();
            campi[IndexOf.matricola] = null;
        }catch ( Exception e ) {
            // Print Error
            e.printStackTrace();  
            campi[IndexOf.matricola] = null;          
        }

        campi[IndexOf.matricola]=setUserId();

        if(campi[IndexOf.matricola]!=null){
            CSV_Utilities.addArraytoCSV(file, campi, header);
            System.out.println("\nRegistrazione completata!\nPer accedere usare il seguente User-ID: " + campi[0] + " e la password scelta");       
        }else{
            System.err.println("Errore durante la registrazione");
        }

    }
    /**
     * Permette all'utente di associarsi ad un centro.
     * @return id del centro
     */
    protected static String associaCentro(){

        String [] centri;
        String nome="";

        //show centres to user
        centri=MonitoringCentre.getCentri();
        // Check
        if (centri == null) {
            System.out.println("Attualmente non sono presenti Centri di Monitoraggio.");
            System.out.println("Crea un centro di Monitoraggio per effetturare l'associazione");
            return defaultValueOfCentre;
        }
        System.out.println("Centri esistenti:");
        for(int i=0;i<centri.length;i++){
            System.out.println(centri[i]);
        }

        //user choose centre
        System.out.print("\nScegliere il centro inserendone il nome: ");
        do{
            nome=InputScanner.INPUT_SCANNER.nextLine();
            if(!MonitoringCentre.CenterExistence(nome)){
                System.out.print("Nome inserito inesistente\nInserire un nome valido: ");
            }
        }while(!MonitoringCentre.CenterExistence(nome));

        return nome;
    }
    /*
     * Controlla che il campo sia valido
     * @param indice_campo campo
     * @return stringa risultato
     */
    private static String campoValido(int indice_campo){
        String campo;
        try {
            switch(indice_campo){

            //insert name
            case 1:
                campo=InputScanner.INPUT_SCANNER.nextLine();
                //check if name contains only letters
                if(!CommonMethods.isValidName(campo)){
                    System.out.print("Nome non valido.\nReinserire: ");
                    return null;
                }else{
                    return campo;
                }

            //insert last name                
            case 2:
                campo=InputScanner.INPUT_SCANNER.nextLine();
                //check if last name contains only letters
                if(!CommonMethods.isValidName(campo)){
                    System.out.print("Cognome non valido.\nReinserire: ");
                    return null;
                }else{
                    return campo;
                }

            //insert codice fiscale                
            case 3:
                campo=InputScanner.INPUT_SCANNER.nextLine();
                //upper case codice fiscale
                campo=campo.toUpperCase();
                //check if fiscal code is correct
                if(!ControlloCodiceFiscale(campo)){
                    System.out.print("Codice fiscale non valido.\nReinserire: ");
                    return null;
                }else if( file.exists() && Research.isStringInCol(file, IndexOf.codice_fiscale, campo)){ //check if fiscal code is unique in the file
                    System.out.print("Codice fiscale già utilizzato.\nReinserire: ");
                    return null;
                } else {
                    return campo;
                }
                
            //insert email                
            case 4:
                campo=InputScanner.INPUT_SCANNER.nextLine();
                //check if email is correct
                if(!ControlloEmail(campo)){
                    System.out.print("Indirizzo non valido.\nReinserire: ");
                    return null;
                }else if( file.exists() && Research.isStringInCol(file, IndexOf.email, campo)){ //check if email is unique in the file
                    System.out.print("Indirizzo già utilizzato.\nReinserire: "); 
                    return null;
                } else {
                    return campo;
                }

            //insert centre             
            case 5:
                campo=setCentro();
                return campo;
             
            //insert password               
            case 6:                
                return campo=InputScanner.INPUT_SCANNER.nextLine();
            default:
                return null;
            }  
        } catch (InputMismatchException e) {
            System.err.print("Errore nell'inserimento dei dati.\nReinserire: ");
            return null;
        }
    }
    /*
     * Seleziona il nome utente
     * @return nome utente
     */
    private static String setUserId(){

        long id=0;
        if(!file.exists()){
            id=1;
        }else{
            try {
                id=(Files.lines(file.toPath()).count());
                //id++;
            } catch (IOException e) {
                System.err.print("Errore nella lettura del file");
                return null;
            } 
        }
        return String.format("%05d", id);
    }
    /*
     * Controlla la correttezza del codice fiscale
     * @param cf codice fiscale
     * @return true se il codice è corretto
     */
    private static boolean ControlloCodiceFiscale( String cf ) {
        // Check if ASCII
        if ( ! Charset.forName("US-ASCII").newEncoder().canEncode(cf)) {
            // The Fiscal Code is not ASCII
            return false;
        }
        // Output declaration
        boolean check = true;
        // The length of Codice Fiscale must be 16 characters for fisical people
        if ( cf.length() != 16 ) {
            check = false;
        } else {
            // Index of the character which could not be letters
            short [] int_index = { 6, 7, 9, 10, 12, 13, 14 } ;
            // Index for the previos array
            short j = 0;
            // For every character of the array checkif the letters are in the correct position
            for (short i = 0; check && i < cf.length(); i++) {
                // If it could not be a letter increade the j index and go ahead
                if ( j < int_index.length && i == int_index[j]) {
                    j++;
                } else {
                    // If the current character is not a letter the string is not incorrect
                    if ( ! Character.isLetter(cf.charAt(i)) ) {
                        check = false;
                    }
                }
            }
            // If the string can still be true continue with the verification else terminate the execution fo the function
            if (check) {
                // Characters of the months
                final char[] m = { 'A', 'B', 'C', 'D', 'E', 'H', 'L', 'M', 'P', 'R', 'S', 'T' };
                // This check if the month character is correct
                boolean month_chek = false;
                // Check for every character of the months
                for (char c : m) {
                    // If one of the months characters corrispond to the month charater in the string than true
                    if ( c == Character.toUpperCase(cf.charAt(8)) ) {
                        month_chek = true;
                    }
                }
                // If the check of the month is correct continue
                // If the first birthday character is a number continue else true
                if ( check = month_chek && Character.isDigit(cf.charAt(9))) {
                    // First  birthday character
                    char c1 = cf.charAt(9);
                    // Second birthday character 
                    char c2 = cf.charAt(10);
                    // If the first birthday digit is between 0 and 7 it is correct
                    if ( ! ( ( c1 >= '0' && c1 < '3' ) || ( c1 >= '4' && c1 < '7' ) ) ) {
                        // If the first birthday digit is 3 or 7 is ok
                        if ( ! ( c1 == '3' || c1 == '7' ) ) {
                            check = false;
                        // Now the first digit must be 3 or 7, check if the second is a digit
                        // If true than it must be 0 or 1 to be correct
                        } else if ( Character.isDigit(c2) && ( ! ( c2 == '0' || c2 == '1' ) ) ) {
                            check = false;
                        }
                    }
                }
            }
        }
        return check;
    }
    /*
     * Controlla la correttezza dell'indirizzo e-mail.
     * @param email indirizzo e-mail
     * @return true se l'indirizzo è corretto
     */
    private static boolean ControlloEmail(String email){
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regexPattern).matcher(email).matches();
    }
    /*
     * Mostra un menù con differenti modalità per associare l'operatore al centro.
     * @return id centro
     */
    private static String setCentro(){
        
        String input="";
        Short choice=0;
        String centre="";
        MenuCentre mc = new MenuCentre();
        do{
            System.out.print(mc.getMenu());

            System.out.print("Inserire codice: ");

            try {
                input = InputScanner.INPUT_SCANNER.nextLine();
                choice = (short) Short.valueOf(input);
            } catch (NumberFormatException e) {
                choice=0;
            } catch (Exception e) {
                choice=0;
            }
            centre=centreChoice(choice);            
        }while(centre==null);

        return centre;
    }
    /**
     * Associa l'operatore al centro
     * @param choice scelta
     * @return id del centro
     */
    private static String centreChoice(short choice){
        String centre;
        switch (choice) {
            //user choose an existing centre
            case MenuCentre.IndexOf.existingCentre:
                if (MonitoringCentre.FileExist()) {
                    centre=associaCentro();
                } else {
                    System.out.println("Attualmente non sono presenti centri.");
                    centre = defaultValueOfCentre;
                }
                return centre;
            //user create a new centre
            case MenuCentre.IndexOf.newCentre:
                centre=MonitoringCentre.insertCentre();
                if(centre!=null){
                    return centre;
                }else{
                    System.err.println("Errore durante la creazione del nuovo centro");
                    return null;
                }
                
            //user does not choose a centre
            case MenuCentre.IndexOf.doNothing:
                centre=defaultValueOfCentre;
                return centre;
        
            default:
                System.out.println("Codice inserito errato.\n");
                return null;
        }
    }
    /**
     * Crea un utente
     */
    public User() {}
    /**
     * Permette all'utente di effettuare la ricerca di aree geografiche.
     * @author Lorenzo Radice
     */
    public void cercaAreaGeografica() {
        // Loop exit variable
        boolean exit = true;
        // Input integer
        int in = -1;
        // Check file existence
        if ( GeographicArea.doesCSVExist() ) {
            // While exit is false
            do {
                // Input
                try {
                    // Print indexes menu
                    GeographicArea.printIndexesMenu();
                    // Output
                    System.out.println("Seleziona il tipo di parametro con cui effettuare la ricerca.");
                    // Output for input
                    System.out.print  ("Inserire il codice: ");
                    // Input integer
                    in = InputScanner.INPUT_SCANNER.nextInt();
                    // Collect trash
                    InputScanner.INPUT_SCANNER.nextLine();
                    // If the chosen integer exist 
                    if (GeographicArea.IndexExist(in)) {
                        // Research Argument
                        String arg = "";
                        // Output
                        System.out.print("Inserire il parametro per la ricerca: ");
                        do {
                            // Input string
                            arg = InputScanner.INPUT_SCANNER.nextLine();
                            // If the argument is correct
                            if (GeographicArea.argumentCorrect(arg, in)) {
                                // New Line
                                System.out.println();
                                // Search
                                GeographicArea.SearchList(in, arg, 0);
                                // Exit true
                                exit = true;
                            } else {
                                // Output
                                System.out.print("Reinserire il parametro: ");
                                // Not Exit
                                exit = false;
                            }
                        } while (!exit);
                    } else {
                        // Error output
                        System.out.println("\nL'indice selezionato non è disponibile.");
                        System.out.println();
                        // Not exit
                        exit = false;
                    }
                } catch ( InputMismatchException e) {
                    // Reset input scanner
                    InputScanner.INPUT_SCANNER.nextLine();
                    // Error Output
                    System.err.println("\nInserimento non valido.\nInserire uno dei numeri mostrati per selezionare un'opzione.");
                    // New line
                    System.out.println();
                    // Not exit
                    exit = false;
                } catch (Exception e) {
                    // Output Exception
                    e.printStackTrace();
                    // Exit 
                    exit = true;
                }
            } while (!exit);
        } else {
            System.out.println("\nNon è presente alcuna area di interesse per cui effettuare la ricerca.");
        }
    }
    /**
     * Permette all'utente di visualizzare le informazioni associate ad una area geografica.
     */
    public void visualizzaAreaGeografica(){
        boolean exit=false;
        String id;
        String[] aree = null;
        if ( Parameters.FileExist() ) {
            aree = Parameters.getIDAree();
            if(aree!=null){
                // Print Areas
                System.out.println("Aree disponibili:");
                System.out.println(GeographicArea.ListIDs(aree));
                // Ask
                System.out.print("Inserire l'id dell'area per visualizzarne le informazioni: ");

                do{
                    id=InputScanner.INPUT_SCANNER.nextLine();
                    // Set Exit
                    exit = false;
                    //check if user input is a valid id
                    for(String value: aree){
                        // Area is in options
                        if(value.equals(id)){
                            exit=true;
                        }
                    }
                    if (!exit) {
                        System.out.print("Geoname ID non valido\nInserire un Geoname ID tra quelli disponibili: ");
                    }
                }while(!exit);

                Parameters.MostraParametri(id);
                
            } else {
                System.out.println("Nessuna area disponibile.");
            }
        } else {
            System.out.println("Non è presente alcun parametro attualmente.");
        }
    }
}
