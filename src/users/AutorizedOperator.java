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
 * @version 0.21.0
 */
public class AutorizedOperator extends User {
    /**
     * Classe che contiene il menù operatore.
     * @author Lorenzo Radice
     * @version 0.21.0
     */
    public class MenuOperator {
        /**
         * Indexes
         */
        private static final record IndexOf() {
            private static final short research = 1;
            private static final short view_areas = 2;
            private static final short make_area = 3;
            private static final short add_parameters = 4;
            private static final short set_centre = 5;
            private static final short make_centre = 6;
            private static final short exit = 7;
        }
        // Menu string
        private String menu = null;
        // Exit Option
        private final String exit = "Logout";
        /**
         * Costruisce un oggetto menù
         */
        public MenuOperator(){
            // Separator string
            final String separator = " - ";
            // Header
            final String header = "\n\tMenù Operatore Autorizzato\n";
            // Options array
            final String[] options = {
                (IndexOf.research       + separator + "Ricerca aree"),
                (IndexOf.view_areas     + separator + "Visualizza informazioni aree"),
                (IndexOf.make_area      + separator + "Crea area"),
                (IndexOf.add_parameters + separator + "Aggiungi parametri"),
                (IndexOf.set_centre     + separator + "Seleziona centro"),
                (IndexOf.make_centre    + separator + "Crea centro di monitoraggio"),
                (IndexOf.exit           + separator + exit)
            };
            // Initialize menu
            this.menu = header;
            // For every element in the options array
            for ( short i = 0; i < options.length; i++ ) {
                // Create the menu string
                this.menu += options[i] + '\n';
                // If the current option string is equal to exit than
            }
        }
        /**
         * Mostra il menù e permette di sceglierne le opzioni.
         */
        public void ChooseOption() {
            // Short integer for the menu options
            short mainmenu_input = 0;
            // Input
            String input = "";
            // While exit is not selected
            do {
                // Output the menu
                System.out.println(this.getMenu());
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
            // Check if exit
            } while ( selectedAction(mainmenu_input) );
        }
        /**
         * Restituisce la stringa che rappresenta il menù
         * @return menù
         */
        public String getMenu() {
            return this.menu;
        }
    }
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
        int riga = -1;
        AutorizedOperator a = null;
        String userid;
        String password;
        System.out.println("LOGIN\n");

        System.out.print("Inserire l'User-ID: ");
        userid = InputScanner.INPUT_SCANNER.nextLine();
        System.out.print("Inserire la password: ");
        password=InputScanner.INPUT_SCANNER.nextLine();

        //return the column where UserId is
        riga=Research.OneStringInCol(file, IndexOf.matricola, userid);
        if ( riga > 0 ) {
            // Initialize record
            String[] record = null;
            // Check if the research returned a valid result
            record = Research.getRecord(file, riga);
            if(record!=null){
                //if password match, set the object's attributes
                if(record[IndexOf.password].equals(password)){
                    a =  new AutorizedOperator(Short.valueOf(userid), record[IndexOf.nome], record[IndexOf.cognome], record[IndexOf.codice_fiscale], record[IndexOf.email], password, record[IndexOf.centro]);
                }
            }
        }
        // Return a
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
    // TODO JD
    //cambiare tipo di ritorno in boolean?
    //user inserts climatic parameters
    public boolean inserisciParametriClimatici(){
        if(!this.centre.equals(defaultValueOfCentre)){
            Parameters p = Parameters.MakeParameters(centre);
            if (p != null) {
                return p.addToCSV();
            } else
                return false;
        }else{
            System.out.println("Impossibile inserire i parametri climatici\nPer inserire i parametri bisogna essere associati ad un centro");
            return false;
        }
    }
    @Override
    public String toString(){
        final String none = "NESSUNO";
        String str = "";
        str += "User ID: " + String.format("%05d", this.userid) + "\n";
        str += "Nome: "   + this.nome + "\n";
        str += "Cognome: "       + this.cognome + "\n";
        str += "Codice Fiscale: "   + this.codice_fiscale + "\n";
        str += "Indirizzo Email: " + this.email_address + "\n";
        str += "Password: "     + this.passwd + "\n" ;
        if(this.centre==defaultValueOfCentre){
            str += "Id Centro di appartenenza: " + none;
        }else{
            str += "Id Centro di appartenenza: "    + this.centre;
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
    // TODO JD
    public void menu() {
        MenuOperator mo = new MenuOperator();
        mo.ChooseOption();
    }
    //update file with new value of centre
    private boolean addCentreToFile(String centre){
        int riga=Research.OneStringInCol(file, IndexOf.matricola, Short.toString(this.userid));
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
            case MenuOperator.IndexOf.make_centre:
                // Make Centre
                MonitoringCentre.insertCentre();
                return true;
            case MenuOperator.IndexOf.exit:
                // Logout
                return false;
            default:
                // Error Message
                System.out.println("\nIl valore inserito non è corretto.");
                System.out.println("Inserire un numero valido per continuare.\n");
                return true;
            }
        }
    }
