/**************************************
 * Matricola    Cognome     Nome
 * 754530       Galimberti  Riccardo
 * 755152       Paredi      Giacomo
 * 753252       Radice      Lorenzo
 * Sede: Como
***************************************/

package src.users;

import src.common.InputScanner;
import src.geographicarea.GeographicArea;

import java.util.InputMismatchException;

/**
 * Un oggetto della classe <code>User</code> rappresenta un utente.
 * Ciò che l'utente può fare &egrave descritto nei metodi che gli appartengono.
 * @author Lorenzo Radice
 * @version 0.10.3
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
    public static void cercaAreaGeografica() {
        // Loop exit variable
        boolean exit = true;
        // Input integer
        int in = -1;
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
                    System.out.println("L'indice selezionato non è disponibile.");
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
    }
}
