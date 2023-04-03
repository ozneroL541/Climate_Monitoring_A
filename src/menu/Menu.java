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
    // Object constructor
    public Menu(){
        String filename = "menu.txt";
        StringBuilder str_maker = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String current_line;
            while ((current_line = br.readLine()) != null) {
                op_number++;
                str_maker.append(current_line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        menu = str_maker.toString();
    }
    // This method returns the menu string and, if it doesn't exist it makes it before return
    public String getMenu() {
        return menu;
    }
    //Check if the integer number corrisponds to the Exit command
    public boolean isQuit( int n ) {
        return n == op_number;
    }
    // Return the Number of Options of the Menu
    // It's the max (which is the last) number displayed by the Menu
    public int NumberOfOptions() {
        return (int) op_number;
    }
}
