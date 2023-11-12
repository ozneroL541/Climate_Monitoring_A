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

package src.monitoringcentre;
// TODO Remove unused imports
import java.io.File;
import java.nio.file.FileSystems;
import java.util.ArrayList;

import src.common.*;

/**
 * Classe che contiene il centro di monitoraggio.
 * @author Riccardo Galimberti
 * @version 0.10.6
 */
public class MonitoringCentre {
    // private String via, civico, cap, comune, provincia;
    private String nome;
    private String [] indirizzo = new String[5];
    private String [] areeInteresse;
    private short userid;
    private final static String header = "nome, via/piazza, numero civico, cap, comune, provincia, userID, aree";

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
            System.err.println("Errore: lunghezza array errata."); 
        }
        if(areeInteresse.length > 0){
            this.areeInteresse = areeInteresse;
        }else{
            System.err.println("Errore: Lista vuota."); 
        }
        this.userid = userid;
        memorizzaCentroAree(nome, indirizzo, areeInteresse, userid);
    }

    /**
     * Ritorna un array di stringhe dei nomi dei Centri di Monitoraggio.
     * Se non ci sono Centri ritorna null.
     * @return nomi dei centri
     */
    public String[] getCentri(){
        return Research.getColArray(f,0);
    }

    private void memorizzaCentroAree(String nome, String [] indirizzo, String [] areeInteresse, short userid){
        ArrayList<String> str = new ArrayList<String>();
        String aree = "";
        str.add(nome);
        for (int i = 0; i < indirizzo.length; i++) {
            str.add(indirizzo[i]);
        }
        str.add(String.valueOf(userid));
        
        for (int i = 0; i < areeInteresse.length; i++) {
            aree = aree + areeInteresse[i] + "-" ;
        }
        str.add(aree);
        String s[] = str.toArray(new String[str.size()]);
        CSV_Utilities.addArraytoCSV(f,s,header);
    }

    private boolean CenterExistence(String nome){
        boolean exists = false;
        if(Research.isStringInCol(f,0,nome))
            exists = true;
        else
            exists = false;

        return exists;
    }
    // TODO Remove test main
    public static void main(String[] args) {
        String nome = "Centro Prova";
        String [] indirizzo = { "Via Regina Teodolinda" ,"37", "Como", "CO", "Italia" };
        String [] areeInteresse = {"123456", "1234567", "123456" };
        short userid = 00002;
        MonitoringCentre m = new MonitoringCentre(nome, indirizzo, areeInteresse, userid);
        m.registraCentroAree(nome, indirizzo, areeInteresse, userid);
        
    }
}
