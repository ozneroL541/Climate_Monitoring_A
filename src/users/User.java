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

import java.util.InputMismatchException;

import src.common.InputScanner;
import src.geographicarea.GeographicArea;
import src.parameters.Parameters;

/**
 * Un oggetto della classe <code>User</code> rappresenta un utente.
 * Ciò che l'utente può fare è descritto nei metodi che gli appartengono.
 * @author Lorenzo Radice
 * @version 0.20.2
 */
public class User {
    /**
     * Crea un utente
     */
    public User() {
        //TODO
    }
    /**
     * Permette all'utente di effettuare la ricerca di aree geografiche.
     * @author Lorenzo Radice
     */
    public void cercaAreaGeografica() {
        // Loop exit variable
        boolean exit = true;
        // Input integer
        int in = -1;
        // Check file existence
        if ( GeographicArea.doesCSVExist() ) {
            // While exit is false
            do {
                // Input
                try {
                    // Print indexes menu
                    GeographicArea.printIndexesMenu();
                    // Output
                    System.out.println("Seleziona il tipo di parametro con cui effettuare la ricerca.");
                    // Output for input
                    System.out.print  ("Inserire il codice: ");
                    // Input integer
                    in = InputScanner.INPUT_SCANNER.nextInt();
                    // Collect trash
                    InputScanner.INPUT_SCANNER.nextLine();
                    // If the chosen integer exist 
                    if (GeographicArea.IndexExist(in)) {
                        // Research Argument
                        String arg = "";
                        // Output
                        System.out.print("Inserire il parametro per la ricerca: ");
                        do {
                            // Input string
                            arg = InputScanner.INPUT_SCANNER.nextLine();
                            // If the argument is correct
                            if (GeographicArea.argumentCorrect(arg, in)) {
                                // New Line
                                System.out.println();
                                // Search
                                GeographicArea.SearchList(in, arg, 0);
                                // Exit true
                                exit = true;
                            } else {
                                // Output
                                System.out.print("Reinserire il parametro: ");
                                // Not Exit
                                exit = false;
                            }
                        } while (!exit);
                    } else {
                        // Error output
                        System.out.println("\nL'indice selezionato non è disponibile.");
                        System.out.println();
                        // Not exit
                        exit = false;
                    }
                } catch ( InputMismatchException e) {
                    // Reset input scanner
                    InputScanner.INPUT_SCANNER.nextLine();
                    // Error Output
                    System.err.println("\nInserimento non valido.\nInserire uno dei numeri mostrati per selezionare un'opzione.");
                    // New line
                    System.out.println();
                    // Not exit
                    exit = false;
                } catch (Exception e) {
                    // Output Exception
                    e.printStackTrace();
                    // Exit 
                    exit = true;
                }
            } while (!exit);
        } else {
            System.out.println("\nNon è presente alcuna area di interesse per cui effettuare la ricerca.");
        }
    }

    /**
     * Permette all'utente di visualizzare le informazioni associate ad una area geografica.
     * @param id id dell'area
     */
    public void visualizzaAreaGeografica(){
        // TODO fa un po' cagare va sistemato
        boolean exit=false;
        String id;
        String aree[]=Parameters.getIDAree();
        if(aree!=null){
            System.out.println("Aree disponibili:");
            for(String area: aree){
                System.out.println(area);
            }
            System.out.print("Inserire l'id dell'area per visualizzarne le informazioni: ");

            do{
                id=InputScanner.INPUT_SCANNER.nextLine();
                //check if user input is a valid id
                for(String value: aree){
                    if(value.equals(id)){
                        exit=true;
                        break;
                    }else{
                        exit=false;
                        break;
                    }
                }
                if (!exit) {
                    System.out.print("Id non valido\nInserire un id tra quelli disponibili: ");
                }
            }while(!exit);

            Parameters.MostraParametri(id);
            
        }else{
            System.out.println("Nessuna area disponibile");
        }
    }
}
