/**************************************
 * Matricola    Cognome     Nome
 * 754530       Galimberti  Riccardo
 * 753747       Masolo      Carlos
 * 755152       Paredi      Giacomo
 * 753252       Radice      Lorenzo
 * Sede: Como
***************************************/

package src.users;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.opencsv.CSVReader;

import javax.annotation.processing.SupportedOptions;

import src.monitoringcentre.MonitoringCentre;
/**
 * Un oggetto della classe <code>AutorizedOperator</code> rappresenta
 * un utente con privilegi speciali.
 * Ciò che l'operatore autorizzato può fare è descritto nei metodi che gli appartengono.
 * @author Lorenzo Radice
 * @version 0.0.0
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


    // To read input
    private static Scanner in = new Scanner(System.in);
    // Make the path platform independent
    private final static File file = FileSystems.getDefault().getPath("data", "OperatoriRegistrati.csv").toFile();

    /**
    * Costruisce un operatore autorizzato
    */
    public AutorizedOperator() {}

    //TODO
    //java doc
    public static void registrazione() {
        //TODO
        //migliorare la grafica
        System.out.println("Benvenuto nel form per la registrazione!\nPrego, inserisca le informazioni richieste\n");
        // Insert nome
        System.out.print("Inserire il nome: ");
        String nome=in.nextLine();
        // Insert cognome
        System.out.print("Inserire il cognome: ");
        String cognome=in.nextLine();
        // Insert codice fiscale
        System.out.print("Inserire il codice fiscale: ");
        String codFisc="";
        do{
            codFisc=in.nextLine();
            if(!ControlloCodiceFiscale(codFisc)){
                System.out.print("Codice fiscale non valido.\nReinserire: ");
            }else{
                if(presenzaCodiceFiscale(codFisc)){
                    System.out.print("Codice fiscale già utilizzato.\nReinserire: ");
                }
            }
        }while(!ControlloCodiceFiscale(codFisc) || presenzaCodiceFiscale(codFisc));
        // Insert email
        System.out.print("Inserire la mail: ");
        String email="";
        do{
            email=in.nextLine();
            if(!ControlloEmail(email)){
                System.out.print("Email non valida.\nReinserire: ");
            }else{
                if(presenzaEmail(email)){
                    System.out.print("Email già utilizzata.\nReinserire: ");
                }
            }
        }while(!ControlloEmail(email) || presenzaEmail(email));

        //insert monitoring centre
        //TODO
        String centre=null;

        // Insert password
        System.out.print("Inserire la password: ");
        String passwd=in.nextLine();

        // Set the userid
        short userid=setUserId();

        // Add the operator to the file
        aggiungiOperatore(userid, nome, cognome, codFisc, email, passwd, centre);
        
        System.out.println("\n\nRegistrazione completata!\nPer accedere usare il seguente userid: " + String.format("%05d", userid) + " e la password scelta");
    }

    //TODO
    //java doc
    //return true if authentication is successful
    //TODO
    //cambiare il tipo del ritorno in int per avere più codici di errore(?)
    public boolean autenticazione() {
        //TODO
        //migliorare la grafica
        System.out.println("LOGIN\n");
        System.out.print("Inserire l'user-Id: ");
        String userid="";
        do{
            userid=in.nextLine();
            
            if(!presenzaUserId(userid)){
                System.out.print("User-Id non riconosciuto.\nReinserire: ");
            }
            
        }while(!presenzaUserId(userid));

        int riga=ricercaPerUserId(userid);

        String[] record=getRecord(riga);

        if(record!=null){
            System.out.print("Inserire la password: ");
            String password=in.nextLine();
            //if password match set the object's attributes
            if(record[5].equals(password)){

                this.userid=Short.valueOf(userid);
                this.nome=record[1];
                this.cognome=record[2];
                this.codice_fiscale=record[3];
                this.email_address=record[4];
                this.passwd=password;
                //TODO
                //aggiungere quando i centri sono fatti
                //this.centre=record[6].toString();
                this.centre=null;   //usato temporaneamente, va cambiato
                return true;
            }else{
                return false;
            }
        }else{
            //TODO
            //migliorare?
            System.out.println("Errore");
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
                aggiungiOperatore();
            } catch (IOException e) {
                System.out.println("Errore nella creazione del file");
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

    //return the record corresponding to the row passed as a parameter
    private static String[] getRecord(int riga){
        try{
            // CSV Reader
            CSVReader creader = new CSVReader( new FileReader(file) );
            // Line read
            String [] nextRecord;
            // Read first line
            nextRecord = creader.readNext();
            //read the previous lines
            for(int i=1;i<riga;i++){
                nextRecord=creader.readNext();
            }
            creader.close();

            return nextRecord;
        }catch(Exception e){ //to catch any exception inside try block
            e.printStackTrace();//used to print a throwable class along with other dataset class
            return null;
        }
    }

    // Create the file OperatoriRegistrati.csv and set the header of it
    private static void aggiungiOperatore(){

        String s="Matricola,Nome,Cognome,Codice Fiscale,Email,Password,Centro di Monitoraggio\n";
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
    private static void aggiungiOperatore(short userid, String nome, String cognome, String codice_fiscale, String email_address, String passwd, String centre){

        String s=String.format("%05d", userid);
        s=s + "," + nome + "," + cognome + "," + codice_fiscale + "," + email_address + "," + passwd + "," + centre + "\n";
        
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
    //rendere privato(?) o rimuovere
    public static void leggiOperatori(){
        try{

            FileReader freader = new FileReader(file);//created an object of freader class
            //@SuppressWarnings("resource")
            CSVReader creader= new CSVReader(freader);// created creader object by parsing freader as a parameter
            String [] nextRecord;// created an array of type String
            //read data line by line
            while((nextRecord = creader.readNext())!=null){

                for(String token: nextRecord)
                System.out.print(token +"\t"); //will bring the value of cell seperated by tab space
                System.out.println();
            }

            creader.close();
            System.out.println();
        }catch(Exception e){ //to catch any exception inside try block
            e.printStackTrace();//used to print a throwable class along with other dataset class
        }
    }

    //return true if the Fiscal Code is present in the file
    private static boolean presenzaCodiceFiscale(String cf) {
        return presenzaStringInCol(3, cf);
    }

    //return true if the Email is present in the file
    private static boolean presenzaEmail(String email) {
        return presenzaStringInCol(4, email);
    }

    //return true if the UserId is present in the file
    private static boolean presenzaUserId(String userid) {
        return presenzaStringInCol(0, userid);
    }

    //return the line of the record that match the userid
    private static int ricercaPerUserId(String userid){
        return researchStringInCol(0, userid);
    }
    
    // Research a String in a Column, return true if finded
    private static boolean presenzaStringInCol(int col, String str) {
        try{
            // CSV Reader
            CSVReader creader = new CSVReader( new FileReader(file) );
            // Line read
            String [] nextRecord;
            // Read first line
            nextRecord = creader.readNext();
            // Read data line by line
            while( (nextRecord = creader.readNext()) != null){
                // When the first cell equals the id return true
                if ( nextRecord[col].equals(str) ) {
                    return true;
                }
            }
            creader.close();
            
        }catch(Exception e){ //to catch any exception inside try block
            //e.printStackTrace();//used to print a throwable class along with other dataset class
        }
        return false;
    }

    // Research a String in a Column, return the line of the record
    private static int researchStringInCol( int col, String str ) {
        int line = 0;
        try{
            // CSV Reader
            CSVReader creader = new CSVReader( new FileReader(file) );
            // Line read
            String [] nextRecord;
            // Found flag
            boolean found = false;
            // Read first line
            nextRecord = creader.readNext();
            // If columns are less than col exit code -2
            if ( nextRecord.length <= col )
                return -2;
            // First line will not contain any researched element so, increment and go on
            // Line increment
            line++;
            // Read data line by line
            while( (nextRecord = creader.readNext()) != null && !found ){
                // When the first cell equals the id exit the while
                if ( nextRecord[col].equals(str) ) {
                    found = true;
                }
                // Line increment
                line++;
            }
            creader.close();
            // If the line hasn't been found return -1 as error
            if ( nextRecord == null && ! found )
                line = -1;
        }catch(Exception e){ //to catch any exception inside try block
            e.printStackTrace();//used to print a throwable class along with other dataset class
        }
        return line;
    }

    //TODO
    //main per testare, da rimuove alla fine
    public static void main(String []args){
        AutorizedOperator a=new AutorizedOperator();
        if(a.autenticazione()){
            System.out.println("Autenticazione completata");
            //resto dei metodi dell'operatore autorizzato
        }else{
            System.out.println("Autenticazione fallita");
            //ritorno al menu di partenza(?)
        }
    }

}