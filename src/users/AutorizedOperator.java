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
    private MonitoringCentre centre;

    // To read input
    private Scanner in=new Scanner(System.in);
    // Make the path platform independent
    private static Path filepath=FileSystems.getDefault().getPath("data", "OperatoriRegistrati.csv");

    private static File file=new File(filepath.toFile().toString());;

    //TODO
    //rimuove non appena faccio la lettura 
    // To read files
    private BufferedReader leggi;

    public AutorizedOperator() {

        /*
        try {
            FileReader leggi=new FileReader(s);

            int data;
            data=leggi.read();
            while(data!=-1){
                System.out.print((char)data);
                data = leggi.read();
            }

            leggi.close();
            //System.out.println("qualcosa: "+ leggi.read());

        } catch (FileNotFoundException ex) {
            System.out.println("a");
        } catch (IOException ex) {
            System.out.println("b");
        }*/
        
    }

    public void registrazione() {
        //TODO
        //migliorare la grafica
        System.out.println("Benvenuto nel form per la registrazione!\nPrego, inserisca le informazioni richieste\n");
        // Insert nome
        System.out.print("Inserire il nome: ");
        this.nome=in.nextLine();
        // Insert cognome
        System.out.print("Inserire il cognome: ");
        this.cognome=in.nextLine();
        // Insert codice fiscale
        System.out.print("Inserire il codice fiscale: ");
        String codFisc="";
        do{
            codFisc=in.nextLine();
            if(!ControlloCodiceFiscale(codFisc)){
                System.out.print("Codice fiscale non valido.\nReinserire: ");
            }
        }while(!ControlloCodiceFiscale(codFisc));
        this.codice_fiscale=codFisc;
        // Insert email
        System.out.print("Inserire la mail: ");
        String email="";
        do{
            email=in.nextLine();
            if(!ControlloEmail(email)){
                System.out.print("Email non valida.\nReinserire: ");
            }
        }while(!ControlloEmail(email));
        this.email_address=email;

        //insert monitoring centre
        //TODO
        this.centre=null;

        // Insert password
        System.out.print("Inserire la password: ");
        this.passwd=in.nextLine();

        // Set the userid
        this.userid=setUserId();

        // Add the operator to the file
        aggiungiOperatore(false);
        
        System.out.println("\n\nRegistrazione completata!\nPer accedere usare il seguente userid: " + String.format("%05d", this.userid) + " e la password scelta");
    }

    public void autenticazione() {
        //TODO
    }
    //set the userid
    private short setUserId(){
        long id=0;
        //if the file doesn't exist, create it and add the first operator with userid 00001, else add the operator with incremental userid
        if(!file.exists()){
            try {
                file.createNewFile();
                aggiungiOperatore(true);
            } catch (IOException e) {
                System.out.println("Errore nella creazione del file");
            }
            id=1;
        }else{
            try {
                id=(Files.lines(Paths.get(this.filepath.toFile().toString())).count());
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
    // Initialize objects for reading/writing files
    //TODO
    //va eliminata
    private void setReadingWritingFiles(){
        //file=new File(this.filepath.toFile().toString());

        /*
        try {
            leggi=new BufferedReader(new FileReader(this.filePath));
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato");
        }
 
        try {
            scrivi=new BufferedWriter(new FileWriter(this.filePath, true));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
    }

    // Add the current instance of AutorizedOperator to the file OperatoriRegistrati.csv
    private void aggiungiOperatore(boolean nuovoFile){

        String s;

        if(nuovoFile){
            s="Matricola,Nome,Cognome,Codice Fiscale,Email,Password,Centro di Monitoraggio\n";
        }else{
            s=String.format("%05d", this.userid);
            s=s + "," + this.nome + "," + this.cognome + "," + this.codice_fiscale + "," + this.email_address + "," + this.passwd + "," + this.centre + "\n";
        }

        //TODO
        //rendere scrivi locale, rimuovere scrivi attributo delle classe
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

    public static void leggiOperatori(){

        file=new File(filepath.toFile().toString());

        try{

            FileReader freader= new FileReader(file);//created an object of freader class
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

}