/**************************************
 * Matricola    Cognome     Nome
 * 754530       Galimberti  Riccardo
 * 755152       Paredi      Giacomo
 * 753252       Radice      Lorenzo
 * Sede: Como
***************************************/

package src.monitoringcentre;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;

import src.research.Research;

/**
 * Classe che contiene il centro di monitoraggio.
 * @author Riccardo Galimberti
 * @version 0.10.0
 */
public class MonitoringCentre {
    // private String via, civico, cap, comune, provincia;
    private String nome;
    private String [] indirizzo = new String[5];
    private String [] areeInteresse;
    private short userid;
    Research res = new Research();

     private final static File f = FileSystems.getDefault().getPath("data", "CentroMonitoraggio.dati.csv").toFile();

    public MonitoringCentre(String nome, String [] indirizzo, String [] areeInteresse, short userid){
        if(CenterExistence(nome))
            registraCentroAree(nome, indirizzo, areeInteresse, userid);
    }

    //costruttore vuoto
    public MonitoringCentre(){
    }

    public void registraCentroAree(String nome, String [] indirizzo, String [] areeInteresse, short userid){
        this.nome = nome;
        if(indirizzo.length == this.indirizzo.length){
            this.indirizzo = indirizzo;
        }else{
            //TODO exception wrong array input length
        }
        if(areeInteresse.length > 0){
            this.areeInteresse = areeInteresse;
        }else{
            //TODO exception empty list
        }
        this.userid = userid;
        memorizzaCentroAree(nome, indirizzo, areeInteresse, userid);
    }
    
    //metodo per controllare se un centro esiste già in base a nome
    private boolean ExistingCenter(String name){
        File f = new File("data\\CentroMonitoraggio.dati.csv");
        boolean exists = false;
        res.AllStringInCol(f, 0, name);
        return exists;
    }

    //getLista del nome dei centri (returna array di string)
    public String[] getCentri(){
        return res.getRecord(f,0);
    }

    //aggiunge un'area ad un centro già esistente
    private void addArea(String area, String nome){

        if(CenterExistence(nome)){
            //TODO aggiunta area 
        }
        
    }

    private void memorizzaCentroAree(String nome, String [] indirizzo, String [] areeInteresse, short userid){
        String s = nome + ",";
        for (int i = 0; i < indirizzo.length; i++) {
            s = s + indirizzo[i] + ",";
        }
        for (int i = 0; i < areeInteresse.length; i++) {
            s = s + areeInteresse[i] + ",";
        }
        s = s + userid;
        try(FileWriter fw = new FileWriter("data\\CentroMonitoraggio.dati.csv", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw)){
            out.println(s);
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }

    private boolean CenterExistence(String nome){
        boolean exists = false;
        if(res.isStringInCol(f,0,nome))
            exists = true;
        else
            exists = false;

        return exists;
    }
}
