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
 * @version 0.10.0
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
    // Program version
    private final static String version = "0.10.0";
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
    }
    /**
     * Stampa a schermo la versione e la licenza del programma.
     */
    public static void print_header() {
        // Print License
        System.out.println();
        System.out.print("    ");
        print_version();
        System.out.println();
        print_license();
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
}
