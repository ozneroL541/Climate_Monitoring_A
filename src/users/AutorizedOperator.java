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
import src.common.CSV_Utilities;
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
        // TODO Test
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
    /**
     * Mostra il menù e permette di scegliere le azioni eseguibili dall'operatore autorizzato
     */
    public void menu(){
        // TODO Modularizzare

        final String menu="\n\nMenù Operatore Autorizzato\n"+
                            "1) Cerca area geografica\n"+
                            "2) Visualizza area geografica\n"+
                            "3) Creazione area geografica"+
                            "4) Aggiunta parametri climatici\n"+
                            "5) Selezione centro di appartenenza\n"+
                            "6) Creazione nuovo centro di monitoraggio\n"+
                            "7) Logout\n";    
        
        boolean exit=true;
        // input
        String input = null;
        short scelta = 0;

        do{
            scelta=0;
            System.out.print(menu);

            System.out.print("\nInserire codice: ");

            // Input
            try {
                // Input
                input = InputScanner.INPUT_SCANNER.nextLine();
                // Parse input
                scelta = (short) Short.valueOf(input);
            } catch (NumberFormatException e) {
                // Set to 0
                scelta = 0;
            } catch (Exception e) {
                // Set to -1
                scelta = -1;
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
                
                //create geographic area
                case 3:
                    //TODO creazione area geografica
                    exit=true;
                    break;
                //add climate parameters
                case 4:
                    if(!this.centre.equals(defaultValueOfCentre)){
                        inserisciParametriClimatici(this.centre);
                    }else{
                        System.out.println("Impossibile inserire i parametri climatici\nPer inserire i parametri devi essere associato ad un centro");
                    }
                    System.out.println("\n\nOperazione terminata");
                    System.out.println("Ritorno al menù");
                    exit=true;
                    break;
                
                //select centre
                case 5:
                    //if user does not have a center
                    if(this.centre.equals(defaultValueOfCentre)){
                        String centre=associaCentro();
                        boolean success=addCentreToFile(centre);
                        if(!success){
                            System.out.println("Errore nell'aggiornamento del file");
                        }                        
                    }else{
                        System.out.println("Impossibile associarsi ad un altro centro\nSei già associato al centro "+ this.centre);
                    }
                    System.out.println("\n\nOperazione terminata");
                    System.out.println("Ritorno al menù");
                    exit=true;
                    break;
                
                //create centre
                case 6:
                    MonitoringCentre.insertCentre();
                    exit=true;
                    break;
                // TODO Manca la creazione dell'area geografica
                //logout
                case 7:
                    exit=false;
                    break;
            
                default:
                    System.out.println("Codice inserito errato!");
                    exit=true;
                    break;
            }
        }while(exit);
    }
    //update file with new value of centre
    private boolean addCentreToFile(String centre){
        int riga=Research.OneStringInCol(file, IndexOf.matricola, Short.toString(this.userid));
        return CSV_Utilities.addCellAtEndOfLine(file, centre, riga);
    }

    //cambiare tipo di ritorno in boolean?
    //user inserts climatic parameters
    private static void inserisciParametriClimatici(String centre){
        Parameters p=Parameters.MakeParameters(centre);
        //TODO fare controllo sulla esistenza? altrimenti questo oggetto è abbastanza inutile
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
}
