package src.menu;

public class Menu {
    private String menu = null;
    private static String s[] = {
        "Ricerca aree",
        "Visualizzazione i parametri climatici associati",
        "Registrazione (Solo operatori autorizzati)",
        "Login (Solo operatori autorizzati)",
        "Creazione centri di monitoraggio (Solo operatori autorizzati)",
        "Inserirmento valori dei parametri climatici (Solo operatori autorizzati)",
        "Uscita"
    };
    private static String separatore = " - ";
    private static String out = "";

    public Menu(){
        menu = MenuMaker();
    }
    public static String MenuMaker() {
        for (int i = 0; i < s.length; i++) {
            out += (i + 1) +  separatore + s[i] + '\n';
        }
        return out;
    }
    public String getMenu() {
        if ( menu == null )
            menu = MenuMaker();
        return menu;
    }
    public boolean isQuit( int n ) {
        return s[n].equals("Uscita");
    }
}