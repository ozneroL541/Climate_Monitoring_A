/**************************************
 * Matricola    Cognome     Nome
 * 754530       Galimberti  Riccardo
 * 755152       Paredi      Giacomo
 * 753252       Radice      Lorenzo
 * Sede: Como
***************************************/

package src.users;

import java.util.Scanner;

import src.geographicarea.GeographicArea;

/**
 * Un oggetto della classe <code>User</code> rappresenta un utente.
 * Ciò che l'utente può fare è descritto nei metodi che gli appartengono.
 * @author Lorenzo Radice
 * @version 0.0.1
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
    public void Ricerca() {
        // Loop exit variable
        boolean exit = true;
        // Input integer
        int in = -1;
        // While exit is false
        do {
            // Input scanner
            Scanner sc = new Scanner(System.in);
            // Print indexes menu
            GeographicArea.printIndexesMenu();
            // Output
            System.out.println("Seleziona il tipo di parametro con cui effettuare la ricerca.");
            // Output for input
            System.out.print  ("Inserire il codice: ");
            // Input integer
            in = sc.nextInt();
            // If the chosen integer exist 
            if (GeographicArea.IndexExist(in)) {
                // Research Argument
                String arg = "";
                // Output
                System.out.print("Inserire il parametro per la ricerca: ");
                do {
                    // Input string
                    arg = sc.nextLine();
                    // If the argument is correct
                    if (GeographicArea.argumentCorrect(arg, in)) {
                        // Search
                        GeographicArea.SearchList(in, arg, 0);
                    } else {
                        // Output
                        System.out.print("Reinserire il parametro: ");
                        // Not Exit
                        exit = false;
                    }
                } while (!exit);
            } else {
                // Error output
                System.out.println("L'indice selezionato non è disponibile.");
                System.out.println("");
                // Not exit
                exit = false;
            }
        } while (!exit);
    }
}
