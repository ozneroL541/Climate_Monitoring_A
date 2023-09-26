/**************************************
 * Matricola    Cognome     Nome
 * 754530       Galimberti  Riccardo
 * 753747       Masolo      Carlos
 * 755152       Paredi      Giacomo
 * 753252       Radice      Lorenzo
 * Sede: Como
***************************************/

package src.monitoringcentre;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Classe che contiene il centro di monitoraggio.
 * @author Riccardo Galimberti
 * @version 0.0.1
 */
public class MonitoringCentre {
    // private String via, civico, cap, comune, provincia;
    private String nome;
    private String [] indirizzo = new String[5];
    private String [] areeInteresse;
    private short userid;

    public MonitoringCentre(String nome, String [] indirizzo, String [] areeInteresse, short userid){
        //TODO controllare se esiste un centro con lo stesso indirizzo
        registraCentroAree(nome, indirizzo, areeInteresse, userid);
    }

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
    
    //TODO metodo per controllare se un centro esiste già in base a nome
    //TODO getLista del nome dei centri (returna array di string)
    //TODO aggiungere un'area ad un centro già esistente

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
}
