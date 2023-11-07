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

package src.license;
//TODO
public class License {
    // Program header
    private final static String header =
            "    Climate Monitoring  Copyright (C) 2024  Galimberti Riccardo, Paredi Giacomo, Radice Lorenzo\r\n" + //
            "    This program comes with ABSOLUTELY NO WARRANTY; for details type `show w'.\r\n" + //
            "    This is free software, and you are welcome to redistribute it\r\n" + //
            "    under certain conditions; type `show c' for details.";
    // TODO 
    public static void print() {
        // Print License
        System.out.println(header);
    }
}
