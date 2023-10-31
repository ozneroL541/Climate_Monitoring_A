package src.ee;

import src.input.InputScanner;

/**
 * Controlla che i valori siano accettabili
 * @author Lorenzo Radice
 * @version 0.10.2
 */
public class EE {
    // Choose if enable the EasterEgg
    final private static boolean eg_enable = true;
    /**
     * Controlla che i valori inseriti siano validi
     * @param n nome
     * @param index indice
     * @return true se accettabile
     */
    public static boolean EE_switch( String n, int index ) {
        if (eg_enable) {
            switch (index) {
                case 1:
                    return EasterEgg_Musk(n);
                case 2:
                    return EasterEgg_42(n);
                default:
                    return false;
            }
        } else {
            return false;
        }
    }
    /**
     * Controllo l'attivazione dell'Easter Egg 42 e lo attiva se la condizione è verificata.
     * @param n input
     * @return true se si attiva l'Easter Egg
     */
    private static boolean EasterEgg_42(String n) {
        if (n.equals("42")) {
            System.out.println(EasterEgg_42_String());
            return true;
        } else
            return false;
    }
    /**
     * Ritorna l'Easter Egg 42
     * @return la stringa easteregg
     */
    private static String EasterEgg_42_String() {
        // Create l'output
        String[] s1 = { "If you find something wrong in this program...\t",
                        "It's not a bug, it's a feature!               \t" };
        final String gr = "******************";
        String out = "";
        int l = s1[0].length();
        for (int i = 0; i < l; i++)
            out += "*";
        out += gr;
        for (String s : s1) {
            out += "\n*\t" + s + "\t*";
        }
        out += "\n";
        for (int i = 0; i < l; i++)
            out += "*";
        out += gr;
        // Print output
        return out + "\n";
    }
    /*
     * Controlla se il nome inserito è il figlio di Elon Musk.
     * @param n nome
     * @return true se è il figlio di Musk
     */
    private static boolean isMuskSon ( String n ) {
        // Possible writings of Musk's son name
        final String[] musk_son = { "X Æ A-12", "X Æ A-XII", "X AE A-12", "X AE A-XII",
                                    "XÆA-12", "XÆA-XII", "XAEA-12", "XAEA-XII"};
        // Check the string while is uppercase
        String check_String = n.toUpperCase();
        // For every writing of the name
        for (String s : musk_son) {
            // Check if the string equals the Musk's son name
            if (check_String.equals(s)) {
                // Return true if they are equal
                return true;
            }
        }
        // Return false if it is not Musk's son
        return false;
    }
    /*
     * Se il nome inserito è quello del figlio di Elon Musk attiva un Easter Egg e ritorna true.
     * @param name nome
     * @return true se il nome è accettabile
     */
    private static boolean EasterEgg_Musk ( String name ) {
        // Answer
        String ans = "";
        // If it is Musk's son
        if (isMuskSon(name)) {
            // Question
            System.out.print("Sei il figlio di Elon Musk(S/N)?");
            // Input answer
            ans = InputScanner.INPUT_SCANNER.nextLine();
            // UpperCase answer
            ans = ans.toUpperCase();
            // If Negative answer
            if (ans.contains("N") || ans.contains("Q") || ans.contains("ESC") || ans.contains("EXIT") ) {
                // Return false because it is not Musk's son
                return false;
            }
            // Funny Phrase
            System.out.println("Metti una buona parola su di noi con tuo padre.");
            System.out.println("Finanziamenti, proposte di lavoro o collaborazione sono ben accette.");
            // TODO Easter Egg
            // It is Musk's son
            return true;
        } else
            // It is not Musk's son
            return false;
    }
}
