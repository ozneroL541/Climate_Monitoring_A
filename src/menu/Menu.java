/**************************************
 * Matricola    Cognome     Nome
 * 754530       Galimberti  Riccardo
 * 753747       Masolo      Carlos
 * 755152       Paredi      Giacomo
 * 753252       Radice      Lorenzo
 * Sede: Como
***************************************/

package src.menu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Menu {
    // Menu string
    private String menu = null;
    // Number of options
    private short op_number = 0;
    // Number of the exit option
    private short exit_number = 0;
    // Object constructor
    public Menu(){
        // File name of the menu
        String filename = "menu.txt";
        // String builder object
        StringBuilder str_maker = new StringBuilder();
        // Exception
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            // Current line
            String current_line;
            // While not EOF
            while ((current_line = br.readLine()) != null) {
                // Create the string
                str_maker.append(current_line).append("\n");
                // Increment option number
                this.op_number++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Return the String made to the menu
        this.menu = str_maker.toString();
        // The exit number is the last one
        this.exit_number = this.op_number;
    }
    // This method returns the menu string and, if it doesn't exist it makes it before return
    public String getMenu() {
        return menu;
    }
    //Check if the integer number corrisponds to the Exit command
    public boolean isQuit( short n ) {
        return n == exit_number;
    }
    // Return the Number of Options of the Menu
    // It's the max (which is the last) number displayed by the Menu
    public short NumberOfOptions() {
        return op_number;
    }
}
