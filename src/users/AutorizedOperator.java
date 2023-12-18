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

import src.common.Research;
import src.geographicarea.GeographicArea;
import src.common.CSV_Utilities;
import src.common.CommonMethods;
import src.common.InputScanner;

import src.monitoringcentre.MonitoringCentre;
import src.parameters.Parameters;

/**
 * Un oggetto della classe <code>AutorizedOperator</code> rappresenta
 * un utente con privilegi speciali.
 * Ciò che l'operatore autorizzato può fare è descritto nei metodi che gli appartengono.
 * @author Giacomo Paredi
 * @version 0.21.1
 */
public class AutorizedOperator extends User {
    /**
     * Ritorna DefaultValueOfCentre come Short
     * @return DefaultValueOfCentre
     */
    public static String getDefaultValueOfCentre(){
        return defaultValueOfCentre;
    }
    /**
     * Permette all'utente di autenticarsi inserendo il proprio id e la password
     * Ritorna un oggetto di AutorizedOperator se l'autenticazione avviene con successo, altrimenti ritorna null
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
    //evaluate userid and password
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
    /**
     * Permette all'Operatore Autorizzato di inserire i parametri climatici per un'area geografica appartenente
     * al suo centro di monitoraggio
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
                System.err.println("Paramtri non aggiunti.");
                // Return with error
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
        str += "Indirizzo Email:\t"     + this.email_address + "\n";
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
     */
    public boolean setCentre(String centre) {
        //if user does not have a center
        if(this.centre != null && !hasCentre()){
            this.centre = centre;
            if(! addCentreToFile(centre) ){
                System.err.println("ERRORE: aggiornamento file centri fallito.");
                this.centre = defaultValueOfCentre;
                return false;
            } else {
                return true;
            }
        }else{
            System.err.println("ERRORE: il centro non è al valore di default.");
            return false;
        }
    }
    /**
     * Crea un'area geografica e la salva sul file.
     * @return true se l'esecuzion è avvenuta correttamente.
     */
    public boolean makeArea() {
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
            System.out.println("Creazione area Geografica fallita");
            return false;
        }
    }
    /**
     * Mostra il menù dell'Operatore Autorizzato e ne esegue le opzioni
     */
    public void menu() {
        // Make a menu
        MenuOperator mo = new MenuOperator();
        ChooseOption(mo);
    }
    //update file with new value of centre
    private boolean addCentreToFile(String centre){
        int riga=Research.OneStringInCol(file, IndexOf.matricola, String.format("%05d", this.userid));
        return CSV_Utilities.addCellAtEndOfLine(file, centre, riga);
    }
    // Execute selected action
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
                MonitoringCentre.insertCentre();
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
     * Controlla l'esistenza dell'oggetto Operatore
     * @return true se il centro esiste
     */
    public boolean Exist() {
        return this.nome != null && this.nome.length() > 0;
    }
    /**
     * Viene richiesto all'Operatore Autorizzato il centro a cui associarsi tra quelli presenti.
     * @return true se l'associazione ha avuto successo o se l'operatore è già associato ad un centro.
     */
    public boolean chooseCentre() {
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
    /**
     * Controlla se l'Operatore Autorizzato è associato ad un centro di monitoraggio.
     * @return true se l'operatore è associato ad un centro
     */
    public boolean hasCentre() {
        return !(this.centre.equals(defaultValueOfCentre));
    }
    /**
     * Mostra il menù e permette di sceglierne le opzioni.
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
