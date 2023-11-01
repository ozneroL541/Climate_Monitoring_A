/**************************************
 * Matricola    Cognome     Nome
 * 754530       Galimberti  Riccardo
 * 755152       Paredi      Giacomo
 * 753252       Radice      Lorenzo
 * Sede: Como
***************************************/

package src.users;
import src.input.InputScanner;
import src.monitoringcentre.MonitoringCentre;
import src.research.Research;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

/**
 * Un oggetto della classe <code>AutorizedOperator</code> rappresenta
 * un utente con privilegi speciali.
 * Ciò che l'operatore autorizzato può fare &egrave descritto nei metodi che gli appartengono.
 * @author Giacomo Paredi
 * @version 0.12.2
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
    private short centre;

    // Make the path platform independent
    private final static File file = FileSystems.getDefault().getPath("data", "OperatoriRegistrati.dati.csv").toFile();

    //dafault value for attribute centre if user does not choose a centre during registration
    private final static short defaultValueOfCentre=-1;

    //TODO
    //aggiungere classe privata con indici del file csv

    /**
    * Costruttore vuoto
    */
    public AutorizedOperator() {}

    /**
     * Costruttore di <code>AutorizedOperator</code>
     * Costruisce l'oggetto AutorizedOperator dati i valori passati come parametriS
     * @param id id univoco dell'operatore
     * @param nome nome dell'operatore
     * @param cognome cognome dell'operatore
     * @param cod_fis codice fiscale dell'operatore
     * @param email_add indirizzo email dell'operatore
     * @param password password scelta dall'operatore
     * @param centre centro al quale l'operatore appartiene. Se non appartiene a nessun centro assume un valore di default 
     */
    public AutorizedOperator(short id, String nome, String cognome, String cod_fis, String email_add, String password, short centre){
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
    public static short getDefaultValueOfCentre(){
        return defaultValueOfCentre;
    }

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
                        System.out.print("Indirizzo già utilizzato.\nReinserire: "); //check if email is unique in the file
                    } else {
                        // Exit loop
                        exit = true;
                    }
                } while( ! exit );   //loop if email is wrong and if it is not unique in the file

                //insert monitoring centre
                //TODO
                //cambiare appena è fatto
                centre=String.valueOf(defaultValueOfCentre);

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
        aggiungiOperatore(userid, nome, cognome, codFisc, email, passwd, centre);
        
        System.out.println("\n\nRegistrazione completata!\nPer accedere usare il seguente User-ID: " + String.format("%05d", userid) + " e la password scelta");
    }

    //return an AutorizedOperator object if userid and password are guessed under 3 attemps
    private static AutorizedOperator autenticazione(){
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
        if(Research.isStringInCol(file, 0, userid)){
            //return the column where UserId is
            int riga=Research.OneStringInCol(file, 0, userid);
            // Initialize record
            String[] record = null;
            // Check if the research returned a valid result
            if(riga > 0){
                record = Research.getRecord(file, riga);
            }
            if(record!=null){
                //if password match, set the object's attributes
                if(record[5].equals(password)){
                    return new AutorizedOperator(Short.valueOf(userid), record[1], record[2], record[3], record[4], password, Short.valueOf(record[6]));
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

    //set the userid
    private static short setUserId(){
        long id=0;
        //if the file doesn't exist, create it and add the first operator with userid 00001, else add the operator with incremental userid
        if(!file.exists()){
            try {
                file.createNewFile();
                addHeader();
            } catch (IOException e) {
                System.err.println("Errore nella creazione del file");
            }
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
        return (short)id;
    }
    
    // Check Codice Fiscale
    private static boolean ControlloCodiceFiscale( String cf ) {
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
                    // If the current character is not a letter the string isn incorrect
                    if ( ! Character.isLetter(cf.charAt(i)) ) {
                        check = false;
                    }
                }
            }
            // If the string can still be true continue with the verification else terminate the execution fo the function
            if (check) {
                // Characters of the months
                char[] m = { 'A', 'B', 'C', 'D', 'E', 'H', 'L', 'M', 'P', 'R', 'S', 'T' };
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

    private static boolean onlyLettersInString(String s){
        return s.matches("[a-zA-Z]+");
    }

    // Create the file OperatoriRegistrati.csv and set the header of it
    private static void addHeader(){
        // File Header
        final String header = "Matricola,Nome,Cognome,Codice Fiscale,Email,Password,Centro di Monitoraggio\n";
        BufferedWriter scrivi;

        try {
            scrivi=new BufferedWriter(new FileWriter(file, true));
            scrivi.append(header);
            scrivi.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    //Update the file OperatoriRegistrati with a new record
    private static void aggiungiOperatore(short userid, String nome, String cognome, String codice_fiscale, String email_address, String passwd, String centre){

        String s=String.format("%05d", userid);
        s += "," + nome + "," + cognome + "," + codice_fiscale + "," + email_address + "," + passwd + "," + centre + "\n";
        
        BufferedWriter scrivi;

        try {
            scrivi=new BufferedWriter(new FileWriter(file, true));
            scrivi.append(s);
            scrivi.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //TODO 
    //rimuovere
    public static void main(String[] args) {
        AutorizedOperator.registrazione();
        AutorizedOperator u=autenticazione();
        if(u==null){
            System.out.println("Autenticazione fallita");
        }else{
            System.out.println(u);
        }
    }
}
