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

/**
 * Classe contenete informazioni su versione e licenza del software.
 * @author Lorenzo Radice
 * @version 0.10.1
 */
public class Header {
    // Program Name
    private final static String p_name = "Climate Monitoring";
    // Short License Reminder
    private final static String license_h =
            "    Climate Monitoring  Copyright (C) 2023  Galimberti Riccardo\r\n" +//"
            "                                            Paredi Giacomo\r\n" + //
            "                                            Radice Lorenzo\r\n" + //
            "    License GPLv3: GNU GPL version 3 <https://gnu.org/licenses/gpl-3.0.html>\n" + //
            "    This program comes with ABSOLUTELY NO WARRANTY.\r\n" + //
            "    This is free software, and you are welcome to redistribute it\r\n" + //
            "    under certain conditions. ";
    // TODO write show c
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
            "ALL NECESSARY SERVICING, REPAIR OR CORRECTION.\n";
    // Program version
    private final static String version = "0.10.0";
    // Help
    private final static String help = 
            "\nUsage: ClimateMonitor.jar [options]\r\n" + //
            "Options:\r\n" + //
            "\t-h\t-help\t\t--help\t\t\tShow this help menu\r\n" + //
            "\t\t-show c\t\t--show c\t\tShow the License Conditions\r\n" + //
            "\t\t-show w\t\t--show w\t\tShow the License Warranty\r\n" + //
            "\t-v\t-version\t--version\t\tShow the current program version\r\n" + //
            "\nEnter no command to start the actual program.";
    /**
     * Stampa a schermo un breve messaggio con la licenza.
     */
    public static void print_license() {
        // Print License
        System.out.println(license_h);
    }
    /**
     * Stampa a schermo la versione del programma.
     */
    public static void print_version() {
        // Print version
        System.out.println(p_name + "\tVersion: " + version);
        System.out.println();
    }
    /**
     * Stampa a schermo la versione e la licenza del programma.
     */
    public static void print_header() {
        // Print License
        System.out.println();
        System.out.print("    ");
        print_version();
        print_license();
        System.out.println();
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
     * Stampa a schermo la garazia.
     */
    public static void print_warranty() {
        System.out.println();
        System.out.println(warranty);
        System.out.println();
    }
    /**
     * Stampa a schermo i possibili comandi
     */
    private static void print_help() {
        System.out.println(help);
        System.out.println();
    }
    /**
     * Controlla se il comando inserito si riferisce ad uno di quelli per la visualizzazione della licenza
     * o della versione. Se il comando corrisponde stampa a schermo la caratteristica richiesta e ritorna
     * true, altrimenti ritorna false.
     * @param cmd comando
     * @return true se viene stampato qualcosa.
     */
    public static boolean evalCommand( String[] cmd ) {
        // For each command
        for ( short i = 0; i < cmd.length; i++ ) {
            // If str is...
            switch (cmd[i]) {
                // Conditions
                case "--show":
                case "-show":
                case "show":
                    // There is a letter after
                    if (cmd[i + 1] != null) {
                        // Conditions argument
                        if (cmd[i + 1].toLowerCase().equals("c")) {
                            // Conditions
                            // TODO Print Conditions
                            return true;
                        // Warranty argument
                        } else if (cmd[i + 1].toLowerCase().equals("w")) {
                            // Warranty
                            print_warranty();
                            return true;
                        }
                    } else
                        break;
                // Version
                case "--version":
                case "-version":
                case "-v":
                    print_version();
                    return true;
                case "--help":
                case "-help":
                case "-h":
                    print_help();
                    return true;
                // Nothing
                default:
                    break;
            }
        }
        return false;
    }
}
