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

import src.monitoringcentre.MonitoringCentre;

/**
 * Un oggetto della classe <code>AutorizedOperator</code> rappresenta
 * un utente con privilegi speciali.
 * Ciò che l'operatore autorizzato può fare &egrave descritto nei metodi che gli appartengono.
 * @author Giacomo Paredi
 * @version 0.13.4
 */
public class AutorizedOperator extends User {
    // User Identity Code
    private short userid;
    // Name
    private String nome;
    // Surname
    private String cognome;
    // Fiscal Code
    private String codice_fiscale;
    // e-mail address
    private String email_address;
    // User Password
    private String passwd;
    // Monitoring Centre
    private String centre;
    //private short centre;

    // Make the path platform independent
    private final static File file = FileSystems.getDefault().getPath("data", "OperatoriRegistrati.dati.csv").toFile();

    //dafault value for attribute centre if user does not choose a centre during registration
    private final static String defaultValueOfCentre="";

    //file header
    private final static String header = "Matricola,Nome,Cognome,Codice Fiscale,Indirizzo Email,Password,Centro di Monitoraggio";

    //indexes in CSV file
    private final static class IndexOf {
        private final static short matricola=0;
        private final static short nome=1;
        private final static short cognome=2;
        private final static short codice_fiscale=3;
        private final static short email=4;
        private final static short password=5;
        private final static short centro=6;
        //number of indexes
        private final static short indexes = 7;
    }

    /**
    * Costruttore vuoto
    */
    public AutorizedOperator() {}

    /**
     * Costruttore di <code>AutorizedOperator</code>
     * Costruisce l'oggetto AutorizedOperator dati i valori passati come parametri
     * @param id id univoco dell'operatore
     * @param nome nome dell'operatore
     * @param cognome cognome dell'operatore
     * @param cod_fis codice fiscale dell'operatore
     * @param email_add indirizzo email dell'operatore
     * @param password password scelta dall'operatore
     * @param centre centro al quale l'operatore appartiene. Se non appartiene a nessun centro assume un valore di default 
     */
    public AutorizedOperator(short id, String nome, String cognome, String cod_fis, String email_add, String password, String centre){
        this.userid=id;
        this.nome=nome;
        this.cognome=cognome;
        this.codice_fiscale=cod_fis;
        this.email_address=email_add;
        this.passwd=password;
        this.centre=centre;
    }

    @Override
    public String toString(){
        String str = "";
        str += "User ID: " + String.format("%05d", this.userid) + "\n";
        str += "Nome: "   + this.nome + "\n";
        str += "Cognome: "       + this.cognome + "\n";
        str += "Codice Fiscale: "   + this.codice_fiscale + "\n";
        str += "Indirizzo Email: " + this.email_address + "\n";
        str += "Password: "     + this.passwd + "\n" ;
        if(this.centre==defaultValueOfCentre){
            /* TODO se metti
             * final static String nocentre = "NESSUNO"
             * in caso di modifica o ripetizione in altri
             * possibili output è più funzionale. 
             * 
             * Comunque non crei una nuova costante perché 
             * le stringhe scritte così le salva già come costante.
            */
            str += "Id Centro di appartenenza: NESSUNO";
        }else{
            str += "Id Centro di appartenenza: "    + this.centre;
        }
        return str;
    }

    /**
     * Ritorna DefaultValueOfCentre come Short
     * @return DefaultValueOfCentre
     */
    public static String getDefaultValueOfCentre(){
        return defaultValueOfCentre;
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
        // TODO Cosa vogliono dire questi numero? Scarsa leggibilità
        String temp=nomi_campi[5];
        nomi_campi[5]=nomi_campi[6];
        nomi_campi[6]=temp;
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
                temp=campi[5];
                campi[5]=campi[6];
                campi[6]=temp;
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

    //TODO
    //fare javadoc
    //return an AutorizedOperator object if userid and password are guessed under 3 attemps
    public static AutorizedOperator autenticazione(){
        // Attempt limit
        final int limit = 3;
        //counter
        int c=0;
        //AutorizedOperator object
        AutorizedOperator u=null;

        // If file doesn't exist exit
        if ( ! file.exists() ){
            // Error Output
            System.err.println("ERRORE: il file " + file.getName() + " non si trova nella cartella \'" + file.getParent() + "\'.\n" );
            // Error return
            return null;
        }

        u=login();
        while(u==null && c<limit){
            System.out.println("User-ID e password non riconosciuti (tentativi rimasti: " + (limit-c) + ").\nReinserire");
            u=login();
            c++;
        }
        if ( c >= limit ) {
            // Exit
            return null;
        }
        return u;
    }

    //evaluate userid and password
    private static AutorizedOperator login(){
        String userid;
        String password;
        System.out.println("LOGIN\n");

        try{
            System.out.print("Inserire l'User-ID: ");
            userid = InputScanner.INPUT_SCANNER.nextLine();
            System.out.print("Inserire la password: ");
            password=InputScanner.INPUT_SCANNER.nextLine();
        }catch(InputMismatchException e){
            e.printStackTrace();
            return null;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        
        //if userid exist
        if(Research.isStringInCol(file, IndexOf.matricola, userid)){
            //return the column where UserId is
            int riga=Research.OneStringInCol(file, IndexOf.matricola, userid);
            // Initialize record
            String[] record = null;
            // Check if the research returned a valid result
            if(riga > 0){
                record = Research.getRecord(file, riga);
            }
            if(record!=null){
                //if password match, set the object's attributes
                if(record[IndexOf.password].equals(password)){
                    return new AutorizedOperator(Short.valueOf(userid), record[IndexOf.nome], record[IndexOf.cognome], record[IndexOf.codice_fiscale], record[IndexOf.email], password, record[IndexOf.centro]);
                }else{
                    return null;
                }
            }
        }else{
            return null;
        }

        //TODO
        //senza mi da errore, perchè boh
        return null;
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
    
    //TODO terminare metodo
    public void menu(){

        final String menu="\n\nMenù Operatore Autorizzato\n"+
                            "1) Cerca area geografica\n"+
                            "2) Visualizza area geografica\n"+
                            "3) Aggiunta parametri climatici\n"+
                            "4) Selezione centro di appartenenza\n"+
                            "5) Creazione nuovo centro di monitoraggio\n"+
                            "6) Logout\n";    
        
        boolean exit=true;
        int scelta;

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

                //search geographic area
                case 1:
                    cercaAreaGeografica();
                    exit=true;
                    break;
                
                //view geographic area
                case 2:
                    visualizzaAreaGeografica();
                    exit=true;
                    break;
                
                //add climate parameters
                case 3:
                    //TODO aggiungere metodo
                    //aggiungi parametri ad una area del centro (solo se utente ha un centro)
                    //rimuovere il primo println
                    System.out.println("AGGIUNTA PARAMETRI");
                    System.out.println("\n\nOperazione terminata");
                    System.out.println("Ritorno al menù");
                    exit=true;
                    break;
                
                //select centre
                case 4:
                    //if user does not have a center
                    if(!this.centre.equals(defaultValueOfCentre)){
                        String centre=associaCentro();
                        //TODO fare update del file operatoriAutorizzati
                    }else{
                        System.out.println("Impossibile associarsi ad un altro centro\nSei già associato al centro "+ this.centre);
                    }
                    System.out.println("\n\nOperazione terminata");
                    System.out.println("Ritorno al menù");
                    exit=true;
                    break;
                
                //create centre
                case 5:
                    if(!this.centre.equals(defaultValueOfCentre)){
                        String centre=creaCentro();
                        //TODO fare update del file operatoriAutorizzati
                    }else{
                        System.out.println("Impossibile creare un nuovo centro\nSei già associato al centro "+ this.centre);
                    }
                    System.out.println("\n\nOperazione terminata");
                    System.out.println("Ritorno al menù");
                    exit=true;
                    break;

                //logout
                case 6:
                    exit=false;
                    break;
            
                default:
                    System.out.println("Codice inserito errato!");
                    exit=true;
                    break;
            }
        }while(exit);
    }

    //TODO Rendere modulare
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
                    centre=creaCentro();
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
    private static String creaCentro(){

        MonitoringCentre centre=MonitoringCentre.createCentre();
        if(centre!=null){
            return centre.getNome();
        }else{
            return null;
        }
        
    }

    //user choose a centre from the existing ones
    private static String associaCentro(){

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

    //TODO
    //cambiare anche tipo di ritorno
    public void inserisciParametriClimatici(){}

    /**
     * Ritorna il nome dell'operatore autorizzato
     * @return nome
     */
    public String getNome() {
        return nome;
    }
    /**
     * Ritorna il cognome dell'operatore autorizzato
     * @return cognome
     */
    public String getCognome() {
        return cognome;
    }
    //rimuovere
    public static void main(String[] args) {

        
        int riga=Research.OneStringInCol(file, IndexOf.matricola, "00001");
        System.out.println(riga);
        int col=IndexOf.centro;


        if(CSV_Utilities.updateCellInCSV2(file, "Insubria", riga)){
            System.out.println("Aggiornamento file andato a buon fine");
        }else{
            System.out.println("Errore nell'aggiornamento del file");
        }

        //creaCentro();

        //System.out.println(AutorizedOperator.associaCentro());
        
        /*AutorizedOperator.registrazione();
        AutorizedOperator u=autenticazione();
        //u.menu();
        System.out.println(u);



        //TODO 
        //implementare alla fine di autenticazione()
        //oppure
        //implementare all'inizio di menu(), prima della stampa del menu effettivo
        // Sarebbe carino da implementare
        /*
        if ( u != null )
            System.out.println("Autenticazione effettuata come: " + u.getCognome() + " " + u.getNome());

        
        System.out.println(u);*/
        //AutorizedOperator.cercaAreaGeografica();
        
        /*
        AutorizedOperator.registrazione();
        AutorizedOperator u=autenticazione();
        if(u==null){
            System.out.println("Autenticazione fallita");
        }else{
            System.out.println(u);
        }
        */
    }
}
