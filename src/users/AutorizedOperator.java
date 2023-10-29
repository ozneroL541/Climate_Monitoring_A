/**************************************
 * Matricola    Cognome     Nome
 * 754530       Galimberti  Riccardo
 * 755152       Paredi      Giacomo
 * 753252       Radice      Lorenzo
 * Sede: Como
***************************************/

package src.users;
//TODO remove unused import
import src.Input.InputScanner;
import src.cryptography.AES;
import src.cryptography.Chiper_DeChiper;
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
 * @version 0.10.2
 */
public class AutorizedOperator extends User {
    // Name
    private String nome;
    // Surname
    private String cognome;
    // Fiscal Code
    private String codice_fiscale;
    // e-mail address
    private String email_address;
    // User Identity Code
    private short userid;
    // User Password
    private String passwd;
    // Monitoring Centre
    //TODO
    //cambiare il tipo della variabile in short
    private MonitoringCentre centre;

    // Make the path platform independent
    private final static File file = FileSystems.getDefault().getPath("data", "OperatoriRegistrati.csv").toFile();

    /**
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
                System.err.println(record[0]);
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
                return false;
            }
        } catch( InputMismatchException e ){
            e.printStackTrace();
            return false;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
        //AutorizedOperator.registrazione();
        AutorizedOperator a = new AutorizedOperator();
        if (a.autenticazione()) {
            System.err.println("Autenticato");
            System.out.println(a.toString());
        }
    }
}