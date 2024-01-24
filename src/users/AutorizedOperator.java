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
<<<<<<< HEAD
//TODO remove unused import
import src.Input.InputScanner;
import src.cryptography.AES;
import src.cryptography.Chiper_DeChiper;
=======

import src.common.*;
import src.geographicarea.GeographicArea;
>>>>>>> Development
import src.monitoringcentre.MonitoringCentre;
import src.parameters.Parameters;

/**
 * Un oggetto della classe <code>AutorizedOperator</code> rappresenta
 * un utente con privilegi speciali.
 * Ciò che l'operatore autorizzato può fare è descritto nei metodi che gli appartengono.
 * @author Giacomo Paredi
 * @version 1.0.1
 */
public class AutorizedOperator extends User {
    /**
<<<<<<< HEAD
    * Costruisce un operatore autorizzato
    */
    public AutorizedOperator() {}

    //TODO
    //java doc
    public static void registrazione() {
        // Datas
        String nome = "", cognome = "", codFisc = "", email = "", centre = "", passwd = "";
        // Exit loop
        boolean exit = false;
        // Max number of operators
        final int max_operators = 99999;
        try {
            // Check if number of operators exceded
            if ( file.exists() && Files.lines(file.toPath()).count() > (max_operators + 1) ) {
                // Error Output
                System.err.println("Numero massimo di operatori raggiunto.\nNon è possibile effettuare la registrazione");
            } else {
                //TODO
                //migliorare la grafica
                System.out.println("Benvenuto nel form per la registrazione!\nPrego, inserisca le informazioni richieste\n");
                
                // Insert name
                System.out.print("Inserire il nome: ");
                do{
                    nome=InputScanner.INPUT_SCANNER.nextLine();
                    //check if name contains only letters
                    if(!onlyLettersInString(nome)){
                        System.out.print("Nome non valido.\nReinserire: ");
                    }else{
                        //exit loop
                        exit=true;
                    }
                }while(!exit);  //loop if name is wrong
                exit = false;
                // Insert last name
                System.out.print("Inserire il cognome: ");
                do{
                    cognome=InputScanner.INPUT_SCANNER.nextLine();
                    //check if last name contains only letters
                    if(!onlyLettersInString(cognome)){
                        System.out.print("Cognome non valido.\nReinserire: ");
                    }else{
                        //exit loop
                        exit=true;
                    }
                }while(!exit);  //loop if last name is wrong
                exit = false;
                // Insert codice fiscale
                System.out.print("Inserire il codice fiscale: ");
                codFisc="";
                do{
                    // Input Fiscal Code
                    codFisc=InputScanner.INPUT_SCANNER.nextLine();
                    // Upper case Fiscal Code
                    codFisc = codFisc.toUpperCase();
                    //check if fiscal code is correct
                    if(!ControlloCodiceFiscale(codFisc)){
                        System.out.print("Codice fiscale non valido.\nReinserire: ");
                    }else if( file.exists() && Research.isStringInCol(file, 3, codFisc)){ //check if fiscal code is unique in the file
                        //TODO: Criptare il codice fiscale con password universale per effettuare questo controllo
                        System.out.print("Codice fiscale già utilizzato.\nReinserire: ");
                    } else {
                        // Exit the loop
                        exit = true;
                    }
                }while( ! exit );   //loop if fiscal code is wrong or if it is not unique in the file
                // Insert email
                System.out.print("Inserire l'indirizzo e-mail: ");
                email="";
                exit = false;
                do{
                    email=InputScanner.INPUT_SCANNER.nextLine();
                    //check if email is correct
                    if(!ControlloEmail(email)){
                        System.out.print("Indirizzo non valido.\nReinserire: ");
                    }else if( file.exists() && Research.isStringInCol(file, 4, email)){
                        //TODO: Criptare la mail con password universale per effettuare questo controllo
                        System.out.print("Indirizzo già utilizzato.\nReinserire: "); //check if email is unique in the file
                    } else {
                        // Exit loop
                        exit = true;
                    }
                } while( ! exit );   //loop if email is wrong and if it is not unique in the file

                //insert monitoring centre
                //TODO
                centre=null;

                // Insert password
                System.out.print("Inserire la password: ");
                passwd=InputScanner.INPUT_SCANNER.nextLine();
            }
        } catch ( IOException e ){
            // Print Error
            e.printStackTrace();
        } catch (InputMismatchException e) {
            // Print Error
            e.printStackTrace();
        } catch ( NoSuchElementException e ){
            // Print Error
            e.printStackTrace();
            System.err.println(e.toString());
        } catch ( Exception e ) {
            // Print Error
            e.printStackTrace();
        }
        // Set the userid
        short userid=setUserId();

        // Add the operator to the file
        String[] record = data_toRecord(userid, nome, cognome, codFisc, email, passwd, centre);
        scriviOperatore_cifrato(record);
        
        System.out.println("\n\nRegistrazione completata!\nPer accedere usare il seguente User-ID: " + String.format("%05d", userid) + " e la password scelta");
    }

    //TODO
    //java doc
    //return true if authentication is successful
    //TODO
    /*cambiare il tipo del ritorno in int per avere più codici di errore(?)
     * Ritornare vari numeri negativi a seconda dell'errore.
     * 0 se è avvenuta l'autenticazione
    */
    public boolean autenticazione() {
        // If file doesn't exist exit
        if ( ! file.exists() ){
            // Error Output
            System.err.println("ERRORE: il file " + file.getName() + " non si trova nella cartella \'" + file.getParent() + "\'.\n" );
            // Error return
            return false;
        }
        // Attempt limit
        final int limit = 3;
        // Counter 
        int c = 0;
        //TODO
        //migliorare la grafica
        System.out.println("LOGIN\n");
        System.out.print("Inserire lo User-ID: ");
        try {
            String userid = InputScanner.INPUT_SCANNER.nextLine();
            System.out.print("Inserire la password: ");
            String password=InputScanner.INPUT_SCANNER.nextLine();
            // Encrypt userid to search
            userid = AES.encrypt(userid, password);
            // Search encrypted userid
            //return the column where UserId is
            String[] record = Research.getRecordByData(file, 0, userid);
            // If the result is valis
            if(record!=null){
                record = Chiper_DeChiper.deCipher_Record(record, password);
                this.userid=Short.valueOf(record[0]);
                this.nome=record[1];
                this.cognome=record[2];
                this.codice_fiscale=record[3];
                this.email_address=record[4];
                this.passwd=password;
                return true;
            }else{
                //TODO
                //migliorare?
                System.err.println("Errore aaaa");
=======
     * Permette all'utente di autenticarsi inserendo il proprio id e la password.
     * Ritorna un oggetto di AutorizedOperator se l'autenticazione avviene con successo, altrimenti ritorna null.
     * @return oggetto di AutorizedOperator
     */
    public static AutorizedOperator autenticazione(){
        //userid and password must be guessed under a finite number of attemps
        // Attempt limit
        final int limit = 3;
        //counter
        int c=0;
        //AutorizedOperator object
        AutorizedOperator u=null;

        // If file doesn't exist exit
        if ( ! file.exists() ){
            // Output
            System.out.println("Non ci sono Operatori Autorizzati registrati.");
            // Error return
            return null;
        }

        u=login();
        while((u==null || !u.Exist()) && c<limit){
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
    /**
     * Effettua il Login dell'Operatore Autorizzato.
     * @return Operatore Autorizzato
     */
    private static AutorizedOperator login(){
        AutorizedOperator a = null;
        String userid = null;
        String password = null;
        // Title
        System.out.println("LOGIN");
        // Ask
        System.out.print("Inserire l'User-ID: ");
        userid = InputScanner.INPUT_SCANNER.nextLine();
        // Ask
        System.out.print("Inserire la password: ");
        password=InputScanner.INPUT_SCANNER.nextLine();
        // Check for Parsing
        try {
            // Create Operator
            a = new AutorizedOperator(Short.valueOf(userid), password);
        } catch (NumberFormatException e) {
            // Set a as null
            a = null;
        }
        // Check creation (a == null compulsory) 
        if ( a == null || !a.Exist() ) {
            // Set a as null
            a = null;
        }
        // Return
        return a;
    }
    /** Codice Utente */
    private short userid;
    /** Nome */
    private String nome;
    /** Cognome */
    private String cognome;
    /** Codice fiscale */
    private String codice_fiscale;
    /** Indirizzo e-mail */
    private String email_address;
    /** Password dell'utente */
    private String passwd;
    /** Centro di monitoraggio */
    private String centre;
    /**
     * Costruisce un Operatore Autorizzato usando userid e password.
     * @param userid userid
     * @param passwd password
     */
    public AutorizedOperator(short userid, String passwd) {
        //return the column where UserId is
        int riga=Research.OneStringInCol(file, IndexOf.matricola, String.format("%05d", userid));
        // Check
        if ( riga > 0 ) {
            // Initialize record
            String[] record = null;
            // Check if the research returned a valid result
            record = Research.getRecord(file, riga);
            if(record!=null){
                //if password match, set the object's attributes
                if(record[IndexOf.password].equals(passwd)){
                    this.userid         = userid;
                    this.nome           = record[IndexOf.nome];
                    this.cognome        = record[IndexOf.cognome];
                    this.codice_fiscale = record[IndexOf.codice_fiscale];
                    this.email_address  = record[IndexOf.email];
                    this.passwd         = passwd;
                    this.centre         = record[IndexOf.centro];
                }
            }
        }
    }
    /**
    * Costruttore vuoto
    */
    public AutorizedOperator() {}
    /**
     * Costruttore di <code>AutorizedOperator</code>.
     * Costruisce l'oggetto AutorizedOperator dati i valori passati come parametri.
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
    /**
     * Permette all'Operatore Autorizzato di inserire
     * i parametri climatici per un'area geografica appartenente
     * al suo centro di monitoraggio.
     * @return true se i parametri sono stati aggiunti al loro file con successo
     */
    public boolean inserisciParametriClimatici(){
        // Check if the operator is associate with a centre
        if(hasCentre()){
            // Make Parameters
            Parameters p = Parameters.MakeParameters(centre);
            // Check result of the operation
            if (p != null) {
                // Add Parameters to CSV
                return p.addToCSV();
            } else {
                // Error message
                System.err.println("Parametri non aggiunti.");
                // Return with error
>>>>>>> Development
                return false;
            }
        }else{
            System.out.println("Impossibile inserire i parametri climatici\nPer inserire i parametri bisogna essere associati ad un centro");
            return false;
        }
    }
    @Override
    public String toString(){
        final String none = "NESSUNO";
        String str = "";
        str += "User ID:\t\t"           + String.format("%05d", this.userid) + "\n";
        str += "Nome:\t\t\t"            + this.nome + "\n";
        str += "Cognome:\t\t"           + this.cognome + "\n";
        str += "Codice Fiscale:\t\t"    + this.codice_fiscale + "\n";
        str += "Indirizzo e-mail:\t"     + this.email_address + "\n";
        str += "Password:\t\t"          + this.passwd + "\n" ;
        if(hasCentre()){
            str += "Centro di appartenenza:\t" + this.centre;
        }else{
            str += "Centro di appartenenza:\t" + none;
        }
        return str;
    }
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
    /**
     * Assegna un Centro di Monitoraggio all'Operatore autorizzato, se non lo ha già.
     * @param centre centro di monitoraggio
     * @return true se l'esecuzione ha avuto successo
     */
    public boolean setCentre(String centre) {
        //if user does not have a center
        if(this.centre != null && !hasCentre()){
            this.centre = centre;
            if (file.exists()) {
                if( !addCentreToFile(centre) ){
                    System.err.println("ERRORE: aggiornamento file " + file.getName() + " fallito.");
                    this.centre = defaultValueOfCentre;
                    return false;
                } else {
                    return true;
                }   
            } else {
                // Error output
                System.err.println("\nERRORE: il file " + file.getName() + " è stato rimosso dalla cartella \'" + file.getParent() + "\'.");
                System.err.println("Aggiornamento del file fallito.");
                return false;
            }
        }else{
            System.err.println("ERRORE: il centro non è al valore di default.");
            return false;
        }
    }
    /**
     * Mostra il menu dell'Operatore Autorizzato e ne esegue le opzioni
     */
    public void menu() {
        // Make a menu
        MenuOperator mo = new MenuOperator();
        ChooseOption(mo);
    }
    /**
     * Controlla l'esistenza dell'oggetto Operatore
     * @return true se il centro esiste
     */
    public boolean Exist() {
        return this.nome != null && this.nome.length() > 0;
    }
    /**
     * Crea un'area geografica e la salva sul file.
     * @return true se l'esecuzion è avvenuta correttamente.
     */
    private boolean makeArea() {
        // Input string
        String in = null;
        // Create Geografica Area
        GeographicArea ga = GeographicArea.createArea();
        // Check if creation succeded
        if (ga != null) {
            // Print Area
            System.out.println(ga.toString());
            // Ask
            System.out.println("Aggiungere l'area(S/N)?");
            // Input
            in = InputScanner.INPUT_SCANNER.nextLine();
            // Check input
            if (! CommonMethods.ExitLoop(in)) {
                // Add area to file
                if (ga.addToCSV()) {
                    // Return true
                    return true;
                } else {
                    // Error message
                    System.err.println("Errore: l'area non è stata aggiunta al file.");
                    // Return false
                    return false;
                }
            } else
                // End correctly
                return true;
        } else {
            System.out.println("Creazione area Geografica fallita.");
            return false;
        }
    }
    /**
     * Viene richiesto all'Operatore Autorizzato il centro a cui associarsi tra quelli presenti.
     * @return true se l'associazione ha avuto successo o se l'operatore è già associato ad un centro.
     */
    private boolean chooseCentre() {
        // Check centre is not null
        if (this.centre != null) {
            // If centre equals default vaulue go on
            if (!hasCentre()) {
                // Set Centre
                return setCentre(associaCentro());
            } else {
                // The Operator is already associate to a centre 
                System.out.println("Impossibile associarsi ad un centro.\nSei già associato al centro " + this.centre + ".");
                return true;
            }
        } else {
            // Error, centre cannot be null
            System.err.println("ERRORE: oggetto Operatore Autorizzato corrotto.");
            return false;
        }
    }
<<<<<<< HEAD
    //Update the file OperatoriRegistrati with a new record
    private static String[] data_toRecord(short userid, String nome, String cognome, String codice_fiscale, String email_address, String passwd, String centre){
        String[] record = new String[7];
        record[0] = String.format("%05d", userid);
        record[1] = nome;
        record[2] = cognome;
        record[3] = codice_fiscale;
        record[4] = email_address;
        record[5] = passwd;
        record[6] = centre;
        return record;

    }
    //Update the file OperatoriRegistrati with a new record
    private static void scriviOperatore_cifrato(String[] record ){

        String[] c = Chiper_DeChiper.recordCipher_pw(record, 5);
        String o = "";

        for (String string : c) {
            o += string + ",";
        }
        o = o.substring(0,o.length()-1);
        BufferedWriter scrivi;

        try {
            scrivi=new BufferedWriter(new FileWriter(file, true));
            scrivi.append(o);
            scrivi.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //TODO Remove TestMain
    public static void main(String[] args) {
        AutorizedOperator.registrazione();
        AutorizedOperator a = new AutorizedOperator();
        if (a.autenticazione()) {
            System.err.println("Autenticato");
            System.out.println(a.toString());
        }
    }
}
=======
    /**
     * Controlla se l'Operatore Autorizzato è associato ad un centro di monitoraggio.
     * @return true se l'operatore è associato ad un centro
     */
    private boolean hasCentre() {
        return !(this.centre.equals(defaultValueOfCentre));
    }
    /**
     * Aggiorna il file con il nuovo valore del centro
     * @param centre centro
     * @return true se l'esecuzione è avvenuta con successo
     */
    private boolean addCentreToFile(String centre){
        if (!file.exists()) {
            return false;
        }
        int riga=Research.OneStringInCol(file, IndexOf.matricola, String.format("%05d", this.userid));
        // Check
        if (riga < 0) {
            return false;
        }
        return CSV_Utilities.addCellAtEndOfLine(file, centre, riga);
    }
    /**
     * Esegue l'azione selezionata
     * @param input azione
     * @return false se l'azione è di uscita
     */
    private boolean selectedAction( short input ) {       
        // Select the method choosen by the user
        switch (input) {
            case MenuOperator.IndexOf.research:
                // Ricerca aree
                cercaAreaGeografica();
                return true;
            case MenuOperator.IndexOf.view_areas:
                // Visualizza info aree
                visualizzaAreaGeografica();
                return true;
            case MenuOperator.IndexOf.make_area:
                // Make area
                makeArea();
                return true;
            case MenuOperator.IndexOf.add_parameters:
                // Add Parameters
                inserisciParametriClimatici();
                return true;
            case MenuOperator.IndexOf.set_centre:
                // Choose a Centre
                chooseCentre();
                return true;
            case MenuOperator.IndexOf.make_centre:
                // Make Centre
                MonitoringCentre.registraCentroAree();
                return true;
            case MenuOperator.IndexOf.exit:
                // Logout
                return false;
            default:
                // Error Message
                System.out.println("Il valore inserito non è corretto.");
                System.out.println("Inserire un numero valido per continuare.\n");
                return true;
        }
    }
    /**
     * Mostra il menu e permette di sceglierne le opzioni.
     * @param m menu dell'Operatore Autorizzato
     */
    private void ChooseOption(MenuOperator m) {
        // Short integer for the menu options
        short mainmenu_input = 0;
        // Input
        String input = "";
        // While exit is not selected
        do {
            // Output the menu
            System.out.println(m.getMenu());
            // Request
            System.out.print("Inserire codice:\t");
            // Input
            try {
                // Input
                input = InputScanner.INPUT_SCANNER.nextLine();
                // Parse input
                mainmenu_input = (short) Short.valueOf(input);
            } catch (NumberFormatException e) {
                // Set to 0
                mainmenu_input = 0;
            } catch (Exception e) {
                // Set to -1
                mainmenu_input = -1;
            }
            // New Line
            System.out.println();
        // Check if exit
        } while ( selectedAction(mainmenu_input) );
    }
}
>>>>>>> Development
