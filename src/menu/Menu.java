package src.menu;

public class Menu {
    // Menu string
    private String menu = null;
    // Options array
    private static String options[] = {
        "Ricerca aree",
        "Visualizzazione parametri climatici associati",
        "Registrazione (Solo operatori autorizzati)",
        "Login (Solo operatori autorizzati)",
        "Creazione centri di monitoraggio (Solo operatori autorizzati)",
        "Inserirmento valori dei parametri climatici (Solo operatori autorizzati)",
        "Uscita"
    };
    // Separator String
    private static String separator = " - ";
    // Object constructor
    public Menu(){
        menu = MenuMaker();
    }
    // This method makes the string menu with numbers and with the separator
    // example:  "1 - Option1/n2 - option2" ...
    private static String MenuMaker() {
        String out = "";
        for (int i = 0; i < options.length; i++) {
            out += (i + 1) +  separator + options[i] + '\n';
        }
        return out;
    }
    // This method returns the menu string and, if it doesn't exist it makes it before return
    public String getMenu() {
        // Before return check if menu is null then makes it
        if ( menu == null )
            menu = MenuMaker();
        return menu;
    }
    // Check if the integer number corrisponds to the Exit command
    public boolean isQuit( int n ) {
        return options[n-1].equals("Uscita");
    }
    // Return the Number of Options of the Menu
    // It's the max (which is the last) number displayed by the Menu
    public int NumberOfOptions() {
        return options.length + 1;
    }
}
