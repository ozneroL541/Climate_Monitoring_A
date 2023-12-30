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

package src.header;

import src.common.InputScanner;

/**
 * Classe contenete informazioni su versione e licenza del software.
 * @author Lorenzo Radice
 * @version 0.22.2
 */
public class Header {
    /*
     * Indexes
     */
    private static final record IndexOf() {
        private static final short license = 1;
        private static final short warranty = 2;
        private static final short conditions = 3;
        private static final short version = 4;
        private static final short exit = 5;
    }
    // TODO: Always remember to Update (remove this TODO only after version 1.0.0)
    // Program version
    private final static String version = "0.22.1\tAlpha";
    // Program Name
    private final static String p_name = "Climate Monitoring";
    // Short License Reminder
    private final static String license_h =
            "    Climate Monitoring  Copyright (C) 2023  Galimberti Riccardo\r\n" +//"
            "                                            Paredi Giacomo\r\n" + //
            "                                            Radice Lorenzo\r\n" + //
            "    License GPLv3: GNU GPL version 3 <https://gnu.org/licenses/gpl-3.0.html>\r\n" + //
            "    This program comes with ABSOLUTELY NO WARRANTY; for details type `show w'.\r\n" + //
            "    This is free software, and you are welcome to redistribute it\r\n" + //
            "    under certain conditions; type `show c' for details.\r\n";
    // Conditions
    private final static String conditions = "See <http://www.gnu.org/licenses/> for full conditions.";
    // Warranty
    private final static String warranty =
            "\tDisclaimer of Warranty.\r\n" + //
            "\r\n" + //
            "  THERE IS NO WARRANTY FOR THE PROGRAM, TO THE EXTENT PERMITTED BY\r\n" + //
            "APPLICABLE LAW.  EXCEPT WHEN OTHERWISE STATED IN WRITING THE COPYRIGHT\r\n" + //
            "HOLDERS AND/OR OTHER PARTIES PROVIDE THE PROGRAM \"AS IS\" WITHOUT WARRANTY\r\n" + //
            "OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, BUT NOT LIMITED TO,\r\n" + //
            "THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR\r\n" + //
            "PURPOSE.  THE ENTIRE RISK AS TO THE QUALITY AND PERFORMANCE OF THE PROGRAM\r\n" + //
            "IS WITH YOU.  SHOULD THE PROGRAM PROVE DEFECTIVE, YOU ASSUME THE COST OF\r\n" + //
            "ALL NECESSARY SERVICING, REPAIR OR CORRECTION.\r\n";
    // Help
    private final static String help = 
            "\nUsage: ClimateMonitor.jar [options]\r\n" + //
            "Options:\r\n" + //
            "\t-h\t-help\t\t--help\t\t\tShow this help menu\r\n" + //
            "\t\t-show c\t\t--show c\t\tShow the License Conditions\r\n" + //
            "\t\t-show w\t\t--show w\t\tShow the License Warranty\r\n" + //
            "\t-v\t-version\t--version\t\tShow the current program version\r\n" + //
            "\nEnter no command to start the actual program.\r\n";
    /**
     * Stampa a schermo un breve messaggio con la licenza.
     */
    public static void print_license() {
        // Print License
        System.out.println( "\n" + license_h);
    }
    /**
     * Stampa a schermo la versione del programma.
     */
    public static void print_version() {
        // Print version
        System.out.println( "\n    " + p_name + "\tVersion: " + version);
        System.out.println();
    }
    /**
     * Stampa a schermo la versione e la licenza del programma.
     */
    public static void print_header() {
        // Print License
        print_version();
        print_license();
        System.out.println();
    }
    /**
     * Ritorna il nome del programma
     * @return p_name
     */
    public static String getpName() {
        return p_name;
    }
    /**
     * Ritorna il messaggio contenete la licenza del software.
     * @return license_h
     */
    public static String getLicenseH() {
        return license_h;
    }
    /**
     * Ritorna la versione del programma
     * @return version
     */
    public static String getVersion() {
        return version;
    }
    /**
     * Stampa a schermo le condizioni di utilizzo
     */
    public static void print_conditions() {
        System.out.println();
        System.out.println(conditions);
        System.out.println();
    }
    /**
     * Stampa a schermo la garazia.
     */
    public static void print_warranty() {
        System.out.println();
        System.out.println(warranty);
    }
    /**
     * Controlla se il comando inserito si riferisce ad uno di quelli per la visualizzazione della licenza
     * o della versione. Se il comando corrisponde stampa a schermo la caratteristica richiesta e ritorna
     * true, altrimenti ritorna false.
     * @param cmd comando
     * @return true se viene stampato qualcosa.
     */
    public static boolean evalCommand( String[] cmd ) {
        // Return
        boolean exit = false;
        // For each command
        for ( short i = 0; !exit && i < cmd.length; i++ ) {
            // If str is...
            switch (cmd[i].toLowerCase()) {
                // Conditions
                case "--show":
                case "-show":
                case "show":
                    // There is a letter after
                    if (cmd[i + 1] != null) {
                        // Conditions argument
                        if (cmd[i + 1].toLowerCase().equals("c")) {
                            // Conditions
                            exit = selectedAction(IndexOf.conditions);
                        // Warranty argument
                        } else if (cmd[i + 1].toLowerCase().equals("w")) {
                            // Warranty
                            exit = selectedAction(IndexOf.warranty);
                        }
                    } else {
                        exit = false;
                    }
                    break;
                // Version
                case "--version":
                case "-version":
                case "-v":
                    exit = selectedAction(IndexOf.version);
                    break;
                // Help
                case "--help":
                case "-help":
                case "-h":
                    print_help();
                    exit = true;
                // Nothing
                default:
                    break;
            }
        }
        // Return
        return exit;
    }
    /**
     * Valuta se l'utente richiede informazioni riguardo al software e le fornisce.
     * @return true se stampa qualcosa si richiesto
     */
    public static boolean doWhat() {
        // Input
        String input = "";
        // Splitted input
        String[] in = null;
        // Output Message
        System.out.println("\nPress ENTER to continue...");
        try {
            // Read input
            input = InputScanner.INPUT_SCANNER.nextLine();
            // Split input
            in = input.split(" ");
            // Evaluate command
            return evalCommand(in);
        } catch (Exception e) {
            // In case of error return false
            return false;
        }
    }
    /**
     * Info Menu
     */
    public static void menu() {
        // Info Menu
        final String menu =
            "\tInfo\r\n" + //
            "1 - Licenza\r\n" + //
            "2 - Garanzia\r\n" + //
            "3 - Condizioni\r\n" + //
            "4 - Versione\r\n" + //
            "5 - Esci\n";
        // Print Menu
        System.out.println(menu);
    }
    /**
     * Mostra il menù e permette di sceglierne le opzioni.
     */
    public static void ChooseOption() {
        // Short integer for the menu options
        short menu_input = 0;
        // Input
        String input = "";
        // While exit is not selected
        do {
            // Output the menu
            menu();
            // Request
            System.out.print("Inserire codice:\t");
            // Input
            try {
                // Input
                input = InputScanner.INPUT_SCANNER.nextLine();
                // Parse input
                menu_input = (short) Short.valueOf(input);
            } catch (NumberFormatException e) {
                // Set to 0
                menu_input = 0;
            } catch (Exception e) {
                // Set to -1
                menu_input = -1;
            }
        // Check if exit
        } while ( selectedAction(menu_input) );
    }
    /*
     * Stampa a schermo i possibili comandi
     */
    private static void print_help() {
        System.out.println(help);
        System.out.println();
    }
    /*
     * Esegue l'azione richiesta.
     * @param input azione
     * @return false se è richiesto di uscire
     */
    private static boolean selectedAction( short input ) {
        // Select the method choosen by the user
        switch (input) {
            case IndexOf.license:
                // License
                print_license();
                return true;
            case IndexOf.warranty:
                // Warranty
                print_warranty();
                return true;
            case IndexOf.conditions:
                // Conditions
                print_conditions();
                return true;
            case IndexOf.version:
                // Version
                print_version();
                return true;
            case IndexOf.exit:
                // Esci
                return false;
            default:
                // Error Message
                System.out.println("\nIl valore inserito non è corretto.");
                System.out.println("Inserire un numero valido per continuare.\n");
                return true;
        }
    }
}
