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

import src.common.CSV_Utilities;
import src.common.CommonMethods;
import src.common.InputScanner;
import src.common.Research;
import src.geographicarea.GeographicArea;
import src.monitoringcentre.MonitoringCentre;
import src.parameters.Parameters;

/**
 * Un oggetto della classe <code>User</code> rappresenta un utente.
 * Ciò che l'utente può fare è descritto nei metodi che gli appartengono.
 * @author Giacomo Paredi
 * @author Lorenzo Radice
 * @version 0.20.4
 */
public class User {
    // File header
    private final static String header = "Matricola,Nome,Cognome,Codice Fiscale,Indirizzo Email,Password,Centro di Monitoraggio";
    // Indexes in CSV file
    protected final static class IndexOf {
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
    // Make the path platform independent
    protected final static File file = FileSystems.getDefault().getPath("data", "OperatoriRegistrati.dati.csv").toFile();
    //dafault value for attribute centre if user does not choose a centre during registration
    protected final static String defaultValueOfCentre="";
    /**
     * Crea un utente
     */
    public User() {
        //TODO
    }
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
     * @param id id dell'area
     */
    public void visualizzaAreaGeografica(){
        boolean exit=false;
        String id;
        String aree[]=Parameters.getIDAree();
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
            
        }else{
            System.out.println("Nessuna area disponibile");
        }
    }
    // TODO JavaDoc
    public void login_privilegies() {
        AutorizedOperator a = AutorizedOperator.autenticazione();
        if (a != null) {
            a.menu();
        }
    }
    /**
     * Permette all'utente di registrarsi come Operatore Autorizzato
     * I dati del nuovo operatore vengono salvati sul file OperatoriRegistrati.dati.csv
     */
    public static void registrazione(){
        String [] nomi_campi=header.split(",");
        //swtich password with centre
        //centre comes after password in the file
        //but is asked first during registration
        String temp=nomi_campi[IndexOf.password];
        nomi_campi[IndexOf.password]=nomi_campi[IndexOf.centro];
        nomi_campi[IndexOf.centro]=temp;
        // Max number of operators
        final int max_operators = 99999;

        String [] campi=new String[IndexOf.indexes];
        String campo;

        try{
            if(file.exists() && Files.lines(file.toPath()).count() > (max_operators + 1)){
                System.err.println("Numero massimo di operatori raggiunto.\nNon è possibile effettuare la registrazione");
            }else{
                System.out.println("Benvenuto nel form per la registrazione!\nPrego, inserisca le informazioni richieste\n");

                for(int i=1;i<IndexOf.indexes;i++){
                    System.out.print(nomi_campi[i] + ": ");
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
        }catch ( Exception e ) {
            // Print Error
            e.printStackTrace();            
        }

        campi[0]=setUserId();
        CSV_Utilities.addArraytoCSV(file, campi, header);
        System.out.println("\nRegistrazione completata!\nPer accedere usare il seguente User-ID: " + campi[0] + " e la password scelta");
    }
    //check if a field is correct
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

    //set the userid
    private static String setUserId(){

        long id=0;
        if(!file.exists()){
            id=1;
        }else{
            try {
                id=(Files.lines(file.toPath()).count());
                //id++;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } 
        }
        return String.format("%05d", id);
    }
    // Check Codice Fiscale
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
    
    // Check email
    private static boolean ControlloEmail(String email){
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regexPattern).matcher(email).matches();
    }


    //TODO Rendere modulare
    //show a menù with different way of associate the centre to the operator
    private static String setCentro(){

        final String menu="\n\nMenù associazione centro\n"+
                            "1) Associazione ad un centro esistente\n"+
                            "2) Associazione ad un centro nuovo\n"+
                            "3) Associazione in un secondo momento\n"; 
        
        boolean exit=true;
        int scelta;
        String centre="";

        do{
            scelta=0;
            System.out.print(menu);

            System.out.print("\nInserire codice: ");

            try{
                scelta=InputScanner.INPUT_SCANNER.nextInt();
            }catch(InputMismatchException e){
                //consume invalid token
                InputScanner.INPUT_SCANNER.next();
                System.out.println("ERRORE");
                System.out.println("Codice inserito errato!");
                scelta=0;
            }

            switch (scelta) {

                //user choose an existing centre
                case 1:
                    centre=associaCentro();
                    exit=false;
                    break;
                
                //user create a new centre
                case 2:
                    //TODO testare funzionamento
                    centre=registraCentroAree();
                    exit=false;
                    break;
                
                //user does not choose a centre
                case 3:
                    //consume invalid token
                    InputScanner.INPUT_SCANNER.nextLine();
                    centre=defaultValueOfCentre;
                    exit=false;
                    break;
            
                default:
                    System.out.println("Codice inserito errato!");
                    exit=true;
                    break;
            }
        }while(exit);

        return centre;
    }
    //TODO TESTARE FUNZIONAMENTO, SOPRATUTTO GESTIRE IL NULL DI RITORNO
    //user create a new centre
    private static String registraCentroAree(){

        MonitoringCentre centre=MonitoringCentre.createCentre();
        if(centre!=null){
            return centre.getNome();
        }else{
            return null;
        }
        
    }
    //user choose a centre from the existing ones
    protected static String associaCentro(){

        String [] centri;
        String nome="";

        //show centres to user
        centri=MonitoringCentre.getCentri();
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
}
